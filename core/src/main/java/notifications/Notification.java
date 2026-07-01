package atec.poo.loja.core.notifications;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.products.Product;

import java.time.LocalDate;

public class Notification {

    private int id;
    private LocalDate date;
    private NotificationType type;
    private Client client;
    private Product product;
    private double price;
    private boolean read;

    public Notification(int id, LocalDate date, NotificationType type, Client client, Product product, double price) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.client = client;
        this.product = product;
        this.price = price;
        this.read = false;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public NotificationType getType() {
        return type;
    }

    public Client getClient() {
        return client;
    }
    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
