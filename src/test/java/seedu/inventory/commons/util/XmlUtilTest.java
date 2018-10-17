package seedu.inventory.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.Inventory;
import seedu.inventory.storage.XmlAdaptedItem;
import seedu.inventory.storage.XmlAdaptedTag;
import seedu.inventory.storage.XmlSerializableInventory;
import seedu.inventory.testutil.InventoryBuilder;
import seedu.inventory.testutil.ItemBuilder;
import seedu.inventory.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validInventory.xml");
    private static final Path MISSING_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingItemField.xml");
    private static final Path INVALID_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidItemField.xml");
    private static final Path VALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("validItem.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempInventory.xml");

    private static final String INVALID_QUANTITY = "9482asf424";

    private static final String VALID_NAME = "iPhone XR";
    private static final String VALID_PRICE = "1500.00";
    private static final String VALID_QUANTITY = "9482424";
    private static final String VALID_SKU = "iphone-xr";
    private static final String VALID_IMAGE = "docs/images/iphone.jpg";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("iphone"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Inventory.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Inventory.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Inventory.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Inventory dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableInventory.class).toModelType();
        assertEquals(9, dataFromFile.getItemList().size());
    }

    @Test
    public void xmlAdaptedItemFromFile_fileWithMissingItemField_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                MISSING_ITEM_FIELD_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(
                null, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void xmlAdaptedItemFromFile_fileWithInvalidItemField_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                INVALID_ITEM_FIELD_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedPerson = new XmlAdaptedItem(
                VALID_NAME, VALID_PRICE, INVALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        assertEquals(expectedPerson, actualItem);
    }

    @Test
    public void xmlAdaptedItemFromFile_fileWithValidItem_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                VALID_ITEM_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedPerson = new XmlAdaptedItem(
                VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        assertEquals(expectedPerson, actualItem);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Inventory());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Inventory());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableInventory dataToWrite = new XmlSerializableInventory(new Inventory());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableInventory.class);
        assertEquals(dataToWrite, dataFromFile);

        InventoryBuilder builder = new InventoryBuilder(new Inventory());
        dataToWrite = new XmlSerializableInventory(
                builder.withItem(new ItemBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableInventory.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedItem}
     * objects.
     */
    @XmlRootElement(name = "item")
    private static class XmlAdaptedItemWithRootElement extends XmlAdaptedItem {}
}
