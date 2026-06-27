package atec.poo.loja.core.exceptions;

public class InvalidProductPriceException extends Exception {
  private double price;

  public InvalidProductPriceException(double price) {
    this.price = price;
  }

  public double getPrice() {
    return price;
  }
}
