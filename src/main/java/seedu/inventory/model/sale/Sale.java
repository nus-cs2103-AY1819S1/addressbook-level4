package seedu.inventory.model.sale;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;

/**
 * Represents a Sale in the Inventory Manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Sale {

    private final SaleId saleId;
    private final Item item;
    private final Quantity quantity;
    private final SaleDate saleDate;

    /**
     * Every field must be present and not null.
     */
    public Sale(SaleId saleId, Item item, Quantity quantity, SaleDate saleDate) {
        requireAllNonNull(saleId, item, quantity, saleDate);

        this.saleId = saleId;
        this.item = item;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    /**
     * Returns the sale ID.
     */
    public SaleId getSaleId() {
        return this.saleId;
    }

    /**
     * Returns the item.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns the sold quantity.
     */
    public Quantity getSaleQuantity() {
        return this.quantity;
    }

    /**
     * Returns the SaleDate
     */
    public SaleDate getSaleDate() {
        return this.saleDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Sale)) {
            return false;
        }

        Sale otherSale = (Sale) other;
        return otherSale.getSaleId().equals(getSaleId())
                && otherSale.getSaleQuantity().equals(getSaleQuantity())
                && otherSale.getItem().equals(getItem())
                && otherSale.getSaleDate().equals(getSaleDate());
    }

    @Override
    public String toString() {
        return getSaleId() + ": [" + getSaleDate().toString() + "] " + getSaleQuantity().toString() + "x "
                + getItem().getName().toString();
    }
}
