package atec.poo.loja.core.products;

import java.time.LocalDate;

public class Clothing extends Product{
    private Size size;

    public Clothing(int id, String name, double price, int stock, String size, LocalDate today) {
        super(id, name, ProductCategory.VESTUARIO, price, stock, today);
        this.size = Size.valueOf(size);
    }

    public Size getSize() {
        return size;
    }

    @Override
    public boolean canBeSold() { return true; }
}
