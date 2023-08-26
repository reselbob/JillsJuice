package jillsjuice.messages;

import jillsjuice.model.PurchaseItem;

public class RemoveItem {
  private final PurchaseItem purchaseItem;

  public RemoveItem(PurchaseItem purchaseItem) {
    this.purchaseItem = purchaseItem;
  }

  public PurchaseItem getPurchaseItem() {
    return this.purchaseItem;
  }
}
