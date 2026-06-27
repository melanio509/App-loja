package atec.poo.loja.core.products;

import atec.poo.loja.core.exceptions.InvalidWarrantyMonthsException;

import java.time.LocalDate;

public class Electronic extends Product{
    private int warrantyMonths;

    public Electronic(int id, String name, double price, int stock, int warrantyMonths, LocalDate today) {
        super(id, name, ProductCategory.ELETRONICA, price, stock, today);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public boolean canBeSold() {
        return true;
    }
}
