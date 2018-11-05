package seedu.address.model.appointment;

import java.util.Objects;

/**
 * Represents an Prescription in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Prescription {
    private int id;
    private MedicineName medicineName;
    private Dosage dosage;
    private ConsumptionPerDay consumptionPerDay;

    public Prescription(int appointmentId, MedicineName medicineName, Dosage dosage,
                        ConsumptionPerDay consumptionPerDay) {
        this.id = appointmentId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.consumptionPerDay = consumptionPerDay;
    }

    // Get Methods
    public int getId() {
        return id;
    }

    public MedicineName getMedicineName() {
        return medicineName;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public ConsumptionPerDay getConsumptionPerDay() {
        return consumptionPerDay;
    }

    /**
     * Returns true if both prescription have the same medicineName
     * This defines a weaker notion of equality between two prescriptions
     */
    public boolean isSamePrescription(Prescription other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getMedicineName().equals(getMedicineName());
    }

    /**
     * Returns true if both prescriptions have the same identity and data fields.
     * This defines a stronger notion of equality between two prescriptions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Prescription)) {
            return false;
        }

        /**
         * Equality of prescription only determined by name
         */
        Prescription otherPrescription = (Prescription) other;
        return otherPrescription.getMedicineName().equals(getMedicineName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, medicineName, dosage, consumptionPerDay);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Medicine Name: ")
                .append(getMedicineName())
                .append(" Dosage: ")
                .append(getDosage())
                .append(" Consumption Per Day: ")
                .append(getConsumptionPerDay());
        return builder.toString();
    }
}
