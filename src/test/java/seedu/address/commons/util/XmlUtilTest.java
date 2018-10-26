package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWishes.getTypicalWishTransaction;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.WishBook;
import seedu.address.model.WishTransaction;
import seedu.address.model.wish.Wish;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlAdaptedWish;
import seedu.address.storage.XmlSerializableWishBook;
import seedu.address.storage.XmlWishTransactions;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.WishBookBuilder;
import seedu.address.testutil.WishBuilder;


public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validWishBook.xml");
    private static final Path MISSING_WISH_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingWishField.xml");
    private static final Path INVALID_WISH_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidWishField.xml");
    private static final Path VALID_WISH_FILE = TEST_DATA_FOLDER.resolve("validWish.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempWishBook.xml");

    private static final Path VALID_WISHTRANSACTION_FILE = TEST_DATA_FOLDER.resolve("validWishTransaction.xml");
    private static final Path TEMP_WISHTRANSACTION_FILE =
            TestUtil.getFilePathInSandboxFolder("tempWishTransaction.xml");

    private static final String INVALID_PRICE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PRICE = "9482424";
    private static final String VALID_DATE = "24/09/2020";
    private static final String VALID_URL = "https://www.amazon.com/gp/product/B07D998212";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));
    private static final String VALID_ID = "ae536134-96c8-45b1-a68e-c872493a935a";

    private static final String VALID_WISHTRANSACTION_NAME = "George Best";
    private static final String VALID_WISHTRANSACTION_PRICE = "94.82";
    private static final String VALID_WISHTRANSACTION_SAVED_AMT = "0.00";
    private static final String VALID_WISHTRANSACTION_DATE = "24/09/2020";
    private static final String VALID_WISHTRANSACTION_URL =
            "https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV";
    private static final String VALID_WISHTRANSACTION_REMARK = "";
    private static final String VALID_WISHTRANSACTION_ID = "1eb6564f-7b01-4c5c-a2c5-37678131c3e4";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, WishBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, WishBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, WishBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        WishBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableWishBook.class).toModelType();
        assertEquals(9, dataFromFile.getWishList().size());

        WishTransaction retrieved = XmlUtil.getDataFromFile(VALID_WISHTRANSACTION_FILE, XmlWishTransactions.class)
                .toModelType();
        assertEquals(8, retrieved.getWishMap().size());
    }

    @Test
    public void xmlAdaptedWishFromFile_fileWithMissingWishField_validResult() throws Exception {
        XmlAdaptedWish actualWish = XmlUtil.getDataFromFile(
                MISSING_WISH_FIELD_FILE, XmlAdaptedWishWithRootElement.class);
        XmlAdaptedWish expectedWish = new XmlAdaptedWish(
                null, VALID_PRICE, VALID_DATE, VALID_URL, VALID_TAGS, VALID_ID);
        assertEquals(expectedWish, actualWish);
    }

    @Test
    public void xmlAdaptedWishFromFile_fileWithInvalidWishField_validResult() throws Exception {
        XmlAdaptedWish actualWish = XmlUtil.getDataFromFile(
                INVALID_WISH_FIELD_FILE, XmlAdaptedWishWithRootElement.class);
        XmlAdaptedWish expectedWish = new XmlAdaptedWish(VALID_NAME, INVALID_PRICE, VALID_DATE, VALID_URL, VALID_TAGS,
                VALID_ID);
        assertEquals(expectedWish, actualWish);
    }

    @Test
    public void xmlAdaptedWishFromFile_fileWithValidWish_validResult() throws Exception {
        XmlAdaptedWish actualWish = XmlUtil.getDataFromFile(
                VALID_WISH_FILE, XmlAdaptedWishWithRootElement.class);
        XmlAdaptedWish expectedWish = new XmlAdaptedWish(
                VALID_NAME, VALID_PRICE, VALID_DATE, VALID_URL, VALID_TAGS, VALID_ID);
        assertEquals(expectedWish, actualWish);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new WishBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new WishBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableWishBook dataToWrite = new XmlSerializableWishBook(new WishBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableWishBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWishBook.class);
        assertEquals(dataToWrite, dataFromFile);

        WishBookBuilder builder = new WishBookBuilder(new WishBook());
        dataToWrite = new XmlSerializableWishBook(
                builder.withWish(new WishBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWishBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    @Test
    public void xmlWishTransactionsFromFile_fileWithValidWishTransaction_validResult() throws Exception {
        WishTransaction retrievedWishTransaction = XmlUtil
                .getDataFromFile(VALID_WISHTRANSACTION_FILE, XmlWishTransactions.class)
                .toModelType();
        Wish containedWish = getWish();
        UUID key = containedWish.getId();
        assertTrue(retrievedWishTransaction.getWishMap().containsKey(key));
        assertTrue(retrievedWishTransaction.getWishMap().get(key).contains(containedWish));
    }

    private static Wish getWish() {
        return new WishBuilder()
                .withName(VALID_WISHTRANSACTION_NAME)
                .withPrice(VALID_WISHTRANSACTION_PRICE)
                .withSavedAmountIncrement(VALID_WISHTRANSACTION_SAVED_AMT)
                .withDate(VALID_WISHTRANSACTION_DATE)
                .withUrl(VALID_WISHTRANSACTION_URL)
                .withId(VALID_WISHTRANSACTION_ID)
                .build();
    }

    @Test
    public void saveDataToFile_validWishTransactionFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_WISHTRANSACTION_FILE);
        XmlWishTransactions dataToWrite = new XmlWishTransactions(new WishTransaction());
        XmlUtil.saveDataToFile(TEMP_WISHTRANSACTION_FILE, dataToWrite);
        XmlWishTransactions dataFromFile = XmlUtil
                .getDataFromFile(TEMP_WISHTRANSACTION_FILE, XmlWishTransactions.class);
        assertEquals(dataToWrite, dataFromFile);

        WishBookBuilder builder = new WishBookBuilder(new WishBook());
        WishTransaction wishTransaction = new WishTransaction(
                builder.withWish(new WishBuilder().build()).build());
        dataToWrite = new XmlWishTransactions(wishTransaction);

        XmlUtil.saveDataToFile(TEMP_WISHTRANSACTION_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_WISHTRANSACTION_FILE, XmlWishTransactions.class);
        assertEquals(dataToWrite, dataFromFile);

        WishTransaction typicalWishTransaction = getTypicalWishTransaction();
        XmlUtil.saveDataToFile(TEMP_WISHTRANSACTION_FILE, new XmlWishTransactions(typicalWishTransaction));
        WishTransaction retrieved = XmlUtil.getDataFromFile(TEMP_WISHTRANSACTION_FILE, XmlWishTransactions.class)
                .toModelType();
        assertEquals(typicalWishTransaction, retrieved);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedWish}
     * objects.
     */
    @XmlRootElement(name = "wish")
    private static class XmlAdaptedWishWithRootElement extends XmlAdaptedWish {}
}
