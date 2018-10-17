package seedu.inventory.testutil.purchaseorder;

import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_REQUIRED_DATE_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_REQUIRED_DATE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_STATUS_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_STATUS_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SUPPLIER_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SUPPLIER_SONY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.TypicalItems;

/**
 * A utility class containing a list of {@code PurchaseOrder} objects to be used in tests.
 */

public class TypicalPurchaseOrder {

    public static final PurchaseOrder LGPO = new PurchaseOrderBuilder()
            .withSku(TypicalItems.LG.getSku().value)
            .withName(TypicalItems.LG.getName().fullName)
            .withQuantity("20")
            .withRequiredDate("2018-12-03")
            .withSupplier("LG Corporation")
            .withStatus("PENDING").build();

    public static final PurchaseOrder SAMSUNGNOTEPO = new PurchaseOrderBuilder()
            .withSku(TypicalItems.SAMSUNGNOTE.getSku().value)
            .withName(TypicalItems.SAMSUNGNOTE.getName().fullName)
            .withQuantity("79")
            .withRequiredDate("2018-12-11")
            .withSupplier("Samsung Corporation")
            .withStatus("PENDING").build();

    public static final PurchaseOrder IPHONEPO = new PurchaseOrderBuilder()
            .withSku(TypicalItems.IPHONE.getSku().value)
            .withName(TypicalItems.IPHONE.getName().fullName)
            .withQuantity("29")
            .withRequiredDate("2018-12-11")
            .withSupplier("Apple Inc,")
            .withStatus("PENDING").build();

    // Manually added - Purchase order's details found in {@code CommandTestUtil}
    public static final PurchaseOrder OPPOPO = new PurchaseOrderBuilder().withSku(VALID_SKU_OPPO)
            .withName(VALID_NAME_OPPO).withQuantity(VALID_QUANTITY_OPPO).withRequiredDate(VALID_REQUIRED_DATE_OPPO)
            .withSupplier(VALID_SUPPLIER_OPPO).withStatus(VALID_STATUS_OPPO).build();

    public static final PurchaseOrder SONYPO = new PurchaseOrderBuilder().withSku(VALID_SKU_SONY)
            .withName(VALID_NAME_SONY).withQuantity(VALID_QUANTITY_SONY).withRequiredDate(VALID_REQUIRED_DATE_SONY)
            .withSupplier(VALID_SUPPLIER_SONY).withStatus(VALID_STATUS_SONY).build();

    private TypicalPurchaseOrder() {
    } // prevents instantiation

    /**
     * Returns an {@code Inventory} with all the typical items.
     */
    public static Inventory getTypicalInventory() {
        Inventory inventory = new Inventory();
        for (Item item : TypicalItems.getTypicalItems()) {
            inventory.addItem(item);
        }
        for (PurchaseOrder po : getTypicalPurchaseOrder()) {
            inventory.addPurchaseOrder(po);
        }
        return inventory;
    }

    public static List<PurchaseOrder> getTypicalPurchaseOrder() {
        return new ArrayList<>(Arrays.asList(LGPO, SAMSUNGNOTEPO));
    }

}

