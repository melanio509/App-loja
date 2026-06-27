package atec.poo.loja.core.exceptions;

import java.time.LocalDate;

public class InvalidWarrantyMonthsException extends Exception {
    private int warrantyMonths;

    public InvalidWarrantyMonthsException(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }
}
