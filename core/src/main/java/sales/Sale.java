package atec.poo.loja.core.sales;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.products.Product;

import java.time.LocalDate;

public class Sale implements Comparable<Sale>{
    private int id;
    private LocalDate date;
    private Client client;
    private Product product;
    private double unitPrice;
    private int quantity;

    public Sale(int id, LocalDate date, Client client, Product product, int quantity) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.product = product;
        this.unitPrice = product.getPrice();
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return unitPrice * quantity;
    }

    @Override
    public int compareTo(Sale s) {
        if (this.date.equals(s.getDate()))
            return this.id - s.getId();

        return this.date.compareTo(s.getDate());
    }
}












