package jillsjuice.messages;

import jillsjuice.model.PurchaseItem;

import java.util.List;

public class AddItems {
    private final List<PurchaseItem> purchaseItems;

    public AddItems(List<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return this.purchaseItems;
    }
}
