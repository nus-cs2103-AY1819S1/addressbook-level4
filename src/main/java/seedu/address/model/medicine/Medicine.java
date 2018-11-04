/* @@author 99percentile */
package seedu.address.model.medicine;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.medicine.exceptions.InsufficientStockException;

/**
 * Represents a Medicine in the records.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Medicine {
    private static ArrayList<Medicine> medicineLowStockList;
    private MedicineName medicineName;
    private MinimumStockQuantity minimumStockQuantity;
    private PricePerUnit pricePerUnit;
    private SerialNumber serialNumber;
    private Stock stock;

    public Medicine(MedicineName medicineName, MinimumStockQuantity minimumStockQuantity, PricePerUnit pricePerUnit,
                    SerialNumber serialNumber, Stock stock) {
        this.medicineName = medicineName;
        this.minimumStockQuantity = minimumStockQuantity;
        this.pricePerUnit = pricePerUnit;
        this.serialNumber = serialNumber;
        this.stock = stock;
    }

    public MedicineName getMedicineName() {
        return medicineName;
    }

    public MinimumStockQuantity getMinimumStockQuantity() {
        return minimumStockQuantity;
    }

    public Integer getMsqValue() {
        return minimumStockQuantity.getValue();
    }

    public PricePerUnit getPricePerUnit() {
        return pricePerUnit;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public Stock getStock() {
        return stock;
    }

    public Integer getStockValue() {
        return stock.getValue();
    }

    /**
     * After dispensing the medicine to the patient, the stock is updated accordingly
     * to the original stock level. There must be sufficient quantity of stock to dispense.
     * @param stockToDispense the amount of medicine to dispense
     */
    public void dispense(QuantityToDispense stockToDispense) throws InsufficientStockException {
        int newStockLevel = getStockValue() - stockToDispense.getValue();
        if (newStockLevel < 0) {
            throw new InsufficientStockException(this);
        }
        this.stock = new Stock(newStockLevel);
    }

    /**
     * In the event that the doctor changes the quantity of medicine allocated to the patient,
     * the original quantity of medicine allocated needs to be added back to the stock.
     */
    public void refill(int stockToRefill) {
        this.stock = new Stock(getStockValue() + stockToRefill);
    }

    /**
     * Returns true if both medicines of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two medicines.
     */
    public boolean isSameMedicine(Medicine otherMedicine) {
        if (otherMedicine == this) {
            return true;
        }
        return otherMedicine != null
                && otherMedicine.getMedicineName().equals(getMedicineName())
                && otherMedicine.getSerialNumber().equals(getSerialNumber())
                && (otherMedicine.getMinimumStockQuantity().equals(getMedicineName())
                || otherMedicine.getPricePerUnit().equals(getPricePerUnit())
                || otherMedicine.getStock().equals(getStock()));
    }

    /**
     * Returns true if both medicines have the same name.
     */
    public boolean hasSameMedicineName(Medicine otherMedicine) {
        return otherMedicine.getMedicineName().equals(getMedicineName());
    }

    /**
     * Returns true if both medicines have the same serial number.
     */
    public boolean hasSameSerialNumber(Medicine otherMedicine) {
        return otherMedicine.getSerialNumber().equals(getSerialNumber());
    }

    /**
     * Returns true if both medicines have the same identity and data fields.
     * This defines a stronger notion of equality between two medicines.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Medicine)) {
            return false;
        }

        Medicine otherMedicine = (Medicine) other;
        return otherMedicine.getMedicineName().equals(getMedicineName())
                && otherMedicine.getMinimumStockQuantity().equals(getMinimumStockQuantity())
                && otherMedicine.getPricePerUnit().equals(getPricePerUnit())
                && otherMedicine.getSerialNumber().equals(getSerialNumber());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(medicineName, minimumStockQuantity, pricePerUnit, serialNumber);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getMedicineName())
                .append(" Minimum Stock Quantity: ")
                .append(getMinimumStockQuantity())
                .append(" Price Per Unit: ")
                .append(getPricePerUnit())
                .append(" Serial Number: ")
                .append(getSerialNumber())
                .append(" Stock: ")
                .append(getStock());
        return builder.toString();
    }
}
