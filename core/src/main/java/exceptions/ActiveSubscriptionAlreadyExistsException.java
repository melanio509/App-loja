package atec.poo.loja.core.exceptions;

public class ActiveSubscriptionAlreadyExistsException extends Exception {
  private int clientId;
  private int productId;

  public ActiveSubscriptionAlreadyExistsException(int clientId, int productId) {
    this.clientId = clientId;
    this.productId = productId;
  }

  public int getClientId() {
    return clientId;
  }

  public int getProductId() {
    return productId;
  }
}
