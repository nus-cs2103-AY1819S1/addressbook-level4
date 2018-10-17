package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.Inventory;
import seedu.inventory.testutil.TypicalItems;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;

public class XmlSerializableInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableInventoryTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemsInventory.xml");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemInventory.xml");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemInventory.xml");
    private static final Path TYPICAL_PURCHASE_ORDER_FILE = TEST_DATA_FOLDER
            .resolve("typicalPurchaseOrderInventory.xml");
    private static final Path INVALID_PURCHASE_ORDER_FILE = TEST_DATA_FOLDER
            .resolve("invalidPurchaseOrderInventory.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalItemsFile_success() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ITEMS_FILE,
                XmlSerializableInventory.class);
        Inventory inventoryFromFile = dataFromFile.toModelType();
        Inventory typicalItemsInventory = TypicalItems.getTypicalInventory();
        assertEquals(inventoryFromFile, typicalItemsInventory);
    }

    @Test
    public void toModelType_typicalPurchaseOrdersFile_success() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PURCHASE_ORDER_FILE,
                XmlSerializableInventory.class);
        Inventory inventoryFromFile = dataFromFile.toModelType();
        Inventory typicalPurchaseOrderInventory = TypicalPurchaseOrder.getTypicalInventory();
        assertEquals(inventoryFromFile.toString(), typicalPurchaseOrderInventory.toString());
    }

    @Test
    public void toModelType_invalidPurchaseOrderFile_throwsIllegalValueException() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(INVALID_PURCHASE_ORDER_FILE,
                XmlSerializableInventory.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidItemFile_throwsIllegalValueException() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(INVALID_ITEM_FILE,
                XmlSerializableInventory.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateItems_throwsIllegalValueException() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ITEM_FILE,
                XmlSerializableInventory.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableInventory.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

}
