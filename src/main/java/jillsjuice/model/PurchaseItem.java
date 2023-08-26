package jillsjuice.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PurchaseItem {
  private final UUID id;
  private final String description;
  private final int packageSize;
  private final BigDecimal quantity;
  private final BigDecimal price;
  private Date shipDate;

  public PurchaseItem(
      UUID id, String description, int packageSize, BigDecimal quantity, BigDecimal price) {
    this.id = id;
    this.description = description;
    this.packageSize = packageSize;
    this.quantity = quantity;
    this.price = price;
    this.shipDate = null;
  }

  public UUID getId() {
    return this.id;
  }

  public int getPackageSize() {
    return packageSize;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getTotal() {
    return this.quantity.multiply(this.price);
  }

  public Date getShipDate() {
    return shipDate;
  }

  public void setShipDate(Date shipDate) {
    this.shipDate = shipDate;
  }

  public String getDescription() {
    return description;
  }
}
