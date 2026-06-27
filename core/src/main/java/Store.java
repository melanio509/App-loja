package atec.poo.loja.core;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.*;
import atec.poo.loja.core.products.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Store implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Data inicial da aplicação.
     *
     * <p>A aplicação inicia sempre nesta data para permitir que os resultados
     * sejam previsíveis e facilmente testáveis. NÃO ALTERAR</p>
     */
    private static final LocalDate INITIAL_DATE =
            LocalDate.of(2026, 6, 1);

    /**
     * Data simulada atual da loja.
     *
     * <p>Esta data faz parte do estado da aplicação e é guardada através
     * de serialização.</p>
     */
    private LocalDate today;

    //TODO: A IMPLEMENTAR. Restantes atributos da classe
    private int nextClientID;
    private HashMap<Integer, Client> clients;

    private int nextProductID;
    private HashMap<Integer, Product> products;


    /**
     * Cria uma nova loja com a data inicial definida pela aplicação.
     */
    public Store() {
        this.today = INITIAL_DATE;

        //TODO: Completar pelos alunos
        this.nextClientID = 1;
        this.clients = new HashMap<>();

        this.nextProductID = 1;
        this.products = new HashMap<>();

    }

    /**
     * Devolve a data simulada atual da aplicação.
     *
     * @return data atual da loja
     */
    public LocalDate getToday() {
        return this.today;
    }


    /**
     * Avança a data simulada da aplicação.
     *
     * <p>Depois de atualizar a data, recalcula o status
     * de todos os clientes.</p>
     *
     * @param numberOfDays número de dias a avançar
     * @return {@code true} se a data tiver sido avançada;
     *         {@code false} se o número de dias não for válido
     */
    public boolean advanceDate(int numberOfDays) {
        if (numberOfDays <= 0) {
            return false;
        }

        this.today =
                this.today.plusDays(
                        numberOfDays
                );

        //TODO: Update User status after sales implemented

        return true;
    }

    //TODO: Completar restantes métodos
    public int registClient (String name, String nif, LocalDate birthDate) throws InvalidClientException {
        Client client = new Client(this.nextClientID, name, nif, birthDate);

        if(client.getAge(this.today) < 18) {
            // Lançar excepção
            throw new InvalidClientException(birthDate);
        }

        this.clients.put(client.getId(), client);
        this.nextClientID++;
        return client.getId();
    }

    public ArrayList<Client> listClients() throws NoClientsRegisteredException {
        ArrayList<Client> clients = new ArrayList<>(this.clients.values());

        if(clients.isEmpty()) {
            throw new NoClientsRegisteredException();
        }

        Collections.sort(clients);
        return clients;
    }

    public Client getClient(int id) throws NoSuchClientException {
        if(!this.clients.containsKey(id)) {
            throw new NoSuchClientException(id);
        }

        return this.clients.get(id);
    }

    private void validProduct(double price, int stock, int warrantyMonths)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {
        if (price <= 0)
            throw new InvalidProductPriceException(price);

        if (stock < 0)
            throw new InvalidProductStockException(stock);

        if (warrantyMonths <= 0)
            throw new InvalidWarrantyMonthsException(warrantyMonths);
    }

    private int storeProduct(Product product) {
        this.products.put(product.getId(), product);
        this.nextProductID++;
        return product.getId();
    }

    public int registProduct (String name, double price, int stock, LocalDate expirationDate)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {

        validProduct(price, stock, 1);
        Product product = new Food(this.nextProductID, name, price, stock, expirationDate, getToday());
        return storeProduct(product);
    }

    public int registProduct (String name, double price, int stock, int warrantyMonths)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {

        validProduct(price, stock, warrantyMonths);
        Product product = new Electronic(this.nextProductID, name, price, stock, warrantyMonths, getToday());
        return storeProduct(product);
    }

    public int registProduct (String name, double price, int stock, String clothingSize)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {

        validProduct(price, stock, 1);
        Product product = new Clothing(this.nextProductID, name, price, stock, clothingSize, getToday());
        return storeProduct(product);
    }
}
