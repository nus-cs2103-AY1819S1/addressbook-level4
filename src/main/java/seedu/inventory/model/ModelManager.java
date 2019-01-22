package seedu.inventory.model;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.model.AccessItemEvent;
import seedu.inventory.commons.events.model.AccessPurchaseOrderEvent;
import seedu.inventory.commons.events.model.AccessSaleEvent;
import seedu.inventory.commons.events.model.AccessStaffEvent;
import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.model.ItemListExportEvent;
import seedu.inventory.commons.events.model.ItemListImportEvent;
import seedu.inventory.commons.events.model.PurchaseOrderListExportEvent;
import seedu.inventory.commons.events.model.PurchaseOrderListImportEvent;
import seedu.inventory.commons.events.model.SaleListChangedEvent;
import seedu.inventory.commons.events.model.SaleListExportEvent;
import seedu.inventory.commons.events.model.SaleListImportEvent;
import seedu.inventory.commons.events.model.StaffListChangedEvent;
import seedu.inventory.commons.events.model.StaffListExportEvent;
import seedu.inventory.commons.events.model.StaffListImportEvent;
import seedu.inventory.commons.events.storage.ItemListUpdateEvent;
import seedu.inventory.commons.events.storage.PurchaseOrderListUpdateEvent;
import seedu.inventory.commons.events.storage.SaleListUpdateEvent;
import seedu.inventory.commons.events.storage.StaffListUpdateEvent;
import seedu.inventory.commons.events.ui.ClearBrowserPanelEvent;
import seedu.inventory.commons.events.ui.ShowDefaultPageEvent;
import seedu.inventory.commons.events.ui.ShowItemTableViewEvent;
import seedu.inventory.commons.events.ui.ShowPurchaseOrderTableViewEvent;
import seedu.inventory.commons.events.ui.ShowSaleTableViewEvent;
import seedu.inventory.commons.events.ui.ShowStaffTableViewEvent;
import seedu.inventory.commons.events.ui.ToggleSidePanelVisibilityEvent;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;


/**
 * Represents the in-memory model of the inventory data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private FilteredList accessedList;

    private final VersionedInventory versionedInventory;
    private final FilteredList<Item> filteredItems;
    private final FilteredList<PurchaseOrder> filteredPurchaseOrder;
    private final FilteredList<Staff> filteredStaffs;
    private UserSession session;

    /**
     * Initializes a ModelManager with the given inventory and userPrefs.
     */
    public ModelManager(ReadOnlyInventory inventory, UserPrefs userPrefs, ReadOnlySaleList readOnlySaleList) {
        super();
        requireAllNonNull(inventory, userPrefs, readOnlySaleList);

        logger.fine("Initializing with inventory: " + inventory + " and user prefs " + userPrefs);

        versionedInventory = new VersionedInventory(inventory);
        filteredItems = new FilteredList<>(versionedInventory.getItemList());
        filteredPurchaseOrder = new FilteredList<>(versionedInventory.getPurchaseOrderList());
        filteredStaffs = new FilteredList<>(versionedInventory.getStaffList());
        accessedList = filteredItems;
        session = new UserSession();
    }

    public ModelManager() {
        this(new Inventory(), new UserPrefs(), new SaleList());
    }

    @Override
    public void resetData(ReadOnlyInventory newData) {
        versionedInventory.resetData(newData);
        indicateInventoryChanged();
    }

    @Override
    public void resetItemList(ReadOnlyItemList newItemList) {
        versionedInventory.resetItemList(newItemList);
        indicateInventoryChanged();
    }

    @Override
    public void resetSaleList(ReadOnlySaleList newSaleList) {
        versionedInventory.resetSaleList(newSaleList);
        indicateSaleListChanged();
    }

    @Override
    public void resetStaffList(ReadOnlyStaffList newStaffList) {
        versionedInventory.resetStaffList(newStaffList);
        indicateStaffListChanged();
    }

    @Override
    public void resetPurchaseOrderList(ReadOnlyPurchaseOrderList newPurchaseOrderList) {
        versionedInventory.resetPurchaseOrderList(newPurchaseOrderList);
        indicateInventoryChanged();
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return versionedInventory;
    }

    @Override
    public <T> FilteredList<T> getAccessedList() {
        return accessedList;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateInventoryChanged() {
        raise(new InventoryChangedEvent(versionedInventory));
    }

    /**
     * Raises an event to indicate accessing item
     */
    private void indicateAccessItem() {
        raise(new AccessItemEvent());
    }

    /**
     * Raises an event to indicate accessing purchase order
     */
    private void indicatePurchaseOrder() {
        raise(new AccessPurchaseOrderEvent());
    }

    //=========== Reporting  ===============================================================================
    @Override
    public void showItemTableView() {
        raise(new ShowItemTableViewEvent());
    }

    @Override
    public void showSaleTableView() {
        raise(new ShowSaleTableViewEvent());
    }

    @Override
    public void showStaffTableView() {
        raise(new ShowStaffTableViewEvent());
    }

    @Override
    public void showPurchaseOrderTableView() {
        raise(new ShowPurchaseOrderTableViewEvent());
    }

    @Override
    public void exportItemList(Path filePath) {
        indicateAccessItem();
        showItemTableView();
        raise(new ItemListExportEvent(versionedInventory, filePath));

    }

    @Override
    public void importItemList(Path filePath) {
        indicateAccessItem();
        showItemTableView();
        raise(new ItemListImportEvent(filePath));
    }

    @Override
    public void exportSaleList(Path filePath) {
        indicateAccessSale();
        showSaleTableView();
        raise(new SaleListExportEvent(versionedInventory, filePath));
    }

    @Override
    public void importSaleList(Path filePath) {
        indicateAccessSale();
        showSaleTableView();
        raise(new SaleListImportEvent(versionedInventory, filePath));
    }

    @Override
    public void exportStaffList(Path filePath) {
        indicateAccessStaff();
        showStaffTableView();
        raise(new StaffListExportEvent(versionedInventory, filePath));
    }

    @Override
    public void importStaffList(Path filePath) {
        indicateAccessStaff();
        showStaffTableView();
        raise(new StaffListImportEvent(filePath));
    }

    @Override
    public void exportPurchaseOrderList(Path filePath) {
        indicatePurchaseOrder();
        showPurchaseOrderTableView();
        raise(new PurchaseOrderListExportEvent(versionedInventory, filePath));
    }

    @Override
    public void importPurchaseOrderList(Path filePath) {
        indicatePurchaseOrder();
        showPurchaseOrderTableView();
        raise(new PurchaseOrderListImportEvent(versionedInventory, filePath));
    }

    //=========== Item  ====================================================================================

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedInventory.hasItem(item);
    }

    @Override
    public void viewItem() {
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        accessedList = filteredItems;
        indicateAccessItem();
        raise(new ClearBrowserPanelEvent());
    }

    @Override
    public void viewLowQuantity() {
        updateFilteredItemList(PREDICATE_SHOW_ALL_LOW_QUANTITY);
        accessedList = filteredItems;
        indicateAccessItem();
        raise(new ClearBrowserPanelEvent());
    }

    @Override
    public void deleteItem(Item target) {
        versionedInventory.removeItem(target);
        indicateInventoryChanged();
    }

    @Override
    public void addItem(Item item) {
        versionedInventory.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateInventoryChanged();
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);
        versionedInventory.updateItem(target, editedItem);
        indicateInventoryChanged();
    }

    //=========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    //=========== Purchase Order ==========================================================================

    @Override
    public boolean hasPurchaseOrder(PurchaseOrder po) {
        requireNonNull(po);
        return versionedInventory.hasPurchaseOrder(po);
    }

    @Override
    public boolean hasPurchaseOrder(Item item) {
        requireNonNull(item);
        return versionedInventory.hasPurchaseOrder(item);
    }

    @Override
    public void addPurchaseOrder(PurchaseOrder po) {
        versionedInventory.addPurchaseOrder(po);
        updateFilteredPurchaseOrderList(PREDICATE_SHOW_ALL_PURCHASE_ORDER);
        indicateInventoryChanged();
    }

    @Override
    public void viewPurchaseOrder() {
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        accessedList = filteredPurchaseOrder;
        indicatePurchaseOrder();
    }

    @Override
    public void deletePurchaseOrder(int target) {
        versionedInventory.removePurchaseOrder(target);
        indicateInventoryChanged();
    }
    @Override
    public void deletePurchaseOrder(Item target) {
        versionedInventory.removePurchaseOrder(target);
        indicateInventoryChanged();
    }

    @Override
    public void updatePurchaseOrder(int target, PurchaseOrder editedPurchaseOrder) {
        requireNonNull(editedPurchaseOrder);
        versionedInventory.updatePurchaseOrder(target, editedPurchaseOrder);
        indicateInventoryChanged();
    }

    @Override
    public void updatePurchaseOrder(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);
        versionedInventory.updatePurchaseOrder(target, editedItem);
        indicateInventoryChanged();
    }

    @Override
    public void approvePurchaseOrder(int target, PurchaseOrder targetPo) {
        versionedInventory.approvePurchaseOrder(target, targetPo);
        indicateInventoryChanged();
    }

    @Override
    public void rejectPurchaseOrder(int target, PurchaseOrder targetPo) {
        versionedInventory.rejectPurchaseOrder(target, targetPo);
        indicateInventoryChanged();
    }

    //=========== User Management ===========================================
    @Override
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return versionedInventory.hasStaff(staff);
    }

    @Override
    public boolean hasUsername(Staff staff) {
        requireNonNull(staff);
        return versionedInventory.hasUsername(staff);
    }

    @Override
    public void deleteStaff(Staff target) {
        requireNonNull(target);
        versionedInventory.removeStaff(target);
        indicateStaffListChanged();
    }

    @Override
    public void addStaff(Staff staff) {
        requireNonNull(staff);
        versionedInventory.addStaff(staff);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        indicateStaffListChanged();
    }

    @Override
    public void updateStaff(Staff target, Staff editedStaff) {
        requireAllNonNull(target, editedStaff);
        versionedInventory.updateStaff(target, editedStaff);
        indicateStaffListChanged();
    }

    @Override
    public void viewStaff() {
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        accessedList = filteredStaffs;
        indicateAccessStaff();
    }

    /**
     * Raises an event to indicate accessing item
     */
    private void indicateAccessStaff() {
        raise(new AccessStaffEvent());
    }


    //=========== Filtered Purchase Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code PurchaseOrder} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<PurchaseOrder> getFilteredPurchaseOrderList() {
        return FXCollections.unmodifiableObservableList(filteredPurchaseOrder);
    }

    @Override
    public void updateFilteredPurchaseOrderList(Predicate<PurchaseOrder> predicate) {
        requireNonNull(predicate);
        filteredPurchaseOrder.setPredicate(predicate);
    }


    // ================ Filtered Staff list accessors=============

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return FXCollections.unmodifiableObservableList(filteredStaffs);
    }

    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateStaffListChanged() {
        raise(new StaffListChangedEvent(versionedInventory));
    }

    //================ Authentication ========================
    @Override
    public void authenticateUser(Staff toLogin) {
        requireNonNull(toLogin);

        session = new UserSession(toLogin);
        raise(new ToggleSidePanelVisibilityEvent(true));
    }

    @Override
    public Staff retrieveStaff(Staff toRetrieve) {
        requireNonNull(toRetrieve);

        return versionedInventory.retrieveStaff(toRetrieve);
    }

    @Override
    public void updateUserSession(Staff staff) {
        session.updateUser(staff);
    }

    @Override
    public void logoutUser() {
        session.logout();
        versionedInventory.reset();
        raise(new ToggleSidePanelVisibilityEvent(false));
        raise(new ShowDefaultPageEvent());
    }

    @Override
    public boolean isUserLoggedIn() {
        return session.isLoggedIn();
    }

    @Override
    public Staff getUser() {
        return this.session.getUser();
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoInventory() {
        return versionedInventory.canUndo();
    }

    @Override
    public boolean canRedoInventory() {
        return versionedInventory.canRedo();
    }

    @Override
    public void undoInventory() {
        versionedInventory.undo();
        indicateInventoryChanged();
        indicateSaleListChanged();
    }

    @Override
    public void redoInventory() {
        versionedInventory.redo();
        indicateInventoryChanged();
        indicateSaleListChanged();
    }

    @Override
    public void commitInventory() {
        versionedInventory.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedInventory.equals(other.versionedInventory)
                && filteredItems.equals(other.filteredItems);
    }

    //=========== Sale ====================================================================================
    @Override
    public ReadOnlySaleList getSaleList() {
        return versionedInventory;
    }

    @Override
    public ObservableList<Sale> getObservableSaleList() {
        return FXCollections.unmodifiableObservableList(versionedInventory.getSaleList());
    }

    @Override
    public void addSale(Sale sale) {
        versionedInventory.addSale(sale);
        indicateSaleListChanged();
    }

    @Override
    public void deleteSale(Sale sale) {
        versionedInventory.removeSale(sale);
        indicateSaleListChanged();
    }

    @Override
    public void listSales() {
        indicateAccessSale();
    }

    /**
     * Raises an event to indicate accessing sale
     */
    private void indicateAccessSale() {
        raise(new AccessSaleEvent());
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateSaleListChanged() {
        raise(new SaleListChangedEvent(versionedInventory));
    }

    @Override
    @Subscribe
    public void handleItemListUpdateEvent(ItemListUpdateEvent event) {
        resetItemList(event.itemList);
    }

    @Override
    @Subscribe
    public void handleSaleListUpdateEvent(SaleListUpdateEvent event) {
        resetSaleList(event.saleList);
    }

    @Override
    @Subscribe
    public void handleStaffListUpdateEvent(StaffListUpdateEvent event) {
        StaffList staffList = new StaffList(event.staffList);
        if (!staffList.hasStaff(getUser())) {
            staffList.addStaff(getUser());
        }
        resetStaffList(staffList);
    }

    @Override
    @Subscribe
    public void handlePurchaseOrderListUpdateEvent(PurchaseOrderListUpdateEvent event) {
        resetPurchaseOrderList(event.purchaseOrderList);
    }

}
