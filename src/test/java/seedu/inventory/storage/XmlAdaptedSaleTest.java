package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.TypicalItems;

public class XmlAdaptedSaleTest {
    private static final String INVALID_SALEID = "ABC";
    private static final String INVALID_SALESKU = "ABC.123";
    private static final String INVALID_SALEQUANTITY = "+651234";
    private static final String INVALID_SALEDATE = "01/01/1990";

    private static final String VALID_SALEID = "1";
    private static final String VALID_SALESKU = TypicalItems.IPHONE.getSku().toString();
    private static final String VALID_SALEQUANTITY = "1";
    private static final String VALID_SALEDATE = "2018-08-01";

    private static final String NOT_FOUND_SKU = "ABCDEFG";

    private static SaleId saleId = new SaleId("1");
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Test
    public void toModelType_validSaleDetails_returnsSale() throws Exception {
        Sale sale = new Sale(saleId, item, quantity, saleDate);

        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale = new XmlAdaptedSale(sale);

        assertEquals(sale, adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleID_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(INVALID_SALEID, VALID_SALESKU, VALID_SALEQUANTITY, VALID_SALEDATE);
        String expectedMessage = SaleId.MESSAGE_ID_CONSTRAINTS;

        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleID_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(null, VALID_SALESKU, VALID_SALEQUANTITY, VALID_SALEDATE);
        String expectedMessage = SaleId.MESSAGE_ID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleSku_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, INVALID_SALESKU, VALID_SALEQUANTITY, VALID_SALEDATE);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleSku_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, null, VALID_SALEQUANTITY, VALID_SALEDATE);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleQuantity_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, VALID_SALESKU, INVALID_SALEQUANTITY, VALID_SALEDATE);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleQuantity_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, VALID_SALESKU, null, VALID_SALEDATE);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleDate_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, VALID_SALESKU, VALID_SALEQUANTITY, INVALID_SALEDATE);
        String expectedMessage = SaleDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleDate_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, VALID_SALESKU, VALID_SALEQUANTITY, null);
        String expectedMessage = SaleDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_itemNotFound_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);

        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, NOT_FOUND_SKU, VALID_SALEQUANTITY, INVALID_SALEDATE);
        String expectedMessage = XmlAdaptedSale.MISSING_ITEM;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullInventory_throwsIllegalValueException() {
        XmlAdaptedSale adaptedSale =
                new XmlAdaptedSale(VALID_SALEID, VALID_SALESKU, VALID_SALEQUANTITY, VALID_SALEDATE);
        String expectedMessage = XmlAdaptedSale.MISSING_ITEM;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(null));
    }
}
