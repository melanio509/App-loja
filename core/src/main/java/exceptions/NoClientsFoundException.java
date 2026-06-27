package atec.poo.loja.core.exceptions;

public class NoClientsFoundException extends Exception {
  private String searchTerm;

  public NoClientsFoundException(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public String searchTerm() {
    return searchTerm;
  }
}
