package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import jillsjuice.messages.AddItems;
import jillsjuice.messages.CheckoutInfo;
import jillsjuice.messages.PaymentInfo;
import jillsjuice.messages.ShippingInfo;
import jillsjuice.model.Purchase;
import jillsjuice.model.PurchaseItem;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ShoppingCartActor extends AbstractActor {
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
  private final List<PurchaseItem> purchaseItems = new ArrayList<>();

  LoggingAdapter getLog() {
    return this.log;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
            .match(AddItems.class, message -> {
              this.purchaseItems.addAll(message.getPurchaseItems());
            })
            .match(CheckoutInfo.class, this::handleCheckout)
            .build();
  }

  private void handleCheckout(CheckoutInfo checkoutInfo){
    ActorSystem system = ActorSystem.create("PaymentActorSystem");
    // pay for the items
    this.getLog().info("Sending payment message");

    ActorRef paymentActor = system.actorOf(Props.create(PaymentActor.class), "paymentActor");

    // Timeout for the ask pattern
    Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

    // Use the ask pattern to send a message to the worker actor and get a response
    Purchase purchase = new Purchase(UUID.randomUUID(), this.purchaseItems);

    BigDecimal totalAmount = checkoutInfo.getPurchaseItems().stream()
                    .map(PurchaseItem::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

    PaymentInfo paymentInfo = new PaymentInfo(checkoutInfo.getCreditCard(),totalAmount,purchase.getId());

    Future<Object> future = Patterns.ask(paymentActor, paymentInfo, timeout);

    try {
      // Wait for the future to complete and get the response
      String response = (String) Await.result(future, timeout.duration());
      this.getLog().info("Received response for payment {}.", response);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // ship the items
    this.getLog().info("Sending shipping message");

    ActorRef shippingActor = system.actorOf(Props.create(ShippingActor.class), "shippingActor");
    ShippingInfo shippingInfo = new ShippingInfo(checkoutInfo.getCustomer(),
            checkoutInfo.getPurchaseItems(),
            "FEDEX", checkoutInfo.getShippingAddress());

    future = Patterns.ask(shippingActor, shippingInfo, timeout);
    try {
      // Wait for the future to complete and get the response
      String response = (String) Await.result(future, timeout.duration());
      this.getLog().info("Received response for shipping {}.", response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    system.terminate();
  }


}
