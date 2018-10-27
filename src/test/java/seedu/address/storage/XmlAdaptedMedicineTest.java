package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedMedicine.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalMedicines.BACITRACIN;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Stock;
import seedu.address.testutil.Assert;

public class XmlAdaptedMedicineTest {

    private static final String INVALID_MEDICINE_NAME = "p@n@d0l";
    private static final Integer INVALID_MIN_STOCK_QUANTITY = -9000;
    private static final String INVALID_PRICE_PER_UNIT = "free";
    private static final String INVALID_SERIAL_NUMBER = "this is a serial number";
    private static final Integer INVALID_STOCK = -9000;

    private static final String VALID_MEDICINE_NAME = BACITRACIN.getMedicineName().fullName;
    private static final Integer VALID_MIN_STOCK_QUANTITY = BACITRACIN.getMinimumStockQuantity().value;
    private static final String VALID_PRICE_PER_UNIT = BACITRACIN.getPricePerUnit().value;
    private static final String VALID_SERIAL_NUMBER = BACITRACIN.getSerialNumber().value;
    private static final Integer VALID_STOCK = BACITRACIN.getStock().value;

    @Test
    public void toModelType_validMedicineDetails_returnsMedicine() throws Exception {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(BACITRACIN);
        assertEquals(BACITRACIN, medicine.toModelType());
    }

    @Test
    public void toModelType_invalidMedicineName_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(INVALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullMedicineName_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(null, VALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidMinStockQty_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, INVALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = MinimumStockQuantity.MESSAGE_MINIMUM_STOCK_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullMinStockQty_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, null,
                VALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                MinimumStockQuantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidPricePerUnit_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                INVALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = PricePerUnit.MESSAGE_PRICE_PER_UNIT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullPricePerUnit_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                null, VALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PricePerUnit.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidSerialNumber_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, INVALID_SERIAL_NUMBER, VALID_STOCK);
        String expectedMessage = SerialNumber.MESSAGE_SERIAL_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullSerialNumber_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, null, VALID_STOCK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SerialNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidStock_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, INVALID_STOCK);
        String expectedMessage = Stock.MESSAGE_STOCK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullStock_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MIN_STOCK_QUANTITY,
                VALID_PRICE_PER_UNIT, VALID_SERIAL_NUMBER, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Stock.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

}
