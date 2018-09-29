package seedu.address.model.medicine;

import java.util.ArrayList;
import java.util.Objects;

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

    public PricePerUnit getPricePerUnit() {
        return pricePerUnit;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public Stock getStock() {
        return stock;
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
                && otherMedicine.getMinimumStockQuantity().equals(getMedicineName())
                && otherMedicine.getPricePerUnit().equals(getPricePerUnit())
                || otherMedicine.getSerialNumber().equals(getSerialNumber());
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
                && otherMedicine.getSerialNumber().equals(getSerialNumber())
                && otherMedicine.getStock().equals(getStock());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(medicineName, minimumStockQuantity, pricePerUnit, serialNumber, stock);
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
