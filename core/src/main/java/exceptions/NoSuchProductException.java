package atec.poo.loja.core.exceptions;

public class NoSuchProductException extends Exception {
  private int id;

  public NoSuchProductException(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
