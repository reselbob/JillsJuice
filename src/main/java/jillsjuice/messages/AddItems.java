package jillsjuice.messages;

import java.util.List;
import jillsjuice.model.PurchaseItem;

public class AddItems {
  private final List<PurchaseItem> purchaseItems;

  public AddItems(List<PurchaseItem> purchaseItems) {
    this.purchaseItems = purchaseItems;
  }

  public List<PurchaseItem> getPurchaseItems() {
    return this.purchaseItems;
  }
}
