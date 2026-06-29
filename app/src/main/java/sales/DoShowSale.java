package atec.poo.loja.app.sales;

import atec.poo.loja.app.exceptions.AppNoSuchSaleException;
import atec.poo.loja.app.sales.Message;
import atec.poo.loja.app.sales.Prompt;
import atec.poo.loja.app.exceptions.AppNoSuchClientException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoSuchSaleException;
import atec.poo.loja.core.sales.Sale;
import atec.poo.loja.core.exceptions.NoSuchClientException;
import atec.poo.loja.core.sales.Sale;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

/**
 * Comando responsável por apresentar os detalhes de uma venda.
 */
public class DoShowSale
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por apresentar uma venda.
     *
     * @param storeManager gestor da loja
     */
    public DoShowSale(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.SHOW_SALE
        );
    }

    /**
     * Apresenta os detalhes de uma venda.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        Sale sale;

        int id = Prompt.saleId();

        try {
            sale = this.getReceptor().showSale(id);
            Message.saleDetails(
                    sale.getId(),
                    sale.getDate().toString(),
                    sale.getClient().getName(),
                    sale.getClient().getId(),
                    sale.getProduct().getName(),
                    sale.getProduct().getId(),
                    sale.getUnitPrice(),
                    sale.getQuantity(),
                    sale.getTotal()
            );
        } catch (NoSuchSaleException e) {
            throw new AppNoSuchSaleException(e.getId());
        }

    }
}