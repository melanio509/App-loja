package atec.poo.loja.app.clients;

import atec.poo.loja.core.StoreManager;
import atec.poo.ui.Comando;
import atec.poo.ui.exceptions.DialogException;

/**
 * Comando responsável por registar um novo cliente.
 */
public class DoRegisterClient extends Comando<StoreManager> {

    /**
     * Cria o comando responsável por registar um cliente.
     *
     * @param storeManager gestor da loja
     */
    public DoRegisterClient(StoreManager storeManager) {
        super(storeManager, Label.REGISTER_CLIENT);
    }

    /**
     * Regista um novo cliente.
     *
     * @throws DialogException se ocorrer um erro durante a interação
     */
    @Override
    public void executar() throws DialogException {

        //TODO: A implementar pelos alunos
    }
}