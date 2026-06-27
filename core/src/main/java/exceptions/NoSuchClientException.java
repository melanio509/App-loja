package atec.poo.loja.core.exceptions;

public class NoSuchClientException extends Exception {
    private int id;

    public NoSuchClientException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
