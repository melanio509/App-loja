package atec.poo.loja.core.exceptions;

public class InvalidSaleQuantityException extends Exception {
  private int quantity;

  public InvalidSaleQuantityException(int quantity) {
    this.quantity = quantity;
  }

  public int getQuantity() {
    return quantity;
  }
}
