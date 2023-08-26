package jillsjuice.messages;

import java.util.List;
import jillsjuice.model.Address;
import jillsjuice.model.Customer;
import jillsjuice.model.PurchaseItem;

public class ShippingInfo {
  private final Customer customer;
  private final List<PurchaseItem> purchaseItems;
  private final String shipper;
  private final Address shippingAddress;

  public ShippingInfo(
      Customer customer,
      List<PurchaseItem> purchaseItems,
      String shipper,
      Address shippingAddress) {
    this.customer = customer;
    this.purchaseItems = purchaseItems;
    this.shipper = shipper;
    this.shippingAddress = shippingAddress;
  }

  public Customer getCustomer() {
    return customer;
  }

  public List<PurchaseItem> getPurchaseItems() {
    return purchaseItems;
  }

  public String getShipper() {
    return shipper;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }
}
