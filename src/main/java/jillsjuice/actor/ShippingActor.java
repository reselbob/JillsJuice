package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import jillsjuice.messages.ShippingInfo;
import jillsjuice.model.Customer;

public class ShippingActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(ShippingInfo.class, this::handleShipping)
                .build();
    }

    private void handleShipping(ShippingInfo message) {
        this.log.info("Shipping {} purchase items to {} at {} using {}.",
                message.getPurchaseItems().size(),
                message.getCustomer(),
                message.getShippingAddress(),
                message.getShipper());

        String response = String.format("Shipped %s purchase to %s using %s",
                message.getPurchaseItems().size(),
                message.getCustomer(),
                message.getShipper());
        getSender().tell(response, getSelf());
    }
}
