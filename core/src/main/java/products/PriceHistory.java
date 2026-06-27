package atec.poo.loja.core.products;

import java.time.LocalDate;

public class PriceHistory {
    private LocalDate date;
    private double price;

    public PriceHistory(LocalDate date, double price) {
        this.date = date;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f €", date, price);
    }
}
