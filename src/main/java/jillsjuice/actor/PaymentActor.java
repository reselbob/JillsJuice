package jillsjuice.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import jillsjuice.messages.PaymentInfo;

public class PaymentActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PaymentInfo.class, this::handlePayment)
                .build();
    }

    private void handlePayment(PaymentInfo message) {
        this.log.info(
                "Executing payment for purchase id {} for the amount of {} the using credit card {}.",
                message.getPurchaseId(),
                message.getPaymentAmount(),
                message.getCreditCard().toString());
        getSender().tell("Payment made for purchase id " + message.getPurchaseId(), getSelf());
    }
}