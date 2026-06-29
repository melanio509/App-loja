package atec.poo.loja.core.exceptions;

public class NoSuchSaleException extends Exception {
    private int id;

    public NoSuchSaleException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
