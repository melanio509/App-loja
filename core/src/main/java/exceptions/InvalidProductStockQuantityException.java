package atec.poo.loja.core.exceptions;

public class InvalidProductStockQuantityException extends Exception {
    private int quantity;

    public InvalidProductStockQuantityException(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
