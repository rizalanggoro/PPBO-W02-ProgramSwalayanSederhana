/*
  File object Transaction.
 */

public class Transaction {
  private Product product;
  private int amount;
  private int totalPrice;
  private int totalPPN;

  public Transaction(
      Product product,
      int amount,
      int totalPrice,
      int totalPPN
  ) {
    this.product = product;
    this.amount = amount;
    this.totalPrice = totalPrice;
    this.totalPPN = totalPPN;
  }

  public int getAmount() {
    return amount;
  }

  public Product getProduct() {
    return product;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public int getTotalPPN() {
    return totalPPN;
  }
}
