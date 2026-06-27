package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppNoSuchProductException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoSuchProductException;
import atec.poo.loja.core.products.*;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.List;

/**
 * Comando responsável por apresentar o histórico
 * de preços de um produto.
 */
public class DoShowProductPriceHistory
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por apresentar
     * o histórico de preços.
     *
     * @param storeManager gestor da loja
     */
    public DoShowProductPriceHistory(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.SHOW_PRODUCT_PRICE_HISTORY
        );
    }

    /**
     * Apresenta o histórico de preços de um produto.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int id = Prompt.productId();

        try {
            Product product = this.getReceptor().showProduct(id);

            Message.productPriceHistoryHeader(
                    product.getId(),
                    product.getName(),
                    product.getPriceHistory().size()
            );

            for(PriceHistory priceHistory : product.getPriceHistory()) {
                Message.productPriceHistoryItem(
                        priceHistory.getDate().toString(),
                        priceHistory.getPrice()
                );
            }
        } catch (NoSuchProductException e) {
            throw new AppNoSuchProductException(e.getId());
        }

    }
}