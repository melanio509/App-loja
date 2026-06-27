package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppNoSuchProductException;
import atec.poo.loja.app.products.Message;
import atec.poo.loja.app.products.Prompt;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoSuchProductException;
import atec.poo.loja.core.products.Clothing;
import atec.poo.loja.core.products.Electronic;
import atec.poo.loja.core.products.Food;
import atec.poo.loja.core.products.Product;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

/**
 * Comando responsável por apresentar os detalhes de um produto.
 */
public class DoShowProduct
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por apresentar um produto.
     *
     * @param storeManager gestor da loja
     */
    public DoShowProduct(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.SHOW_PRODUCT
        );
    }

    /**
     * Apresenta os detalhes de um produto.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int id = Prompt.productId();

        try {
            Product product = this.getReceptor().showProduct(id);

            if (product instanceof Food food) {

                Message.foodProductDetails(
                        food.getId(),
                        food.getName(),
                        food.getCategory().toString(),
                        food.getPrice(),
                        food.getStock(),
                        food.getExpirationDate().toString());

            }
            else if (product instanceof Electronic electronic) {

                Message.electronicProductDetails(
                        electronic.getId(),
                        electronic.getName(),
                        electronic.getCategory().toString(),
                        electronic.getPrice(),
                        electronic.getStock(),
                        electronic.getWarrantyMonths());

            }
            else if (product instanceof Clothing clothing) {

                Message.clothingProductDetails(
                        clothing.getId(),
                        clothing.getName(),
                        clothing.getCategory().toString(),
                        clothing.getPrice(),
                        clothing.getStock(),
                        clothing.getSize().toString());
            }
        } catch (NoSuchProductException e) {
            throw new AppNoSuchProductException(e.getId());
        }
    }
}