package seedu.restaurant.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBookWithItemOnly;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.commons.util.XmlUtil;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.testutil.ingredient.TypicalIngredients;


public class XmlSerializableRestaurantBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableRestaurantBookTest");

    // Account Management
    private static final Path TYPICAL_RESTAURANT_FILE = TEST_DATA_FOLDER.resolve("typicalRestaurantBook.xml");
    private static final Path INVALID_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("invalidAccountOnlyRestaurantBook.xml");
    private static final Path DUPLICATE_ACCOUNT_FILE = TEST_DATA_FOLDER
            .resolve("duplicateAccountOnlyRestaurantBook.xml");

    // Sales Management
    private static final Path INVALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordRestaurantBook.xml");
    private static final Path DUPLICATE_RECORD_FILE = TEST_DATA_FOLDER.resolve("duplicateRecordRestaurantBook.xml");

    // Ingredient Management
    private static final Path TYPICAL_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalIngredientsOnlyRestaurantBook"
            + ".xml");
    private static final Path INVALID_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("invalidIngredientOnlyRestaurantBook"
            + ".xml");
    private static final Path DUPLICATE_INGREDIENT_FILE = TEST_DATA_FOLDER
            .resolve("duplicateIngredientOnlyRestaurantBook"
                    + ".xml");
    private static final Path TYPICAL_DUPLICATE_FILE = TEST_DATA_FOLDER.resolve("duplicateAccountRestaurantBook.xml");

    // Menu Management
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemsOnlyRestaurantBook.xml");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemOnlyRestaurantBook.xml");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemOnlyRestaurantBook.xml");

    // Reservation Management
    private static final Path INVALID_RESERVATION_FILE = TEST_DATA_FOLDER.resolve(
            "invalidReservationOnlyRestaurantBook.xml");
    private static final Path DUPLICATE_RESERVATION_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateReservationOnlyRestaurantBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private XmlSerializableRestaurantBook dataFromFile = null;

    @Test
    public void toModelType_typicalRestaurantFile_success() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RESTAURANT_FILE, XmlSerializableRestaurantBook.class);
        RestaurantBook restaurantBookFromFile = dataFromFile.toModelType();
        RestaurantBook typicalRestaurantBook = getTypicalRestaurantBook();
        assertEquals(restaurantBookFromFile, typicalRestaurantBook);
    }

    @Test
    public void toModelType_invalidRecordFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_RECORD_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRecords_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RECORD_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRestaurantBook.MESSAGE_DUPLICATE_RECORD);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidIngredientFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_INGREDIENT_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateIngredients_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_INGREDIENT_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRestaurantBook.MESSAGE_DUPLICATE_INGREDIENT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_typicalIngredientsOnlyFile_success() throws Exception {
        XmlSerializableRestaurantBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_INGREDIENTS_FILE,
                XmlSerializableRestaurantBook.class);
        RestaurantBook restaurantBookFromFile = dataFromFile.toModelType();
        RestaurantBook typicalIngredientsRestaurantBook = TypicalIngredients
                .getTypicalRestaurantBookWithIngredientsOnly();
        assertEquals(restaurantBookFromFile, typicalIngredientsRestaurantBook);
    }

    //@@author AZhiKai
    // Account Management
    @Test
    public void toModelType_invalidAccountFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_ACCOUNT_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAccounts_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ACCOUNT_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRestaurantBook.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAccountInTypicalRestaurantBook_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(TYPICAL_DUPLICATE_FILE, XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRestaurantBook.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

    //@@author yican95
    // Menu Management
    @Test
    public void toModelType_typicalItemsOnlyFile_success() throws Exception {
        XmlSerializableRestaurantBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ITEMS_FILE,
                XmlSerializableRestaurantBook.class);
        RestaurantBook restaurantBookFromFile = dataFromFile.toModelType();
        RestaurantBook typicalItemsRestaurantBook = getTypicalRestaurantBookWithItemOnly();
        assertEquals(restaurantBookFromFile, typicalItemsRestaurantBook);
    }

    @Test
    public void toModelType_invalidItemOnlyFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRestaurantBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ITEM_FILE,
                XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateItemsOnly_throwsIllegalValueException() throws Exception {
        XmlSerializableRestaurantBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ITEM_FILE,
                XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRestaurantBook.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

    //Reservation Management
    //@@author m4dkip
    @Test
    public void toModelType_invalidReservationOnlyFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRestaurantBook dataFromFile = XmlUtil.getDataFromFile(INVALID_RESERVATION_FILE,
                XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateReservationOnly_throwsIllegalValueException() throws Exception {
        XmlSerializableRestaurantBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RESERVATION_FILE,
                XmlSerializableRestaurantBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRestaurantBook.MESSAGE_DUPLICATE_RESERVATION);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ITEM_FILE, XmlSerializableRestaurantBook.class);

        // same object
        assertTrue(dataFromFile.equals(dataFromFile));

        // different type
        assertFalse(dataFromFile.equals(null));
        assertFalse(dataFromFile.equals(0));
    }
}
