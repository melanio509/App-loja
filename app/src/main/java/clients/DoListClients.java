package atec.poo.loja.app.clients;

import atec.poo.loja.app.exceptions.AppNoClientsRegisteredException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.NoClientsRegisteredException;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;

/**
 * Comando responsável por listar os clientes registados.
 */
public class DoListClients extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por listar os clientes.
     *
     * @param storeManager gestor da loja
     */
    public DoListClients(StoreManager storeManager) {
        super(storeManager, Label.LIST_CLIENTS);
    }

    /**
     * Apresenta a lista de clientes registados.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar() throws DialogException {
        ArrayList<Client> clients;

        try {
            clients = this.getReceptor().listClients();
        } catch (NoClientsRegisteredException e) {
            throw new AppNoClientsRegisteredException();
        }

        Message.clientsListHeader(
                clients.size()
        );

        for (Client client : clients) {
            Message.clientsListItem(
                    client.getId(),
                    client.getName(),
                    client.getNif()
            );

        }
    }
}