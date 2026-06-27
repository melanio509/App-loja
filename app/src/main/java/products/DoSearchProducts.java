package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppNoProductsFoundException;
import atec.poo.loja.app.products.Message;
import atec.poo.loja.app.products.Prompt;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoClientsFoundException;
import atec.poo.loja.core.exceptions.NoProductsFoundException;
import atec.poo.loja.core.products.Product;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por pesquisar produtos pelo nome.
 */
public class DoSearchProducts
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por pesquisar produtos.
     *
     * @param storeManager gestor da loja
     */
    public DoSearchProducts(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.SEARCH_PRODUCTS
        );
    }

    /**
     * Pesquisa produtos pelo nome.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        ArrayList<Product> products;

        String searchTerm = Prompt.searchTerm();

        try {
            products = this.getReceptor().searchProducts(searchTerm);
        } catch (NoProductsFoundException e) {
            throw new AppNoProductsFoundException(e.searchTerm());
        }

        Message.productSearchHeader(
                searchTerm,
                products.size()
        );

        for (Product product: products) {
            Message.productSearchItem(
                    product.getId(),
                    product.getName(),
                    product.getCategory().getName(),
                    product.getPrice(),
                    product.getStock()
            );
        }

    }
}