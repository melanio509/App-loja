package atec.poo.loja.core;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Scanner;

public class StoreManager {
    private Store store;

    public StoreManager() {
        this.store = new Store();
    }

    /**
     * Devolve a data simulada atual da aplicação.
     *
     * @return data atual da loja
     */
    public LocalDate getToday() {
        return this.store.getToday();
    }

    /**
     * Avança a data simulada da aplicação.
     *
     * @param numberOfDays número de dias a avançar
     * @return {@code true} se a data tiver sido avançada;
     * {@code false} se o número de dias não for válido
     */
    public boolean advanceDate(int numberOfDays) {
        return this.store.advanceDate(numberOfDays);
    }

    public void importFile(String dataFile) throws IOException {
        Scanner ler = new Scanner(new File(dataFile));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            //Ignorar linha de comentários (#) no ficheiro
            if (linha.startsWith("#")) continue;
            String[] elements = linha.split("\\|");
            switch (elements[0]) {
                case "CLIENT":
                    //TODO
                    break;

                case "PRODUCT":
                    //TODO
                    break;
            }
        }
    }

    //TODO: A completar pelos alunos
    public int registClient (String name, String nif, LocalDate birthDate) throws InvalidClientException {
        return this.store.registClient(name, nif, birthDate);
    }

    public ArrayList<Client> listClients() throws NoClientsRegisteredException {
        return this.store.listClients();
    }

    public ArrayList<Client> listClientsByAge() throws NoClientsRegisteredException {
        return this.store.listClientsByAge();
    }

    public Client showClient(int id) throws NoSuchClientException {
        return this.store.showClient(id);
    }

    public void changeClientName (int id, String name) throws NoSuchClientException {
        this.store.changeClientName(id, name);
    }

    public ArrayList<Client> searchClients(String searchTerm) throws NoClientsFoundException {
        return this.store.searchClients(searchTerm);
    }

    public int registProduct (String name, double price, int stock, LocalDate expirationDate)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {
        return this.store.registProduct(name, price, stock, expirationDate);
    }

    public int registProduct (String name, double price, int stock, int warrantyMonths)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {
        return this.store.registProduct(name, price, stock, warrantyMonths);
    }

    public int registProduct (String name, double price, int stock, String clothingSize)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {
        return this.store.registProduct(name, price, stock, clothingSize);
    }
}
