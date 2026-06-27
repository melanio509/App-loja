package atec.poo.loja.core.products;

public enum ProductCategory {
    ALIMENTACAO (1, "ALIMENTAÇÃO"),
    ELETRONICA (2, "ELETRÓNICA"),
    VESTUARIO (3, "VESTUÁRIO");

    private final int code;
    private final String name;

    ProductCategory(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
