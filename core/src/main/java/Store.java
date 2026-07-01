package atec.poo.loja.core;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.clients.ClientStatus;
import atec.poo.loja.core.exceptions.*;
import atec.poo.loja.core.notifications.Notification;
import atec.poo.loja.core.notifications.NotificationType;
import atec.poo.loja.core.products.*;
import atec.poo.loja.core.sales.Sale;
import atec.poo.loja.core.sales.Subscription;
import atec.poo.loja.core.sales.SubscriptionStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private int nextClientID;
    private HashMap<Integer, Client> clients;

    private int nextProductID;
    private HashMap<Integer, Product> products;

    private int nextSaleID;
    private HashMap<Integer, Sale> sales;

    private ArrayList<Subscription> subscriptions;

    private int nextNotificationID;
    private HashMap<Integer, Notification> notifications;

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

        this.nextSaleID = 1;
        this.sales = new HashMap<>();

        this.subscriptions = new ArrayList<>();

        this.nextNotificationID = 1;
        this.notifications = new HashMap<>();
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

        for (Client client : this.clients.values())
            recalculateClientStatus(client);

        return true;
    }

    public int registClient (String name, String nif, LocalDate birthDate) throws InvalidClientException {
        Client client = new Client(this.nextClientID, name, nif, birthDate);

        if(client.getAge(this.today) < 18) {
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

    public ArrayList<Client> listClientsByAge() throws NoClientsRegisteredException {
        ArrayList<Client> clients = new ArrayList<>(this.clients.values());

        if(clients.isEmpty()) {
            throw new NoClientsRegisteredException();
        }

        clients.sort(
                Comparator
                        .comparing((Client client) -> client.getAge(this.today))
                        .thenComparing(Client::getStatus)
                        .thenComparing(Client::getId)
        );

        return clients;
    }

    public Client showClient(int id) throws NoSuchClientException {
        if(!this.clients.containsKey(id)) {
            throw new NoSuchClientException(id);
        }

        return this.clients.get(id);
    }

    public void changeClientName (int id, String name) throws NoSuchClientException {
        if(this.clients.get(id) == null) {
            throw new NoSuchClientException(id);
        }

        this.clients.get(id).setName(name);
    }

    public ArrayList<Client> searchClients(String searchTerm) throws NoClientsFoundException {
        ArrayList<Client> clients = new ArrayList<>(this.clients.values());
        ArrayList<Client> searchedClients = new ArrayList<>();

        if(clients.isEmpty())
            throw new NoClientsFoundException(searchTerm);

        for (Client client : clients) {
            boolean matchName = client.getName().contains(searchTerm);
            boolean matchNif = client.getNif().contains(searchTerm);
            boolean matchStatus = client.getStatus().name().contains(searchTerm);

            if (matchName || matchNif || matchStatus) {
                searchedClients.add(client);
            }
        }

        if(searchedClients.isEmpty())
            throw new NoClientsFoundException(searchTerm);

        searchedClients.sort(
                Comparator
                        .comparing(Client::getName)
                        .thenComparing(Client::getId)
        );

        return searchedClients;
    }

    private void validateProduct(double price, int stock)
            throws InvalidProductPriceException, InvalidProductStockException {
        if (price <= 0)
            throw new InvalidProductPriceException(price);
        if (stock < 0)
            throw new InvalidProductStockException(stock);
    }

    private int storeProduct(Product product) {
        this.products.put(product.getId(), product);
        this.nextProductID++;
        return product.getId();
    }

    public int registProduct (String name, double price, int stock, LocalDate expirationDate)
            throws InvalidProductPriceException, InvalidProductStockException {

        validateProduct(price, stock);
        Product product = new Food(this.nextProductID, name, price, stock, expirationDate, getToday());
        return storeProduct(product);
    }

    public int registProduct (String name, double price, int stock, int warrantyMonths)
            throws InvalidProductPriceException, InvalidProductStockException, InvalidWarrantyMonthsException {

        validateProduct(price, stock);

        if (warrantyMonths <= 0)
            throw new InvalidWarrantyMonthsException(warrantyMonths);

        Product product = new Electronic(this.nextProductID, name, price, stock, warrantyMonths, getToday());
        return storeProduct(product);
    }

    public int registProduct (String name, double price, int stock, String clothingSize)
            throws InvalidProductPriceException, InvalidProductStockException {

        validateProduct(price, stock);
        Product product = new Clothing(this.nextProductID, name, price, stock, clothingSize, getToday());
        return storeProduct(product);
    }

    public Product showProduct(int id) throws NoSuchProductException {
        if(!this.products.containsKey(id)) {
            throw new NoSuchProductException(id);
        }

        return this.products.get(id);
    }

    public ArrayList<Product> listProducts() throws NoProductsRegisteredException {
        ArrayList<Product> products = new ArrayList<>(this.products.values());

        if(products.isEmpty()) {
            throw new NoProductsRegisteredException();
        }

        Collections.sort(products);
        return products;
    }

    public ArrayList<Product> listProductsByCategory(int category)
            throws NoProductsRegisteredException, NoProductsInCategoryException {

        if(this.products.isEmpty()) {
            throw new NoProductsRegisteredException();
        }

        ArrayList<Product> productsByCategory = new ArrayList<>();

        for (Product product : this.products.values()) {
            if (product.getCategory().getCode() == category) {
                productsByCategory.add(product);
            }
        }

        if(productsByCategory.isEmpty()) {
            throw new NoProductsInCategoryException(category);
        }

        Collections.sort(productsByCategory);
        return productsByCategory;
    }

    public ArrayList<Product> searchProducts(String searchTerm) throws NoProductsFoundException {

        ArrayList<Product> searchedProducts = new ArrayList<>();

        if(this.products.isEmpty())
            throw new NoProductsFoundException(searchTerm);

        for (Product product : this.products.values()) {
            boolean matchName = product.getName().contains(searchTerm);

            if (matchName) {
                searchedProducts.add(product);
            }
        }

        if(searchedProducts.isEmpty())
            throw new NoProductsFoundException(searchTerm);

        Collections.sort(searchedProducts);
        return searchedProducts;
    }

    public void changeProductPrice (int id, double price) throws InvalidProductPriceException {
        if(price <= 0) {
            throw new InvalidProductPriceException(price);
        }

        Product product = this.products.get(id);

        product.updatePrice(getToday(), price);
        notify(NotificationType.PRICE, product);
    }

    public ArrayList<Product> increaseCategoryPrices (int category, double percentageIncrease)
            throws NoProductsInCategoryException, NoProductsRegisteredException, InvalidCategoryPriceIncreaseException {

        ArrayList<Product> productsByCategoryIncreased = this.listProductsByCategory(category);

        if(percentageIncrease <= 0) {
            throw new InvalidCategoryPriceIncreaseException(percentageIncrease);
        }

        for (Product product : productsByCategoryIncreased) {
            product.increasePrice(getToday(), percentageIncrease);
            notify(NotificationType.PRICE, product);
        }

        return productsByCategoryIncreased;
    }

    public void addProductStock (int id, int quantity) throws InvalidProductStockQuantityException {
        if(quantity <= 0) {
            throw new InvalidProductStockQuantityException(quantity);
        }

        Product product = this.products.get(id);
        product.updateStock(quantity);
        notify(NotificationType.INCREASE, product);
    }

    public void recalculateClientStatus(Client client) {

        double totalSalesByClient = 0;

        if (client.getAge(today) >= 18 && client.getAge(today) < 25 ) {
            for (Sale sale : this.sales.values()) {
                if ((sale.getClient().getId() == client.getId())
                        && (!sale.getDate().isBefore(today.minusDays(60)))) {
                    totalSalesByClient += sale.getTotal();
                }
            }

            if (totalSalesByClient >= 800) client.setStatus(ClientStatus.PLATINUM);
            else if (totalSalesByClient >= 400) client.setStatus(ClientStatus.GOLD);
            else if (totalSalesByClient >= 150) client.setStatus(ClientStatus.SILVER);
            else client.setStatus(ClientStatus.REGULAR);

        } else if (client.getAge(today) >= 25 && client.getAge(today) < 65 ) {
            for (Sale sale : this.sales.values()) {
                if ((sale.getClient().getId() == client.getId())
                        && (!sale.getDate().isBefore(today.minusDays(30)))) {
                    totalSalesByClient += sale.getTotal();
                }
            }

            if (totalSalesByClient >= 1800) client.setStatus(ClientStatus.PLATINUM);
            else if (totalSalesByClient >= 900) client.setStatus(ClientStatus.GOLD);
            else if (totalSalesByClient >= 350) client.setStatus(ClientStatus.SILVER);
            else client.setStatus(ClientStatus.REGULAR);

        } else if (client.getAge(getToday()) >= 65 ) {
            for (Sale sale : this.sales.values()) {
                if ((sale.getClient().getId() == client.getId())
                        && (!sale.getDate().isBefore(today.minusDays(90)))) {
                    totalSalesByClient += sale.getTotal();
                }
            }

            if (totalSalesByClient >= 650) client.setStatus(ClientStatus.PLATINUM);
            else if (totalSalesByClient >= 300) client.setStatus(ClientStatus.GOLD);
            else if (totalSalesByClient >= 100) client.setStatus(ClientStatus.SILVER);
            else client.setStatus(ClientStatus.REGULAR);
        }
    }

    public Sale registSale (Client client, Product product, int quantity)
            throws InvalidSaleQuantityException, ProductCannotBeSoldException, InsufficientProductStockException {

        if(quantity <= 0) {
            throw new InvalidSaleQuantityException(quantity);
        }

        if(!product.canBeSold()) {
            throw new ProductCannotBeSoldException(product.getId());
        }

        if(product.getStock() < quantity) {
            throw new InsufficientProductStockException(product.getId(), quantity, product.getStock());
        }

        Sale sale = new Sale(this.nextSaleID, getToday(), client, product, quantity);

        product.updateStock(-quantity);
        notify(NotificationType.DECREASE, product);
        recalculateClientStatus(client);

        this.sales.put(sale.getId(), sale);
        this.nextSaleID++;
        return sale;
    }

    public boolean existsSubscription(Client client, Product product) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getClient().getId() == client.getId()
                    && subscription.getProduct().getId() == product.getId()
                        && subscription.getStatus() == SubscriptionStatus.ATIVA) {
                return true;
            }
        }
        return false;
    }

    public void subscribeProductNotification (Client client, Product product)
            throws ActiveSubscriptionAlreadyExistsException {

        if(existsSubscription(client, product))
            throw new ActiveSubscriptionAlreadyExistsException(client.getId(), product.getId());

        this.subscriptions.add(new Subscription(client, product, getToday()));
    }

    public Sale showSale(int id) throws NoSuchSaleException {

        if(!this.sales.containsKey(id)) {
            throw new NoSuchSaleException(id);
        }

        return this.sales.get(id);
    }

    public ArrayList<Sale> listSales() throws NoSalesRegisteredException {
        ArrayList<Sale> sales = new ArrayList<>(this.sales.values());

        if(sales.isEmpty()) {
            throw new NoSalesRegisteredException();
        }

        Collections.sort(sales);
        return sales;
    }

    public ArrayList<Sale> listSalesByClient (int clientId)
            throws NoSuchClientException, NoClientSalesException {

        Client client = showClient(clientId);
        ArrayList<Sale> salesByClient = new ArrayList<>();

        for (Sale sale : this.sales.values())
            if (sale.getClient().getId() == client.getId())
                salesByClient.add(sale);

        if(salesByClient.isEmpty())
            throw new NoClientSalesException(client.getId(), client.getName());

        Collections.sort(salesByClient);
        return salesByClient;
    }

    public int registNotification (NotificationType type, Client client, Product product, double price) {

        Notification notification = new Notification(this.nextNotificationID, today, type, client, product, price);

        this.notifications.put(notification.getId(), notification);
        this.nextNotificationID++;
        return notification.getId();
    }

    private void notify(NotificationType type, Product product) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getStatus() == SubscriptionStatus.ATIVA
                    && subscription.getProduct().getId() == product.getId()) {
                registNotification(
                        type,
                        subscription.getClient(),
                        product,
                        product.getPrice());
            }
        }
    }

    public ArrayList<Notification> readClientNotifications(int clientId) throws NoSuchClientException {

        Client client = showClient(clientId);

        ArrayList<Notification> clientNotifications = new ArrayList<>();

        for (Notification n : this.notifications.values()) {
            if (n.getClient().getId() == clientId && !n.isRead()) {
                n.markAsRead();
                clientNotifications.add(n);
            }
        }

        if (clientNotifications.isEmpty())
            clientNotifications.add(new Notification(-1, today, NotificationType.INCREASE, client, null, 0));
        return clientNotifications;
    }

    public ArrayList<Subscription> cancelActiveSubscriptions(int clientId) {

        ArrayList<Subscription> cancelledSubscriptions = new ArrayList<>();
        for (Subscription s : this.subscriptions) {
            if(s.getClient().getId() == clientId) {
                s.setStatus(SubscriptionStatus.CANCELADA);
                cancelledSubscriptions.add(s);
            }
        }

        return cancelledSubscriptions;
    }

    public ArrayList<Notification> listNotificationHistory() throws NoNotificationsRegisteredException {

        ArrayList<Notification> notificationHistory = new ArrayList<>(this.notifications.values());

        if(notificationHistory.isEmpty())
            throw new NoNotificationsRegisteredException();

        notificationHistory.sort(
                Comparator.comparing(Notification::getId)
                        .reversed()
        );

        return notificationHistory;
    }

    public ArrayList<Subscription> listActiveSubscriptions() throws NoActiveSubscriptionsException {
        ArrayList<Subscription> activeSubscriptions = new ArrayList<>();

        for (Subscription s : this.subscriptions)
            if (s.getStatus() == SubscriptionStatus.ATIVA)
                activeSubscriptions.add(s);

        if (activeSubscriptions.isEmpty())
            throw new NoActiveSubscriptionsException();

        activeSubscriptions.sort(Comparator
                        .comparing(Subscription::getDate)
                        .thenComparing(s -> s.getClient().getId())
                        .thenComparing(s ->s.getProduct().getId())
        );

        return activeSubscriptions;
    }
}
