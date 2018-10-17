package seedu.inventory.testutil;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * A utility class to help with building Inventory objects.
 * Example usage: <br>
 *     {@code Inventory ab = new InventoryBuilder().withItem("John", "Doe").build();}
 */
public class InventoryBuilder {

    private Inventory inventory;

    public InventoryBuilder() {
        inventory = new Inventory();
    }

    public InventoryBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds a new {@code Item} to the {@code Inventory} that we are building.
     */
    public InventoryBuilder withItem(Item item) {
        inventory.addItem(item);
        return this;
    }

    /**
     * Adds a new {@code PurchaseOrder} to the {@code Inventory} that we are building.
     */
    public InventoryBuilder withPurchaseOrder(PurchaseOrder purchaseOrder) {
        inventory.addPurchaseOrder(purchaseOrder);
        return this;
    }


    public Inventory build() {
        return inventory;
    }
}
