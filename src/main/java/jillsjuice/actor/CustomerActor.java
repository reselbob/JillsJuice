package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import jillsjuice.messages.PaymentReceipt;
import jillsjuice.messages.ShippingReceipt;

public class CustomerActor extends AbstractActor {
  // We'll leave a reference to the base system
  // in, just in case CustomerActor wants to use it later
  // on
  private final ActorSystem actorSystem;
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  // Declare the parent system
  public CustomerActor(ActorSystem actorSystem) {
    this.actorSystem = actorSystem;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(PaymentReceipt.class, this::handlePaymentReceipt)
        .match(ShippingReceipt.class, this::handleShippingReceipt)
        .build();
  }

  private void handlePaymentReceipt(PaymentReceipt message) {
    this.log.info(
        "Received a payment receipt for customer {} for purchase id {}. .",
        message.getCustomer(),
        message.getPurchaseId());
  }

  private void handleShippingReceipt(ShippingReceipt message) {
    this.log.info("Received a shipping receipt for customer {} ", message.getCustomer());
  }
}
