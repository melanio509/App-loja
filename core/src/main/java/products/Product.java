package atec.poo.loja.core.products;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Product implements Comparable<Product>{
    private int id;
    private String name;
    private double price;
    private int stock;
    private ProductCategory category;
    private ArrayList<PriceHistory> priceHistory;

    protected Product(int id, String name, ProductCategory category, double price, int stock,  LocalDate today) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.priceHistory = new ArrayList<>();

        addPriceHistory(today, price);
    }

    private void addPriceHistory(LocalDate today, double price) {
        priceHistory.add(new PriceHistory(today, price));
    }

    public void updatePrice(LocalDate today, double newPrice) {
        this.price = newPrice;
        addPriceHistory(today, newPrice);
    }

    public void increasePrice(LocalDate today, double percentage) {

        double newPrice = this.price * (1 + percentage / 100);

        updatePrice(today, newPrice);
    }

    public abstract boolean canBeSold(LocalDate today);

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void updateStock(int quantity){
        this.stock += quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public ArrayList<PriceHistory> getPriceHistory() {
        return priceHistory;
    }

    @Override
    public String toString() {

        return String.format(
                "Produto [%03d] - %s - %.2f € - Stock: %d",
                id,
                name,
                price,
                stock
        );
    }

    @Override
    public int compareTo(Product p){
        if(this.name.equals(p.getName()))
            return this.id - p.getId();

        return this.name.compareTo(p.getName());
    };
}
