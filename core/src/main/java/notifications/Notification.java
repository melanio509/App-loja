package atec.poo.loja.core.notifications;

import atec.poo.loja.core.clients.Client;
import atec.poo.loja.core.products.Product;

import java.time.LocalDate;

public class Notification {

    /*
    Identificador único
Data da notificação
Tipo de alteração
Cliente
Produto
Preço do produto nessa data
Estado de leitura
     */
    private int id;
    private LocalDate date;
    private NotificationType type;
    private Client client;
    private Product product;
    private double price;
    private boolean read;

    public Notification(int id, LocalDate date, NotificationType type, Client client, Product product, double price) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.client = client;
        this.product = product;
        this.price = price;
        this.read = false;
    }
}
