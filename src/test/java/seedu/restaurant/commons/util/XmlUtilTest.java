package seedu.restaurant.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_ITEM_PRICE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_EMPTY_REQUIRED_INGREDIENTS;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_FRIES_TAGS;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_PERCENT;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_RECIPE_FRIES;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.storage.XmlSerializableRestaurantBook;
import seedu.restaurant.storage.elements.XmlAdaptedItem;
import seedu.restaurant.testutil.RestaurantBookBuilder;
import seedu.restaurant.testutil.TestUtil;
import seedu.restaurant.testutil.menu.ItemBuilder;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validRestaurantBook.xml");
    private static final Path MISSING_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingItemField.xml");
    private static final Path INVALID_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidItemField.xml");
    private static final Path VALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("validItem.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempRestaurantBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, RestaurantBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, RestaurantBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, RestaurantBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        RestaurantBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableRestaurantBook.class)
                .toModelType();
        assertEquals(2, dataFromFile.getItemList().size());
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithMissingPersonField_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil
                .getDataFromFile(MISSING_ITEM_FIELD_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(null, VALID_ITEM_PRICE_FRIES,
                VALID_ITEM_PERCENT, VALID_ITEM_RECIPE_FRIES, VALID_FRIES_TAGS, VALID_EMPTY_REQUIRED_INGREDIENTS);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithInvalidPersonField_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                INVALID_ITEM_FIELD_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(VALID_ITEM_NAME_FRIES, INVALID_ITEM_PRICE,
                VALID_ITEM_PERCENT, VALID_ITEM_RECIPE_FRIES, VALID_FRIES_TAGS, VALID_EMPTY_REQUIRED_INGREDIENTS);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithValidPerson_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                VALID_ITEM_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(VALID_ITEM_NAME_FRIES, VALID_ITEM_PRICE_FRIES,
                VALID_ITEM_PERCENT, VALID_ITEM_RECIPE_FRIES, VALID_FRIES_TAGS, VALID_EMPTY_REQUIRED_INGREDIENTS);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new RestaurantBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new RestaurantBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableRestaurantBook dataToWrite = new XmlSerializableRestaurantBook(new RestaurantBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableRestaurantBook dataFromFile = XmlUtil
                .getDataFromFile(TEMP_FILE, XmlSerializableRestaurantBook.class);
        assertEquals(dataToWrite, dataFromFile);

        RestaurantBookBuilder builder = new RestaurantBookBuilder(new RestaurantBook());
        dataToWrite = new XmlSerializableRestaurantBook(builder.withItem(new ItemBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRestaurantBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedAccount}
     * objects.
     */
    @XmlRootElement(name = "items")
    private static class XmlAdaptedItemWithRootElement extends XmlAdaptedItem {}
}
