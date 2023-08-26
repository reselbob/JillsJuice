package jillsjuice.messages;

import jillsjuice.model.CreditCard;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentInfo {
    private final CreditCard creditCard;
    private final BigDecimal paymentAmount;
    private final UUID purchaseId;

    public PaymentInfo(CreditCard creditCard, BigDecimal paymentAmount, UUID purchaseId) {
        this.creditCard = creditCard;
        this.paymentAmount = paymentAmount;
        this.purchaseId = purchaseId;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public UUID getPurchaseId() {
        return purchaseId;
    }
}
