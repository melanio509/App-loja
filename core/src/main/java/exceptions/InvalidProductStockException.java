package atec.poo.loja.core.exceptions;

public class InvalidProductStockException extends Exception {
    private int stock;

    public InvalidProductStockException(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }
}
