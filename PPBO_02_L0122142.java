// NIM : L0122142
// Nama : Rizal Dwi Anggoro

/*
  File ini merupakan file utama dari program SuperMarket.

  File ini berguna untuk mengatur UI/menu yang ditampilkan
  di console.

  Alur kerja program:
  ui -> (call: with/without parameters) -> logic
  logic -> (return: data) -> ui
 */

import java.util.List;
import java.util.Scanner;

public class PPBO_02_L0122142 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    SuperMarket superMarket = new SuperMarket();
    superMarket.initializeProducts();

    while (true) {
      printProducts(superMarket);

      int option = printMainMenu(scanner);

      if (option == 1) sellProduct(scanner, superMarket);
      else if (option == 2) restockProduct(scanner, superMarket);
      else if (option == 3) createProduct(scanner, superMarket);
      else if (option == 4) showSuperMarketStatistic(scanner, superMarket);
      else {
        System.out.println("\nKeluar program...");
        break;
      }
    }

    scanner.close();
  }

  private static void showSuperMarketStatistic(Scanner scanner, SuperMarket superMarket) {
    System.out.println("\nStatistik Anggoro Market\n");

    System.out.printf("Total transaksi  : %d\n", SuperMarket.totalTransaction);
    System.out.printf("Total pendapatan : Rp %s\n", Utils.currencyFormat(superMarket.getTotalBalance()));

    System.out.println("\nRiwayat transaksi:\n");

    if (superMarket.getTransactionHistories().isEmpty()) {
      System.out.println("Tidak ada data transaksi!");
      Utils.enterToContinue(scanner);
      return;
    }

    Utils.printLine("=", 60);
    System.out.printf(
        "%-3s | %-24s | %-8s | %-16s\n",
        "No", "Nama Produk", "Jumlah", "Harga + PPN"
    );

    int num = 1;
    for (Transaction transaction : superMarket.getTransactionHistories()) {
      System.out.printf(
          "%3d | %-24s | %8d | Rp%14s\n",
          num,
          transaction.getProduct().getName(),
          transaction.getAmount(),
          Utils.currencyFormat(transaction.getTotalPrice() + transaction.getTotalPPN())
      );
      num++;
    }
    Utils.printLine("=", 60);
    Utils.enterToContinue(scanner);
  }

  private static void createProduct(Scanner scanner, SuperMarket superMarket) {
    System.out.println("\nTambah Produk Baru\n");

    System.out.println("Masukkan nama produk");
    System.out.print("> ");
    String name = scanner.nextLine();

    System.out.println("Masukkan harga produk");
    System.out.print("> ");
    int price = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Masukkan jumlah produk");
    System.out.print("> ");
    int amount = scanner.nextInt();
    scanner.nextLine();

    try {
      superMarket.createProduct(name, price, amount);
      System.out.printf("\nProduk dengan nama %s berhasil dibuat!\n", name);
      Utils.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("\nError: %s\n", e.getMessage());
      Utils.enterToContinue(scanner);
    }
  }

  private static void restockProduct(Scanner scanner, SuperMarket superMarket) {
    System.out.println("\nRestok Produk\n");

    System.out.println("Masukkan nama produk");
    System.out.print("> ");
    String name = scanner.nextLine();

    System.out.println("Masukkan jumlah produk");
    System.out.print("> ");
    int amount = scanner.nextInt();
    scanner.nextLine();

    try {
      superMarket.restockProduct(name, amount);
      System.out.printf("\nProduk dengan nama %s berhasil diisi kembali!\n", name);
      Utils.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("\nError: %s\n", e.getMessage());
      Utils.enterToContinue(scanner);
    }
  }

  private static void sellProduct(Scanner scanner, SuperMarket superMarket) {
    System.out.println("\nJual Produk\n");

    System.out.println("Masukkan nama produk");
    System.out.print("> ");
    String name = scanner.nextLine();

    System.out.println("Masukkan jumlah produk");
    System.out.print("> ");
    int amount = scanner.nextInt();
    scanner.nextLine();

    try {
      Transaction result = superMarket.sellProduct(name, amount);

      int totalPrice = result.getTotalPrice() + result.getTotalPPN();

      System.out.println();
      Utils.printLine("=", 65);
      System.out.println("Total pembayaran yang harus Anda lakukan sebesar:");
      System.out.printf("Rp %s\n", Utils.currencyFormat(totalPrice));
      Utils.printLine("=", 65);

      int userChangeMoney = 0;
      int userMoney = 0;
      boolean isTransactionCompleted = false;
      while (!isTransactionCompleted) {
        System.out.println("\nMasukkan nominal pembayaran");
        System.out.print("> ");
        userMoney = scanner.nextInt();
        scanner.nextLine();

        if (userMoney < totalPrice) {
          System.out.println("\nNominal yang Anda masukkan tidak mencukupi!");
          System.out.println("Silahkan coba kembali");
        } else {
          userChangeMoney = userMoney - totalPrice;
          isTransactionCompleted = true;
        }
      }

      System.out.println("\nTransaksi sukses");
      System.out.println("Berikut struk belanja Anda\n");

      Utils.printLine("=", 65);
      System.out.printf(
          "%-6s %-24s %-16s %-16s\n",
          "Jmlh", "Nama", "Satuan", "Harga"
      );
      System.out.printf(
          "%-6s %-24s Rp%14s Rp%14s\n",
          "[x".concat(String.valueOf(result.getAmount())).concat("]"),
          result.getProduct().getName(),
          Utils.currencyFormat(result.getProduct().getPrice()),
          Utils.currencyFormat(result.getTotalPrice())
      );

      Utils.printLine("-", 65);
      System.out.printf(
          "%48s Rp%14s\n",
          "", Utils.currencyFormat(result.getTotalPrice())
      );
      System.out.printf(
          "%-48s Rp%14s\n",
          "PPN ".concat(String.valueOf(SuperMarket.PPN)).concat("%"),
          Utils.currencyFormat(result.getTotalPPN())
      );
      System.out.printf(
          "%-48s Rp%14s\n",
          "Total pembayaran",
          Utils.currencyFormat(result.getTotalPrice() + result.getTotalPPN())
      );

      Utils.printLine("-", 65);
      System.out.printf(
          "%-48s Rp%14s\n",
          "Tunai",
          Utils.currencyFormat(userMoney)
      );
      System.out.printf(
          "%-48s Rp%14s\n",
          "Kembali",
          Utils.currencyFormat(userChangeMoney)
      );

      Utils.printLine("=", 65);

      Utils.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("\nError: %s\n", e.getMessage());
      Utils.enterToContinue(scanner);
    }
  }

  private static int printMainMenu(Scanner scanner) {
    System.out.println("\nOpsi:");
    String[] menus = {
        "Jual produk",
        "Restok produk",
        "Tambah produk baru",
        "Statistik swalayan",
        "Keluar program",
    };

    for (int a = 0; a < menus.length; a++)
      System.out.printf("%d. %s\n", (a + 1), menus[a]);

    System.out.print("> ");
    int option = scanner.nextInt();
    scanner.nextLine();
    return option;
  }

  private static void printProducts(SuperMarket superMarket) {
    final List<Product> products = superMarket.getProducts();

    System.out.println("\nAnggoro Market\n");
    Utils.printLine("=", 60);
    System.out.printf(
        "%-3s | %-24s | %-8s | %-16s\n",
        "No", "Nama", "Jumlah", "Harga"
    );

    int index = 1;
    for (Product product : products) {
      System.out.printf(
          "%3d | %-24s | %8d | Rp%14s\n",
          index,
          product.getName(),
          product.getAmount(),
          Utils.currencyFormat(product.getPrice())
      );
      index++;
    }
    Utils.printLine("=", 60);
  }
}
