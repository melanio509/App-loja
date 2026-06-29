package atec.poo.loja.app.sales;

import atec.poo.loja.app.exceptions.*;
import atec.poo.loja.app.sales.Message;
import atec.poo.loja.app.sales.Prompt;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.*;
import atec.poo.loja.core.products.Product;
import atec.poo.loja.core.sales.Sale;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.time.LocalDate;

/**
 * Comando responsável por registar uma venda.
 */
public class DoRegisterSale
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por registar uma venda.
     *
     * @param storeManager gestor da loja
     */
    public DoRegisterSale(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.REGISTER_SALE
        );
    }

    /**
     * Regista uma nova venda.
     *
     * <p>Quando não existe stock suficiente para satisfazer
     * a quantidade solicitada, permite ao cliente subscrever
     * notificações relativas ao produto.</p>
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        Client client = null;
        Product product = null;
        try {
            int clientId = Prompt.clientId();
            client = this.getReceptor().showClient(clientId);

            int productId = Prompt.productId();
            product = this.getReceptor().showProduct(productId);

            int quantity = Prompt.quantity();

            Sale newSale = this.getReceptor().registSale(client, product, quantity);
            Message.saleRegistered(newSale.getId(), newSale.getTotal());
        } catch (NoSuchClientException e) {
            throw new AppNoSuchClientException(e.getId());
        } catch (NoSuchProductException e) {
            throw new AppNoSuchProductException(e.getId());
        } catch (ProductCannotBeSoldException e) {
            throw new AppProductCannotBeSoldException(e.getProductId());
        } catch (InsufficientProductStockException e) {

            if (Prompt.subscribeProductNotifications()) {
                try {
                    this.getReceptor()
                            .subscribeProductNotification(client, product);
                    Message.subscriptionCreated(
                            client.getId(),
                            product.getId());
                } catch (ActiveSubscriptionAlreadyExistsException ex) {
                    throw new AppActiveSubscriptionAlreadyExistsException(ex.getClientId(), ex.getProductId());
                }
            }

            throw new AppInsufficientProductStockException(
                    e.getProductId(),
                    e.getRequestedQuantity(),
                    e.getAvailableStock());

        } catch (InvalidSaleQuantityException e) {
            throw new AppInvalidSaleQuantityException(e.getQuantity());
        }

    }
}