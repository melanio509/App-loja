package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppNoProductsInCategoryException;
import atec.poo.loja.app.exceptions.AppNoProductsRegisteredException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoProductsInCategoryException;
import atec.poo.loja.core.exceptions.NoProductsRegisteredException;
import atec.poo.loja.core.products.Product;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por listar os produtos
 * pertencentes a uma categoria.
 */
public class DoListProductsByCategory
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por listar produtos por categoria.
     *
     * @param storeManager gestor da loja
     */
    public DoListProductsByCategory(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.LIST_PRODUCTS_BY_CATEGORY
        );
    }

    /**
     * Apresenta os produtos pertencentes a uma categoria.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int category = Prompt.productCategory();

        ArrayList<Product> productsByCategory;

        try {
            productsByCategory = this.getReceptor().listProductsByCategory(category);

            Message.productsByCategoryListHeader(
                    productsByCategory.get(0).getCategory().toString(),
                    productsByCategory.size()
            );

            for (Product product : productsByCategory) {
                Message.productsListItem(
                        product.getId(),
                        product.getName(),
                        product.getCategory().getName(),
                        product.getPrice(),
                        product.getStock()
                );

            }
        } catch (NoProductsRegisteredException e) {
            throw new AppNoProductsRegisteredException();
        } catch (NoProductsInCategoryException e) {
            throw new AppNoProductsInCategoryException(e.getCategory());
        }
    }
}