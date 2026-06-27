package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppInvalidProductPriceException;
import atec.poo.loja.app.exceptions.AppInvalidProductStockQuantityException;
import atec.poo.loja.app.exceptions.AppNoSuchProductException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.InvalidProductPriceException;
import atec.poo.loja.core.exceptions.InvalidProductStockQuantityException;
import atec.poo.loja.core.exceptions.NoSuchProductException;
import atec.poo.loja.core.products.Product;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

/**
 * Comando responsável por adicionar unidades ao stock
 * atual de um produto.
 */
public class DoAddProductStock
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por adicionar stock a um produto.
     *
     * @param storeManager gestor da loja
     */
    public DoAddProductStock(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.ADD_PRODUCT_STOCK
        );
    }

    /**
     * Acrescenta unidades ao stock atual de um produto.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int id = Prompt.productId();

        try {
            Product product = this.getReceptor().showProduct(id);
            int quantity = Prompt.stockQuantity();

            this.getReceptor().addProductStock(id, quantity);
            Message.productStockAdded(id, quantity, product.getStock());
        } catch (NoSuchProductException e) {
            throw new AppNoSuchProductException(e.getId());
        } catch (InvalidProductStockQuantityException e) {
            throw new AppInvalidProductStockQuantityException(e.getQuantity());
        }

    }
}