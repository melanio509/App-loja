package atec.poo.loja.core.products;

import atec.poo.loja.core.Store;

import java.time.LocalDate;

public class Food extends Product{
    private LocalDate expirationDate;

    public Food(int id, String name, double price, int stock, LocalDate expirationDate, LocalDate today) {
        super(id, name, ProductCategory.ALIMENTACAO, price, stock, today);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean canBeSold(LocalDate today) {
        return !today.isAfter(expirationDate);
    }
}
