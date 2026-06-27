package atec.poo.loja.app.clients;

import atec.poo.loja.app.exceptions.AppNoSuchClientException;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.NoSuchClientException;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Comando responsável por apresentar os detalhes de um cliente.
 */
public class DoShowClient extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por apresentar um cliente.
     *
     * @param storeManager gestor da loja
     */
    public DoShowClient(StoreManager storeManager) {
        super(storeManager, Label.SHOW_CLIENT);
    }

    /**
     * Apresenta os detalhes de um cliente.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar() throws DialogException {
        int id = Prompt.clientId();

        Client client = null;

        try {
            client = this.getReceptor().getClient(id);
        } catch (NoSuchClientException e) {
            throw new AppNoSuchClientException(e.getId());
        }

        Message.clientDetails(
                client.getId(),
                client.getName(),
                client.getNif(),
                client.getBirthDate().toString(),
                client.getAge(this.getReceptor().getToday()),
                client.getStatus().toString()
        );
    }
}