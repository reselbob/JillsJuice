package jillsjuice.messages;

import java.util.UUID;
import java.util.Vector;
import jillsjuice.model.Customer;
import jillsjuice.model.PurchaseItem;

public class ShippingReceipt {
  private final UUID id;
  private final Customer customer;
  private final Vector<PurchaseItem> purchaseItems;
  private final String shipper;

  public ShippingReceipt(
      UUID id, Customer customer, Vector<PurchaseItem> purchaseItems, String shipper) {
    this.id = id;
    this.customer = customer;
    this.purchaseItems = purchaseItems;
    this.shipper = shipper;
  }

  public UUID getId() {
    return this.id;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public Vector<PurchaseItem> getPurchaseItems() {
    return this.purchaseItems;
  }

  public String getShipper() {
    return this.shipper;
  }
}
