package atec.poo.loja.core.exceptions;

public class NoProductsInCategoryException extends Exception {
  private int category;

  public NoProductsInCategoryException(int category) {
    this.category = category;
  }

  public int getCategory() {
    return category;
  }
}
