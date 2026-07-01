package atec.poo.loja.core.exceptions;

public class NoClientSalesException extends Exception {
    private int clientId;
    private String clientName;

    public NoClientSalesException (int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }
}
