package jillsjuice;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jillsjuice.actor.ShoppingCartActor;
import jillsjuice.messages.CheckoutInfo;
import jillsjuice.model.Address;
import jillsjuice.model.CreditCard;
import jillsjuice.model.Customer;
import jillsjuice.model.PurchaseItem;

public class App {
  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("JillsJuiceSystem");

    Address address = new Address("123 Main Street", "Apt 1", "Anytown", "CA", "99999-9999", "USA");

    Customer customer =
        new Customer(
            UUID.randomUUID(), "Barney", "Rubble", "barney@rubble.com", "310 878 9999", address);

    List<PurchaseItem> purchaseItems = new ArrayList<>();
    PurchaseItem purchaseItem1 =
        new PurchaseItem(
            UUID.randomUUID(),
            "Jill's Gourmet Apple Juice Large",
            5,
            new BigDecimal("1"),
            new BigDecimal("10.99"));

    purchaseItems.add(purchaseItem1);

    PurchaseItem purchaseItem2 =
        new PurchaseItem(
            UUID.randomUUID(),
            "Jill's Apricot Peach Medley Medium",
            3,
            new BigDecimal("2"),
            new BigDecimal("7.99"));

    purchaseItems.add(purchaseItem2);

    PurchaseItem purchaseItem3 =
        new PurchaseItem(
            UUID.randomUUID(),
            "Jill's Cherry Blast Small",
            1,
            new BigDecimal("3"),
            new BigDecimal("4.99"));

    purchaseItems.add(purchaseItem3);
    // Get  the credit card
    String firstName = customer.getFirstName();
    String lastName = customer.getLastName();
    CreditCard creditCard =
        new CreditCard(firstName + " " + lastName, "1111222233334444", 8, 26, 111);
    ActorRef shoppingCartActor =
        system.actorOf(Props.create(ShoppingCartActor.class, system), "shoppingCartActor");

    CheckoutInfo checkoutInfo =
        new CheckoutInfo(creditCard, purchaseItems, customer, address, address);

    shoppingCartActor.tell(checkoutInfo, shoppingCartActor);

  }
}
