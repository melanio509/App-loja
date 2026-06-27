package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppInvalidCategoryPriceIncreaseException;
import atec.poo.loja.app.exceptions.AppNoProductsInCategoryException;
import atec.poo.loja.app.exceptions.AppNoProductsRegisteredException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.InvalidCategoryPriceIncreaseException;
import atec.poo.loja.core.exceptions.NoProductsInCategoryException;
import atec.poo.loja.core.exceptions.NoProductsRegisteredException;
import atec.poo.loja.core.products.Product;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;

/**
 * Comando responsável por aumentar os preços dos produtos
 * pertencentes a uma categoria.
 */
public class DoIncreaseCategoryPrices
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por aumentar os preços
     * dos produtos de uma categoria.
     *
     * @param storeManager gestor da loja
     */
    public DoIncreaseCategoryPrices(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.INCREASE_CATEGORY_PRICES
        );
    }

    /**
     * Aumenta os preços dos produtos de uma categoria.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int category = Prompt.productCategory();
        double percentageIncrease = Prompt.percentageIncrease();
        ArrayList<Product> productsByCategory;

        try {
            productsByCategory = this.getReceptor().increaseCategoryPrices(category, percentageIncrease);
            Message.categoryPricesIncreased(
                    productsByCategory.get(0).getCategory().getName(),
                    percentageIncrease,
                    productsByCategory.size()
            );
        } catch (NoProductsInCategoryException e) {
            throw new AppNoProductsInCategoryException(e.getCategory());
        } catch (NoProductsRegisteredException e) {
            throw new AppNoProductsRegisteredException();
        } catch (InvalidCategoryPriceIncreaseException e) {
            throw new AppInvalidCategoryPriceIncreaseException(e.getPercentageIncrease());
        }
    }
}
