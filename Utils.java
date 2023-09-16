/*
  File utilitas.
 */

import java.text.DecimalFormat;
import java.util.Scanner;

public class Utils {
  public static String currencyFormat(int num) {
    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
    return decimalFormat.format(num);
  }

  public static void printLine(
      String line,
      int n
  ) {
    for (int a = 0; a < n; a++)
      System.out.print(line);
    System.out.println();
  }

  public static void enterToContinue(Scanner scanner) {
    System.out.println("\nTekan [enter] untuk melanjutkan...");
    String a = scanner.nextLine();
  }
}
