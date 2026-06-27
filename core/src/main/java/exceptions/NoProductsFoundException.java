package atec.poo.loja.core.exceptions;

public class NoProductsFoundException extends Exception {
    private String searchTerm;

    public NoProductsFoundException(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String searchTerm() {
        return searchTerm;
    }
}
