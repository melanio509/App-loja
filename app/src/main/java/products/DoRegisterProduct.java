package atec.poo.loja.app.products;

import atec.poo.loja.app.exceptions.AppInvalidProductPriceException;
import atec.poo.loja.app.exceptions.AppInvalidProductStockException;
import atec.poo.loja.app.exceptions.AppInvalidWarrantyMonthsException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.InvalidProductPriceException;
import atec.poo.loja.core.exceptions.InvalidProductStockException;
import atec.poo.loja.core.exceptions.InvalidWarrantyMonthsException;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.time.LocalDate;

/**
 * Comando responsável por registar um novo produto.
 */
public class DoRegisterProduct
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por registar um produto.
     *
     * @param storeManager gestor da loja
     */
    public DoRegisterProduct(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.REGISTER_PRODUCT
        );
    }

    private int registProduct ()
    {
        return 0;
    }

    /**
     * Regista um novo produto.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int id;
        int category = Prompt.productCategory();
        String name = Prompt.productName();
        double price = Prompt.productPrice();
        int stock = Prompt.initialStock();

        try {
            switch (category) {
                case 1:
                    id = this.getReceptor().registProduct(
                            name,
                            price,
                            stock,
                            Prompt.expirationDate());
                    break;
                case 2:
                    id = this.getReceptor().registProduct(
                            name,
                            price,
                            stock,
                            Prompt.warrantyMonths());
                    break;
                case 3:
                    id = this.getReceptor().registProduct(
                            name,
                            price,
                            stock,
                            Prompt.clothingSize());
                    break;
                default:
                    return;
            }

            Message.productRegistered(id);

        } catch (InvalidProductPriceException e) {
            throw new AppInvalidProductPriceException(e.getPrice());
        } catch (InvalidProductStockException e) {
            throw new AppInvalidProductStockException(e.getStock());
        } catch (InvalidWarrantyMonthsException e) {
            throw new AppInvalidWarrantyMonthsException(e.getWarrantyMonths());
        }
    }
}
