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
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;

public class XmlSerializableSaleListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableSaleListTest");
    private static final Path TEST_SALE_LIST_FILE = TEST_DATA_FOLDER.resolve("saleListTest.xml");
    private static final Path INVALID_SALE_LIST_FILE = TEST_DATA_FOLDER.resolve("invalidSaleList.xml");
    private static final Path DUPLICATE_SALE_LIST_FILE = TEST_DATA_FOLDER.resolve("duplicateSaleList.xml");

    private static SaleId saleId = new SaleId("1");
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_testSaleListFile_success() throws Exception {
        XmlSerializableSaleList dataFromFile = XmlUtil.getDataFromFile(TEST_SALE_LIST_FILE,
                XmlSerializableSaleList.class);

        Inventory typicalInventory = TypicalItems.getTypicalInventory();

        SaleList saleListFromFile = dataFromFile.toModelType(typicalInventory);

        SaleList defaultSaleList = new SaleList();
        defaultSaleList.addSale(new Sale(saleId, item, quantity, saleDate));

        assertEquals(saleListFromFile, defaultSaleList);
    }

    @Test
    public void toModelType_invalidSaleListFile_throwsIllegalValueException() throws Exception {
        Inventory typicalInventory = TypicalItems.getTypicalInventory();

        XmlSerializableSaleList dataFromFile = XmlUtil.getDataFromFile(INVALID_SALE_LIST_FILE,
                XmlSerializableSaleList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType(typicalInventory);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        Inventory typicalInventory = TypicalItems.getTypicalInventory();

        XmlSerializableSaleList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_SALE_LIST_FILE,
                XmlSerializableSaleList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableSaleList.MESSAGE_DUPLICATE_SALE);
        dataFromFile.toModelType(typicalInventory);
    }

}
