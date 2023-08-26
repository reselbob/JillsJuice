package jillsjuice;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import jillsjuice.actor.ShoppingCartActor;
import jillsjuice.messages.CheckoutInfo;
import jillsjuice.model.Address;
import jillsjuice.model.CreditCard;
import jillsjuice.model.Customer;
import jillsjuice.model.PurchaseItem;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class App {

/*    public static class WorkerActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, message -> {
                        if (message.equals("Hello")) {
                            getSender().tell("Hello, back!", getSelf());
                        } else {
                            unhandled(message);
                        }
                    })
                    .build();
        }
    }*/
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("JillsJuice");

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
                        new BigDecimal("7.99")
                );

        purchaseItems.add(purchaseItem2);

        PurchaseItem purchaseItem3 =
                new PurchaseItem(
                        UUID.randomUUID(),
                        "Jill's Cherry Blast Small",
                        1,
                        new BigDecimal("3"), new BigDecimal("4.99"));

        purchaseItems.add(purchaseItem3);
        // Get  the credit card
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        CreditCard creditCard =
                new CreditCard(firstName + " " + lastName, "1111222233334444", 8, 26, 111);
        ActorRef shoppingCartActor = system.actorOf(Props.create(ShoppingCartActor.class), "shoppingCartActor");

        CheckoutInfo checkoutInfo = new CheckoutInfo(creditCard, purchaseItems,customer,address, address);

        shoppingCartActor.tell(checkoutInfo,shoppingCartActor);

/*        // Timeout for the ask pattern
        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

        // Use the ask pattern to send a message to the worker actor and get a response
        Future<Object> future = Patterns.ask(shoppingCartActor, "Hello", timeout);

        try {
            // Wait for the future to complete and get the response
            String response = (String) Await.result(future, timeout.duration());
            System.out.println("Received response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            system.terminate();
        }*/
    }
}
