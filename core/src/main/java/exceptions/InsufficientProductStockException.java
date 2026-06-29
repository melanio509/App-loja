package atec.poo.loja.core.exceptions;

public class InsufficientProductStockException extends Exception {
    private int productId;
    private int requestedQuantity;
    private int availableStock;

    public InsufficientProductStockException(int productId, int requestedQuantity, int availableStock) {
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableStock = availableStock;
    }

    public int getProductId() {
        return productId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableStock() {
        return availableStock;
    }
}
