package seedu.address.testutil;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Stock;

/**
 * A utility class to help with building Medicine objects.
 */
public class MedicineBuilder {

    public static final String DEFAULT_MEDICINE_NAME = "PANADOL";
    public static final String DEFAULT_MINIMUM_STOCK_QUANTITY = "100";
    public static final String DEFAULT_PRICE_PER_UNIT = "2";
    public static final String DEFAULT_SERIAL_NUMBER = "000129374";
    public static final String DEFAULT_STOCK = "1834";

    private MedicineName medicineName;
    private MinimumStockQuantity minimumStockQuantity;
    private PricePerUnit pricePerUnit;
    private SerialNumber serialNumber;
    private Stock stock;

    public MedicineBuilder() {
        medicineName = new MedicineName(DEFAULT_MEDICINE_NAME);
        minimumStockQuantity = new MinimumStockQuantity(DEFAULT_MINIMUM_STOCK_QUANTITY);
        pricePerUnit = new PricePerUnit(DEFAULT_PRICE_PER_UNIT);
        serialNumber = new SerialNumber(DEFAULT_SERIAL_NUMBER);
        stock = new Stock(DEFAULT_STOCK);
    }

    /**
     * Initializes the MedicineBuilder with the data of {@code medicineToCopy}.
     */
    public MedicineBuilder(Medicine medicineToCopy) {
        medicineName = medicineToCopy.getMedicineName();
        minimumStockQuantity = medicineToCopy.getMinimumStockQuantity();
        pricePerUnit = medicineToCopy.getPricePerUnit();
        serialNumber = medicineToCopy.getSerialNumber();
        stock = medicineToCopy.getStock();
    }

    /**
     * Sets the {@code MedicineName} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withMedicineName(String medicineName) {
        this.medicineName = new MedicineName(medicineName);
        return this;
    }

    /**
     * Sets the {@code MinimumStockQuantity} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withMinimumStockQuantity(String minimumStockQuantity) {
        this.minimumStockQuantity = new MinimumStockQuantity(minimumStockQuantity);
        return this;
    }

    /**
     * Sets the {@code Stock} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withStock(String stock) {
        this.stock = new Stock(stock);
        return this;
    }

    /**
     * Sets the {@code PricePerUnit} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = new PricePerUnit(pricePerUnit);
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = new SerialNumber(serialNumber);
        return this;
    }

    public Medicine build() {
        return new Medicine(medicineName, minimumStockQuantity, pricePerUnit, serialNumber, stock);
    }

}
