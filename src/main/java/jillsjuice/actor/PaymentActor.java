package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.UUID;
import jillsjuice.messages.PaymentInfo;
import jillsjuice.messages.PaymentReceipt;

public class PaymentActor extends AbstractActor {
  // Accommodate dependency on an external actor
  private final ActorSystem actorSystem;
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

 //Declare the parent system
  public PaymentActor(ActorSystem actorSystem){
    this.actorSystem = actorSystem;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(PaymentInfo.class, this::handlePayment).build();
  }

  private void handlePayment(PaymentInfo message) {
    ActorRef customerActor = this.actorSystem.actorOf(Props.create(CustomerActor.class, this.actorSystem), "shippingCustomerActor");

    this.log.info(
        "Executing payment for purchase id {} for the amount of {} the using credit card {}.",
        message.getPurchaseId(),
        message.getPaymentAmount(),
        message.getCreditCard().toString());

    PaymentReceipt paymentReceipt =
        new PaymentReceipt(
            UUID.randomUUID(),
            message.getCustomer(),
            message.getPaymentAmount(),
            message.getPurchaseId());

    customerActor.tell(paymentReceipt, customerActor);
    getSender().tell("Payment made for purchase id " + message.getPurchaseId(), getSelf());
  }
}
