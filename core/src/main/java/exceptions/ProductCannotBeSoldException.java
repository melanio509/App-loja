package atec.poo.loja.core.exceptions;

public class ProductCannotBeSoldException extends Exception {
  private int productId;

  public ProductCannotBeSoldException(int productId) {
    this.productId = productId;
  }

  public int getProductId() {
    return productId;
  }
}
