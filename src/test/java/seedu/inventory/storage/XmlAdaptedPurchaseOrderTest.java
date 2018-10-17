package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.storage.XmlAdaptedPurchaseOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.inventory.testutil.TypicalItems.LG;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.LGPO;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;

import seedu.inventory.testutil.Assert;

public class XmlAdaptedPurchaseOrderTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_QUANTITY = "32_+14";
    private static final String INVALID_SKU = "exam12.test";
    private static final String INVALID_REQUIRED_DATE = "21-asd-2";
    private static final String INVALID_SUPPLIER = "";
    private static final String INVALID_STATUS = "OK CAN";

    private static final String VALID_NAME = LG.getName().toString();
    private static final String VALID_QUANTITY = LG.getQuantity().toString();
    private static final String VALID_SKU = LG.getSku().toString();
    private static final String VALID_REQUIRED_DATE = LGPO.getReqDate().toString();
    private static final String VALID_SUPPLIER = LGPO.getSupplier().toString();
    private static final String VALID_STATUS = LGPO.getStatus().toString();

    @Test
    public void toModelType_validPurchaseOrderDetails_returnsPurchaseOrder() throws Exception {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(LGPO);
        assertEquals(LGPO.toString(), po.toModelType().toString());
    }

    @Test
    public void toModelType_invalidSku_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(INVALID_SKU, VALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_nullSku_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(null, VALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, INVALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, null, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }


    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, INVALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, null,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_invalidRequiredDate_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, VALID_QUANTITY,
                INVALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = RequiredDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_nullRequiredDate_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, VALID_QUANTITY,
                null, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RequiredDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_invalidSupplier_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, INVALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Supplier.MESSAGE_SUPPLIER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_nullSupplier_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Supplier.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, INVALID_STATUS);
        String expectedMessage = PurchaseOrder.Status.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedPurchaseOrder po = new XmlAdaptedPurchaseOrder(VALID_SKU, VALID_NAME, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PurchaseOrder.Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, po::toModelType);
    }
}
