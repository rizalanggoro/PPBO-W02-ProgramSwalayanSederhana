/*
  File object Product (barang).
 */

public class Product {
  private String name;
  private int price;
  private int amount;

  public Product(String name) {
    this.name = name;
  }

  public Product(
      String name,
      int price,
      int amount
  ) {
    this.name = name;
    this.price = price;
    this.amount = amount;
  }

  public String getName() {
    return name;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Product)) return false;
    return ((Product) obj).getName().equalsIgnoreCase(name);
  }
}
