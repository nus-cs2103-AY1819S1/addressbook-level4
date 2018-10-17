package seedu.inventory.model;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.staff.Staff;

/**
 * Unmodifiable view of an inventory
 */
public interface ReadOnlyInventory {

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

    /**
     * Returns an unmodifiable view of the purchase order list.
     */
    ObservableList<PurchaseOrder> getPurchaseOrderList();

    /**
     * Returns an unmodifiable view of the staffs list.
     * This list will not contain any duplicate staffs.
     */
    ObservableList<Staff> getStaffList();

    /**
     * Returns the specific item by Sku.
     */
    Item getItemBySku(String sku);

}
