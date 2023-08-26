package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.UUID;
import java.util.Vector;
import jillsjuice.messages.ShippingInfo;
import jillsjuice.messages.ShippingReceipt;

public class ShippingActor extends AbstractActor {
  private final ActorSystem actorSystem;
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  public ShippingActor(ActorSystem actorSystem){
    this.actorSystem = actorSystem;
  }

  @Override
  public AbstractActor.Receive createReceive() {
    return receiveBuilder().match(ShippingInfo.class, this::handleShipping).build();
  }

  private void handleShipping(ShippingInfo message) {
    // Set up the Customer actor which will receive payment and shipping receipts
    ActorRef customerActor = this.actorSystem.actorOf(Props.create(CustomerActor.class, this.actorSystem), "customerActor");

    this.log.info(
        "Shipping {} purchase items to {} at {} using {}.",
        message.getPurchaseItems().size(),
        message.getCustomer(),
        message.getShippingAddress(),
        message.getShipper());

    String response =
        String.format(
            "Shipped %s purchase to %s using %s",
            message.getPurchaseItems().size(), message.getCustomer(), message.getShipper());
    // Send a fire and forget Shipping Receipt to the customer
    ShippingReceipt shippingReceipt =
        new ShippingReceipt(
            UUID.randomUUID(),
            message.getCustomer(),
            new Vector<>(message.getPurchaseItems()),
            message.getShipper());

    customerActor.tell(shippingReceipt, customerActor);

    getSender().tell(response, getSelf());
  }
}
