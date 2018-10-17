package seedu.inventory.testutil;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.inventory.model.Model;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void viewItem() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyInventory newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyInventory getInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteItem(Item target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void viewPurchaseOrder() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPurchaseOrder(PurchaseOrder po) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePurchaseOrder(PurchaseOrder target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updatePurchaseOrder(PurchaseOrder target, PurchaseOrder editedPurchaseOrder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPurchaseOrderList(Predicate<PurchaseOrder> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<PurchaseOrder> getFilteredPurchaseOrderList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void createSale(Sale sale) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSale(String id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void listSales(String records) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlySaleList getSaleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStaff(Staff staff) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStaff(Staff staff) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStaff(Staff target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateStaff(Staff target, Staff editedStaff) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void authenticateUser(Staff toLogin) {
        throw new AssertionError("This method should not be called.");
    }
}
