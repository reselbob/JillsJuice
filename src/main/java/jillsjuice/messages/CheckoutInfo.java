package jillsjuice.messages;

import java.util.List;
import jillsjuice.model.Address;
import jillsjuice.model.CreditCard;
import jillsjuice.model.Customer;
import jillsjuice.model.PurchaseItem;

public class CheckoutInfo {
  private final CreditCard creditCard;
  private final List<PurchaseItem> purchaseItems;
  private final Customer customer;
  private final Address billingAddress;
  private final Address shippingAddress;

  public CheckoutInfo(
      CreditCard creditCard,
      List<PurchaseItem> purchaseItems,
      Customer customer,
      Address billingAddress,
      Address shippingAddress) {
    this.creditCard = creditCard;
    this.purchaseItems = purchaseItems;
    this.customer = customer;
    this.billingAddress = billingAddress;
    this.shippingAddress = shippingAddress;
  }

  public CreditCard getCreditCard() {
    return this.creditCard;
  }

  public List<PurchaseItem> getPurchaseItems() {
    return this.purchaseItems;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public Address getBillingAddress() {
    return this.billingAddress;
  }

  public Address getShippingAddress() {
    return this.shippingAddress;
  }
}
