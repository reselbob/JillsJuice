package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import jillsjuice.messages.*;
import jillsjuice.model.Purchase;
import jillsjuice.model.PurchaseItem;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class ShoppingCartActor extends AbstractActor {
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
  private final List<PurchaseItem> purchaseItems = new ArrayList<>();

  private final ActorSystem actorSystem;

  public ShoppingCartActor(ActorSystem actorSystem) {
    this.actorSystem = actorSystem;
  }

  LoggingAdapter getLog() {
    return this.log;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(AddItems.class, this::handleAddItems)
        .match(CheckoutInfo.class, this::handleCheckout)
        .build();
  }

  private void handleAddItems(AddItems message) {
    this.purchaseItems.addAll(message.getPurchaseItems());
  }

  private void handleCheckout(CheckoutInfo message) {
    // Timeout for the ask pattern
    Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

    // Use the ask pattern to send a message to the PaymentActor actor and get a response
    Purchase purchase = new Purchase(UUID.randomUUID(), this.purchaseItems);
    // pay for the items
    sendPayment(message, timeout, purchase);
  }

  private void sendPayment(CheckoutInfo message, Timeout timeout, Purchase purchase) {
    this.getLog().info("Sending payment message");
    BigDecimal totalAmount =
        message.getPurchaseItems().stream()
            .map(PurchaseItem::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    ActorRef paymentActor =
            this.actorSystem.actorOf(
                    Props.create(PaymentActor.class, this.actorSystem), "paymentActor");

    PaymentInfo paymentInfo =
        new PaymentInfo(
            message.getCustomer(), message.getCreditCard(), totalAmount, purchase.getId());

    Future<Object> future = Patterns.ask(paymentActor, paymentInfo, timeout);

    try {
      // Wait for the future to complete and get the response
      String response = (String) Await.result(future, timeout.duration());
      this.getLog().info("Received response for payment {}, now shipping items.", response);
      // ship the items
      shipItems(message, timeout);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void shipItems(CheckoutInfo message, Timeout timeout) {
    this.getLog().info("Sending shipping message");
    Future<Object> future;
    ActorRef shippingActor =
        this.actorSystem.actorOf(
            Props.create(ShippingActor.class, this.actorSystem), "shippingActor");
    ShippingInfo shippingInfo =
        new ShippingInfo(
            message.getCustomer(),
            message.getPurchaseItems(),
            "FEDEX",
            message.getShippingAddress());

    future = Patterns.ask(shippingActor, shippingInfo, timeout);
    try {
      // Wait for the future to complete and get the response
      String response = (String) Await.result(future, timeout.duration());
      this.getLog().info("Received response for shipping {}.", response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
