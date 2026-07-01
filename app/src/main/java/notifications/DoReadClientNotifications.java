package atec.poo.loja.app.notifications;

import atec.poo.loja.app.exceptions.AppNoSuchClientException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoSuchClientException;
import atec.poo.loja.core.notifications.Notification;
import atec.poo.loja.core.sales.Subscription;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por apresentar as notificações
 * ainda não lidas de um cliente.
 */
public class DoReadClientNotifications
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por consultar notificações.
     *
     * @param storeManager gestor da loja
     */
    public DoReadClientNotifications(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.READ_CLIENT_NOTIFICATIONS
        );
    }

    /**
     * Apresenta as notificações ainda não lidas
     * e permite cancelar as subscrições do cliente.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        int clientId = Prompt.clientId();

        try {
            ArrayList<Notification> notifications = this.getReceptor().readClientNotifications(clientId);
            int size = (notifications.getFirst().getId() < 0) ? 0 : notifications.size();

            Message.clientNotificationsHeader(
                    clientId,
                    notifications.getFirst().getClient().getName(),
                    size
            );

            if(size > 0)
                for (Notification n : notifications)
                    Message.clientNotificationItem(
                            n.getId(),
                            n.getDate().toString(),
                            n.getType().toString(),
                            n.getProduct().getName(),
                            n.getProduct().getId(),
                            n.getPrice()
                    );

            if(Prompt.cancelActiveSubscriptions()) {
                ArrayList<Subscription> cancelledSubscriptions =
                        this.getReceptor().cancelActiveSubscriptions(clientId);
                Message.clientSubscriptionsCancelled(
                        clientId,
                        cancelledSubscriptions.size()
                );
            } else {
                Message.clientSubscriptionsMaintained(
                        clientId
                );
            }

        } catch (NoSuchClientException e) {
            throw new AppNoSuchClientException(e.getId());
        }
    }
}