package atec.poo.loja.app.notifications;

import atec.poo.loja.app.exceptions.AppNoNotificationsRegisteredException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.NoNotificationsRegisteredException;
import atec.poo.loja.core.notifications.Notification;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por apresentar o histórico
 * completo de notificações.
 */
public class DoListNotificationHistory
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por listar
     * o histórico de notificações.
     *
     * @param storeManager gestor da loja
     */
    public DoListNotificationHistory(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.LIST_NOTIFICATION_HISTORY
        );
    }

    /**
     * Apresenta o histórico completo de notificações.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        try {
            ArrayList<Notification> notificationHistory = this.getReceptor().listNotificationHistory();

            Message.notificationHistoryHeader(
                    notificationHistory.size()
            );

            for (Notification n : notificationHistory) {
                Message.notificationHistoryItem(
                        n.getId(),
                        n.getDate().toString(),
                        n.getType().toString(),
                        n.getClient().getName(),
                        n.getClient().getId(),
                        n.getProduct().getName(),
                        n.getProduct().getId(),
                        n.getPrice(),
                        (n.isRead()) ? "READ" : "UNREAD"
                );
            }

        } catch (NoNotificationsRegisteredException e) {
            throw new AppNoNotificationsRegisteredException();
        }
    }
}