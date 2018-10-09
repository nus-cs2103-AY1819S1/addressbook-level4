package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Stock;

/**
 * JAXB-friendly version of the Medicine.
 */
public class XmlAdaptedMedicine {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medicine's %s field is missing!";

    @XmlElement(required = true)
    private String medicineName;
    @XmlElement(required = true)
    private String minimumStockQuantity;
    @XmlElement(required = true)
    private String pricePerUnit;
    @XmlElement(required = true)
    private String serialNumber;
    @XmlElement(required = true)
    private String stock;

    /**
     * Constructs an XmlAdaptedMedicine.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedicine() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given patient details.
     */
    public XmlAdaptedMedicine(String medicineName, String minimumStockQuantity, String pricePerUnit,
                              String serialNumber, String stock) {
        this.medicineName = medicineName;
        this.minimumStockQuantity = minimumStockQuantity;
        this.pricePerUnit = pricePerUnit;
        this.serialNumber = serialNumber;
        this.stock = stock;
    }

    /**
     * Converts a given Medicine into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedMedicine(Medicine source) {
        medicineName = source.getMedicineName().fullName;
        minimumStockQuantity = source.getMinimumStockQuantity().value;
        pricePerUnit = source.getPricePerUnit().value;
        serialNumber = source.getSerialNumber().value;
        stock = source.getStock().value;
    }

    /**
     * Converts this jaxb-friendly adapted medicine object into the model's Medicine object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medicine
     */
    public Medicine toModelType() throws IllegalValueException {

        if (medicineName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicineName.class.getSimpleName()));
        }
        if (!MedicineName.isValidMedicineName(medicineName)) {
            throw new IllegalValueException(MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        }
        final MedicineName modelMedicineName = new MedicineName(medicineName);

        if (minimumStockQuantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MinimumStockQuantity.class.getSimpleName()));
        }
        if (!MinimumStockQuantity.isValidMinimumStockQuantity(minimumStockQuantity)) {
            throw new IllegalValueException(MinimumStockQuantity.MESSAGE_MINIMUM_STOCK_QUANTITY_CONSTRAINTS);
        }
        final MinimumStockQuantity modelMinimumStockQuantity = new MinimumStockQuantity(minimumStockQuantity);

        if (pricePerUnit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PricePerUnit.class.getSimpleName()));
        }
        if (!PricePerUnit.isValidPricePerUnit(pricePerUnit)) {
            throw new IllegalValueException(PricePerUnit.MESSAGE_PRICE_PER_UNIT_CONSTRAINTS);
        }
        final PricePerUnit modelPricePerUnit = new PricePerUnit(pricePerUnit);

        if (serialNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SerialNumber.class.getSimpleName()));
        }
        if (!SerialNumber.isValidSerialNumber(serialNumber)) {
            throw new IllegalValueException(SerialNumber.MESSAGE_SERIAL_NUMBER_CONSTRAINTS);
        }
        final SerialNumber modelSerialNumber = new SerialNumber(serialNumber);

        if (stock == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Stock.class.getSimpleName()));
        }
        if (!Stock.isValidStock(stock)) {
            throw new IllegalValueException(Stock.MESSAGE_STOCK_CONSTRAINTS);
        }
        final Stock modelStock = new Stock(stock);

        return new Medicine(modelMedicineName, modelMinimumStockQuantity, modelPricePerUnit, modelSerialNumber,
                modelStock);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedicine)) {
            return false;
        }

        XmlAdaptedMedicine otherMedicine = (XmlAdaptedMedicine) other;
        return Objects.equals(medicineName, otherMedicine.medicineName)
                && Objects.equals(minimumStockQuantity, otherMedicine.minimumStockQuantity)
                && Objects.equals(pricePerUnit, otherMedicine.pricePerUnit)
                && Objects.equals(serialNumber, otherMedicine.serialNumber)
                && Objects.equals(stock, otherMedicine.stock);
    }
}
