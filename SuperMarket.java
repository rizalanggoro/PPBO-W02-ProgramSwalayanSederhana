/*
  File logic untuk program SuperMarket.

  File ini mengatur beberapa logic berikut.
  - menambahkan initial product
  - sell product dan menambahkan history transaksi
  - restok product
  - create product

  Selain itu, file ini digunakan untuk menyimpan beberapa
  data seperti:
  - konstanta PPN (Pajak Pertambahan Nilai)
  - jumlah transaksi
  - array untuk menyimpan list products
  - array untuk menyimpan history transactions
  - total uang kas super market
 */

import java.util.ArrayList;
import java.util.Collections;

public class SuperMarket {
  public static final int PPN = 11;
  public static int totalTransaction = 0;
  private final ArrayList<Product> arrayListProduct = new ArrayList<>();
  private final ArrayList<Transaction> arrayListTransactionHistory = new ArrayList<>();
  private int totalBalance = 0;

  private int getProductIndexByName(String name) {
    return this.arrayListProduct.indexOf(new Product(name));
  }

  public Transaction sellProduct(
      String name,
      int amount
  ) throws Exception {
    // validasi nama produk
    if (name.trim().isEmpty())
      throw new Exception("Nama produk tidak boleh kosong");

    // validasi jumlah produk
    if (amount <= 0)
      throw new Exception("Jumlah produk harus > 0");

    // cek ketersediaan nama produk
    int productIndex = getProductIndexByName(name);
    if (productIndex == -1)
      throw new Exception("Produk dengan nama " + name + " tidak ditemukan!");

    // cek ketersedian jumlah produk
    Product product = this.arrayListProduct.get(productIndex);
    if (product.getAmount() < amount)
      throw new Exception("Jumlah produk tidak mencukupi!");

    // update list produk
    product.setAmount(product.getAmount() - amount);
    this.arrayListProduct.remove(productIndex);
    this.arrayListProduct.add(productIndex, product);

    int totalPrice = product.getPrice() * amount;
    double totalPPN = totalPrice * ((double) PPN / 100);
    final Transaction transaction = new Transaction(
        product,
        amount,
        totalPrice,
        (int) totalPPN
    );

    this.arrayListTransactionHistory.add(transaction);
    totalTransaction = this.arrayListTransactionHistory.size();
    this.totalBalance += (transaction.getTotalPrice() + transaction.getTotalPPN());

    return transaction;
  }

  public void restockProduct(
      String name,
      int amount
  ) throws Exception {
    // validasi nama produk
    if (name.trim().isEmpty())
      throw new Exception("Nama produk tidak boleh kosong");

    // validasi jumlah produk
    if (amount <= 0)
      throw new Exception("Jumlah produk harus > 0");

    // cek ketersediaan nama produk
    int productIndex = arrayListProduct.indexOf(new Product(name));
    if (productIndex == -1)
      throw new Exception("Produk dengan nama " + name + " tidak ditemukan!");

    // update list produk
    Product product = arrayListProduct.get(productIndex);
    product.setAmount(product.getAmount() + amount);
    arrayListProduct.remove(productIndex);
    arrayListProduct.add(productIndex, product);
  }

  public void createProduct(
      String name,
      int price,
      int amount
  ) throws Exception {
    // validasi nama produk
    if (name.trim().isEmpty())
      throw new Exception("Nama produk tidak boleh kosong");

    if (name.length() > 24)
      throw new Exception("Nama produk tidak boleh melebihi 24 karakter");

    if (getProductIndexByName(name) != -1)
      throw new Exception("Produk dengan nama ".concat(name).concat(" sudah tersedia!"));

    // validasi jumlah produk
    if (amount <= 0)
      throw new Exception("Jumlah produk harus > 0");

    // validasi harga produk
    if (price <= 0)
      throw new Exception("Harga produk harus > 0");

    this.arrayListProduct.add(new Product(
        name, price, amount
    ));
  }

  public void initializeProducts() {
    Product[] products = {
        new Product("POCO F1", 1000000, 10),
        new Product("POCO F2", 1500000, 5),
        new Product("POCO F3", 2000000, 9),
    };
    Collections.addAll(arrayListProduct, products);
  }

  public int getTotalBalance() {
    return totalBalance;
  }

  public ArrayList<Transaction> getTransactionHistories() {
    return this.arrayListTransactionHistory;
  }

  public ArrayList<Product> getProducts() {
    return this.arrayListProduct;
  }
}
