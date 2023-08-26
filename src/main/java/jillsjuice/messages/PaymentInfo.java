package jillsjuice.messages;

import java.math.BigDecimal;
import java.util.UUID;
import jillsjuice.model.CreditCard;
import jillsjuice.model.Customer;

public class PaymentInfo {
  private final CreditCard creditCard;
  private final Customer customer;
  private final BigDecimal paymentAmount;
  private final UUID purchaseId;

  public PaymentInfo(
      Customer customer, CreditCard creditCard, BigDecimal paymentAmount, UUID purchaseId) {
    this.customer = customer;
    this.creditCard = creditCard;
    this.paymentAmount = paymentAmount;
    this.purchaseId = purchaseId;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public CreditCard getCreditCard() {
    return this.creditCard;
  }

  public BigDecimal getPaymentAmount() {
    return this.paymentAmount;
  }

  public UUID getPurchaseId() {
    return this.purchaseId;
  }
}
