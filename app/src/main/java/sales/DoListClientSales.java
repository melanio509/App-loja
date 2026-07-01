package atec.poo.loja.app.sales;

import atec.poo.loja.app.exceptions.AppNoClientSalesException;
import atec.poo.loja.app.exceptions.AppNoSuchClientException;
import atec.poo.loja.app.sales.Message;
import atec.poo.loja.app.sales.Prompt;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoClientSalesException;
import atec.poo.loja.core.exceptions.NoProductsInCategoryException;
import atec.poo.loja.core.exceptions.NoProductsRegisteredException;
import atec.poo.loja.core.exceptions.NoSuchClientException;
import atec.poo.loja.core.sales.Sale;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por listar as vendas
 * realizadas por um cliente.
 */
public class DoListClientSales
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por listar
     * as vendas de um cliente.
     *
     * @param storeManager gestor da loja
     */
    public DoListClientSales(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.LIST_CLIENT_SALES
        );
    }

    /**
     * Apresenta as vendas realizadas por um cliente.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {
        int clientId = Prompt.clientId();
        ArrayList<Sale> salesByClient;

        try {
            salesByClient = this.getReceptor().listSalesByClient(clientId);

            Message.clientSalesListHeader(
                    salesByClient.getFirst().getClient().getId(),
                    salesByClient.getFirst().getClient().getName(),
                    salesByClient.size()
            );

            for (Sale sale: salesByClient)
                Message.clientSalesListItem(
                    sale.getId(),
                    sale.getDate().toString(),
                    sale.getProduct().getName(),
                    sale.getQuantity(),
                    sale.getTotal());

        } catch (NoClientSalesException e) {
            throw new AppNoClientSalesException(e.getClientId(), e.getClientName());
        } catch (NoSuchClientException e) {
            throw new AppNoSuchClientException(e.getId());
        }
    }
}