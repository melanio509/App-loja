package atec.poo.loja.app.clients;

import atec.poo.loja.app.exceptions.AppNoClientsFoundException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.NoClientsFoundException;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.util.ArrayList;
import java.util.List;

/**
 * Comando responsável por pesquisar clientes.
 */
public class DoSearchClients
        extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por pesquisar clientes.
     *
     * @param storeManager gestor da loja
     */
    public DoSearchClients(
            StoreManager storeManager
    ) {
        super(
                storeManager,
                Label.SEARCH_CLIENTS
        );
    }

    /**
     * Pesquisa clientes pelo nome, NIF ou status.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar()
            throws DialogException {

        ArrayList<Client> clients;

        String searchTerm = Prompt.searchTerm();

        try {
            clients = this.getReceptor().searchClients(searchTerm);
        } catch (NoClientsFoundException e) {
            throw new AppNoClientsFoundException(e.searchTerm());
        }

        Message.clientsListHeader(
                clients.size()
        );

        for (Client client: clients) {
            Message.clientSearchItem(
                    client.getId(),
                    client.getName(),
                    client.getNif(),
                    client.getStatus().toString()
            );
        }
    }
}