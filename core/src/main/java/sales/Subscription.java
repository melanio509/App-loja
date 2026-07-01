package atec.poo.loja.core.sales;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.products.Product;

import java.time.LocalDate;

public class Subscription {
    private Client client;
    private Product product;
    private LocalDate date;
    private SubscriptionStatus status;

    public Subscription(Client client, Product product, LocalDate date) {
        this.client = client;
        this.product = product;
        this.date = date;
        this.status = SubscriptionStatus.ATIVA;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDate getDate() {
        return date;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
}
