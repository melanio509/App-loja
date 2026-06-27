package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppInvalidProductPriceException;
import atec.poo.loja.app.exceptions.AppNoSuchProductException;
import atec.poo.loja.app.products.Message;
import atec.poo.loja.app.products.Prompt;
import atec.poo.loja.app.exceptions.AppNoSuchClientException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.InvalidProductPriceException;
import atec.poo.loja.core.exceptions.NoSuchClientException;
import atec.poo.loja.core.exceptions.NoSuchProductException;
import atec.poo.loja.core.products.Product;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

/**
 * Comando responsável por alterar o preço de um produto.
 */
public class DoChangeProductPrice
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por alterar o preço de um produto.
     *
     * @param storeManager gestor da loja
     */
    public DoChangeProductPrice(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.CHANGE_PRODUCT_PRICE
        );
    }

    /**
     * Altera o preço de um produto.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int id = Prompt.productId();

        try {
            Product product = this.getReceptor().showProduct(id);

            double oldPrice = product.getPrice();
            double newPrice = Prompt.newProductPrice();

            this.getReceptor().changeProductPrice(id, newPrice);
            Message.productPriceChanged(id, oldPrice, newPrice);
        } catch (NoSuchProductException e) {
            throw new AppNoSuchProductException(e.getId());
        } catch (InvalidProductPriceException e) {
            throw new AppInvalidProductPriceException(e.getPrice());
        }
    }
}