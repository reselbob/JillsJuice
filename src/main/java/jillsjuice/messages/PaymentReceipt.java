package jillsjuice.messages;

import java.math.BigDecimal;
import java.util.UUID;
import jillsjuice.model.Customer;

public class PaymentReceipt {
  private final UUID id;
  private final Customer customer;
  private final BigDecimal purchaseTotal;
  private final UUID purchaseId;

  public PaymentReceipt(UUID id, Customer customer, BigDecimal purchaseTotal, UUID purchaseId) {
    this.id = id;
    this.customer = customer;
    this.purchaseTotal = purchaseTotal;
    this.purchaseId = purchaseId;
  }

  public UUID getId() {
    return this.id;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public BigDecimal getPurchaseTotal() {
    return this.purchaseTotal;
  }

  public UUID getPurchaseId() {
    return purchaseId;
  }
}
