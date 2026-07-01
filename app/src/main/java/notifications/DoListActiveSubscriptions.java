package atec.poo.loja.app.notifications;

import atec.poo.loja.app.exceptions.AppNoActiveSubscriptionsException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoActiveSubscriptionsException;
import atec.poo.loja.core.sales.Subscription;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por listar as subscrições ativas.
 */
public class DoListActiveSubscriptions
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por listar subscrições ativas.
     *
     * @param storeManager gestor da loja
     */
    public DoListActiveSubscriptions(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.LIST_ACTIVE_SUBSCRIPTIONS
        );
    }

    /**
     * Apresenta todas as subscrições ativas da loja.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        try {
            ArrayList<Subscription> activeSubscriptions = this.getReceptor().listActiveSubscriptions();

            Message.activeSubscriptionsHeader(
                    activeSubscriptions.size()
            );

            for (Subscription s : activeSubscriptions)
                Message.activeSubscriptionItem(
                        s.getDate().toString(),
                        s.getClient().getName(),
                        s.getClient().getId(),
                        s.getProduct().getName(),
                        s.getProduct().getId(),
                        s.getProduct().getPrice(),
                        s.getProduct().getStock()
            );

        } catch (NoActiveSubscriptionsException e) {
            throw new AppNoActiveSubscriptionsException();
        }

    }
}