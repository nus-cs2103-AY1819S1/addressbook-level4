package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;

public class XmlSaleListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSaleListStorageTest");
    private static final Inventory EMPTY_INVENTORY = new Inventory();

    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readSaleList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);

        readSaleList(null, EMPTY_INVENTORY);
    }

    private java.util.Optional<ReadOnlySaleList> readSaleList(String filePath, ReadOnlyInventory inventory)
            throws Exception {
        return new XmlSaleListStorage().readSaleList(addToTestDataPathIfNotNull(filePath), inventory);
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSaleList("NonExistentFile.xml", EMPTY_INVENTORY).isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readSaleList("NotXmlFormatSaleList.xml", EMPTY_INVENTORY);
    }

    @Test
    public void read_invalidSaleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readSaleList("invalidSaleList.xml", EMPTY_INVENTORY);
    }

    @Test
    public void read_validSaleList_noException() throws Exception {
        try {
            Inventory inventory = new Inventory();

            inventory.addItem(TypicalItems.IPHONE);

            readSaleList("validSaleList.xml", inventory);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error reading the file.", ioe);
        }
    }

    @Test
    public void read_validSaleListMissingItem_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readSaleList("validSaleList.xml", EMPTY_INVENTORY);
    }

    @Test
    public void readAndSaveSaleList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempSaleList.xml");
        XmlSaleListStorage xmlSaleListStorage = new XmlSaleListStorage();

        Sale sale1 = new Sale(new SaleId(("1")), item, quantity, saleDate);
        Sale sale2 = new Sale(new SaleId(("2")), item, quantity, saleDate);

        SaleList saleList = new SaleList();

        saleList.addSale(sale1);

        Inventory inventory = new Inventory();

        inventory.addItem(TypicalItems.IPHONE);

        //Save in new file and read back
        xmlSaleListStorage.saveSaleList(saleList, filePath);

        ReadOnlySaleList readBack = xmlSaleListStorage.readSaleList(filePath, inventory).get();
        assertEquals(saleList, new SaleList(readBack));

        //Modify data, overwrite exiting file, and read back
        saleList.addSale(sale2);
        saleList.removeSale(sale1);
        xmlSaleListStorage.saveSaleList(saleList, filePath);

        readBack = xmlSaleListStorage.readSaleList(filePath, inventory).get();
        assertEquals(saleList, new SaleList(readBack));

        //Save and read without specifying file path
        saleList.addSale(sale1);
        xmlSaleListStorage.saveSaleList(saleList); //file path not specified
        readBack = xmlSaleListStorage.readSaleList(inventory).get(); //file path not specified
        assertEquals(saleList, new SaleList(readBack));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void saveSaleList_nullSaleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveSaleList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code saleList} at the specified {@code filePath}.
     */
    private void saveSaleList(ReadOnlySaleList saleList, String filePath) {
        try {
            new XmlSaleListStorage()
                    .saveSaleList(saleList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSaleList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveSaleList(new SaleList(), null);
    }
}
