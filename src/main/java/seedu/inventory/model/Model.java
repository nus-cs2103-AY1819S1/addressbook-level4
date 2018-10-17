package seedu.inventory.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<PurchaseOrder> PREDICATE_SHOW_ALL_PURCHASE_ORDER = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyInventory newData);

    /**
     * Returns the Inventory
     */
    ReadOnlyInventory getInventory();

    //=========== Item API =============================================================

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory.
     */
    boolean hasItem(Item item);

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory.
     */
    void viewItem();

    /**
     * Deletes the given item.
     * The item must exist in the inventory.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the inventory.
     */
    void addItem(Item item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the inventory.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory.
     */
    void updateItem(Item target, Item editedItem);

    /**
     * Returns an unmodifiable view of the filtered item list
     */
    ObservableList<Item> getFilteredItemList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemList(Predicate<Item> predicate);

    //=========== Purchase Order API =============================================================

    /**
     * Returns true if a purchase order with the same identity as {@code item} exists in the inventory.
     */
    boolean hasPurchaseOrder(PurchaseOrder item);

    /**
     * Deletes the given purchase order.
     * The purchase order must exist in the inventory.
     */
    void viewPurchaseOrder();

    /**
     * Deletes the given purchase order.
     * The purchase order must exist in the inventory.
     */
    void deletePurchaseOrder(PurchaseOrder target);

    /**
     * Adds the given purchase order.
     * {@code purchaseorder} must not already exist in the inventory.
     */
    void addPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * Replaces the given purchaseorder {@code target} with {@code editedPurchaseOrder}.
     * {@code target} must exist in the inventory.
     */
    void updatePurchaseOrder(PurchaseOrder target, PurchaseOrder editedPurchaseOrder);

    /**
     * Returns an unmodifiable view of the filtered purchase order list
     */
    ObservableList<PurchaseOrder> getFilteredPurchaseOrderList();

    /**
     * Updates the filter of the filtered purchase order list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPurchaseOrderList(Predicate<PurchaseOrder> predicate);


    //=========== Undo/Redo Api =============================================================

    /**
     * Returns true if a staff with the same identity as {@code item} exists in the staff list.
     *
     * @param staff the staff to be checked
     */
    boolean hasStaff(Staff staff);

    /**
     * Deletes the given staff.
     * The staff must exist in the staff list.
     *
     * @param target staff to be deleted must be in the list
     */
    void deleteStaff(Staff target);

    /**
     * Adds the given staff.
     * {@code staff} must not already exist in the inventory.
     *
     * @param staff the staff to be added must not be in the list.
     */
    void addStaff(Staff staff);

    /**
     * Replaces the given staff {@code target} with {@code editedStaff}.
     * {@code target} must exist in the staff list.
     * The staff identity of {@code editedStaff} must not be the same as another existing staff in the staff list.
     *
     * @param target      the staff to be updated
     * @param editedStaff the staff with updated info
     */
    void updateStaff(Staff target, Staff editedStaff);

    /**
     * Returns an unmodifiable view of the filtered staff list
     */
    ObservableList<Staff> getFilteredStaffList();

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStaffList(Predicate<Staff> predicate);

    /**
     * Authenticate the user.
     */
    void authenticateUser(Staff staff);

    /**
     * Returns true if the model has previous inventory states to restore.
     */
    boolean canUndoInventory();

    /**
     * Returns true if the model has undone inventory states to restore.
     */
    boolean canRedoInventory();

    /**
     * Restores the model's inventory to its previous state.
     */
    void undoInventory();

    /**
     * Restores the model's inventory to its previously undone state.
     */
    void redoInventory();

    /**
     * Saves the current inventory state for undo/redo.
     */
    void commitInventory();

    /**
     * Returns the sale list.
     */
    ReadOnlySaleList getSaleList();

    /**
     * Create a new sale.
     */
    void createSale(Sale sale);

    /**
     * Delete a sale.
     */
    void deleteSale(String id);

    /**
     * List sales.
     */
    void listSales(String records);
}
