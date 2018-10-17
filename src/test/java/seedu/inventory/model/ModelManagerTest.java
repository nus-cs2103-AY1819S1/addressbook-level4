package seedu.inventory.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_PURCHASE_ORDER;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.IPHONEPO;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.SAMSUNGNOTEPO;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.NameContainsKeywordsPredicate;
import seedu.inventory.testutil.InventoryBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemInInventory_returnsTrue() {
        modelManager.addItem(IPHONE);
        assertTrue(modelManager.hasItem(IPHONE));
    }

    @Test
    public void getFilteredInventoryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void hasPurchaseOrder_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPurchaseOrder(null);
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderInInventory_returnsFalse() {
        modelManager.addPurchaseOrder(IPHONEPO);
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void getFilteredPurchaseOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPurchaseOrderList().remove(0);
    }

    @Test
    public void equals() {
        Inventory inventory = new InventoryBuilder()
                .withItem(IPHONE)
                .withItem(SAMSUNG)
                .withPurchaseOrder(IPHONEPO)
                .withPurchaseOrder(SAMSUNGNOTEPO)
                .build();
        Inventory differentInventory = new Inventory();
        UserPrefs userPrefs = new UserPrefs();
        SaleList saleList = new SaleList();
        StaffList staffList = new StaffList();

        // same values -> returns true
        modelManager = new ModelManager(inventory, userPrefs, saleList, staffList);
        ModelManager modelManagerCopy = new ModelManager(inventory, userPrefs, saleList, staffList);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager == null);

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentInventory, userPrefs, saleList, staffList)));

        // different filteredItemList -> returns false
        String[] keywords = IPHONE.getName().fullName.split("\\s+");
        modelManager.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(inventory, userPrefs, saleList, staffList)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        modelManager.updateFilteredPurchaseOrderList(PREDICATE_SHOW_ALL_PURCHASE_ORDER);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(inventory, differentUserPrefs, saleList, staffList)));
    }
}
