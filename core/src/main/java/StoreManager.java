package atec.poo.loja.core;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.exceptions.*;
import atec.poo.loja.core.notifications.Notification;
import atec.poo.loja.core.products.Product;
import atec.poo.loja.core.sales.Sale;
import atec.poo.loja.core.sales.Subscription;
import atec.poo.loja.core.sales.SubscriptionStatus;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void importFile(String dataFile)
            throws IOException, InvalidClientException, InvalidWarrantyMonthsException, InvalidProductPriceException, InvalidProductStockException {
        Scanner ler = new Scanner(new File(dataFile));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            //Ignorar linha de comentários (#) no ficheiro
            if (linha.startsWith("#")) continue;
            String[] elements = linha.split("\\|");
            switch (elements[0]) {
                case "CLIENT":
                    this.registClient(
                            elements[1],
                            elements[2],
                            LocalDate.parse(elements[3])
                    );
                    break;
                case "PRODUCT":
                    switch (elements[1]) {
                        case "FOOD":
                            this.registProduct(
                                    elements[2],
                                    Double.parseDouble(elements[3]),
                                    Integer.parseInt(elements[4]),
                                    LocalDate.parse(elements[5])
                            );
                            break;
                        case "ELECTRONICS":
                            this.registProduct(
                                    elements[2],
                                    Double.parseDouble(elements[3]),
                                    Integer.parseInt(elements[4]),
                                    Integer.parseInt(elements[5])
                            );
                            break;
                        case "CLOTHING":
                            this.registProduct(
                                    elements[2],
                                    Double.parseDouble(elements[3]),
                                    Integer.parseInt(elements[4]),
                                    elements[5]
                            );
                            break;
                    }
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

    public Product showProduct(int id) throws NoSuchProductException {
        return this.store.showProduct(id);
    }

    public ArrayList<Product> listProducts() throws NoProductsRegisteredException {
        return this.store.listProducts();
    }

    public ArrayList<Product> listProductsByCategory(int category)
            throws NoProductsRegisteredException, NoProductsInCategoryException {
        return this.store.listProductsByCategory(category);
    }

    public ArrayList<Product> searchProducts(String searchTerm) throws NoProductsFoundException {
        return this.store.searchProducts(searchTerm);
    }

    public void changeProductPrice (int id, double price) throws InvalidProductPriceException {
        this.store.changeProductPrice(id, price);
    }

    public ArrayList<Product> increaseCategoryPrices (int category, double percentageIncrease)
            throws NoProductsInCategoryException, NoProductsRegisteredException, InvalidCategoryPriceIncreaseException {
        return this.store.increaseCategoryPrices(category, percentageIncrease);
    }

    public void addProductStock (int id, int quantity) throws InvalidProductStockQuantityException {
       this.store.addProductStock(id, quantity);
    }

    public Sale registSale (Client client, Product product, int quantity)
            throws InvalidSaleQuantityException, ProductCannotBeSoldException, InsufficientProductStockException {
        return this.store.registSale(client, product, quantity);
    }

    public void subscribeProductNotification (Client client, Product product)
            throws ActiveSubscriptionAlreadyExistsException {
        this.store.subscribeProductNotification(client, product);
    }

    public Sale showSale(int id) throws NoSuchSaleException {
        return this.store.showSale(id);
    }

    public ArrayList<Sale> listSales() throws NoSalesRegisteredException {
        return this.store.listSales();
    }

    public ArrayList<Sale> listSalesByClient(int clientId) throws NoClientSalesException, NoSuchClientException {
        return this.store.listSalesByClient(clientId);
    }

    public ArrayList<Notification> readClientNotifications(int clientId) throws NoSuchClientException {
        return this.store.readClientNotifications(clientId);
    }

    public ArrayList<Subscription> cancelActiveSubscriptions(int clientId) {
        return this.store.cancelActiveSubscriptions(clientId);
    }

    public ArrayList<Notification> listNotificationHistory() throws NoNotificationsRegisteredException {
        return this.store.listNotificationHistory();
    }

    public ArrayList<Subscription> listActiveSubscriptions() throws NoActiveSubscriptionsException {
        return this.store.listActiveSubscriptions();
    }

    public int getNumberOfClients() {
        return this.store.getNumberOfClients();
    }

    public int getNumberOfProducts() {
        return this.store.getNumberOfProducts();
    }

    public double getTotalSalesValue(int lastDays) {
        return this.store.getTotalSalesValue(lastDays);
    }

    public double getTotalStockValue() {
        return this.store.getTotalStockValue();
    }

    public int numberOfNotifications(boolean filter) {
        return this.store.numberOfNotifications(filter);
    }

    public int numberOfActiveSubscriptions() {
        return this.store.numberOfActiveSubscriptions();
    }

}
