package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.clinicio.model.tag.Tag;

/**
 * Represents a Medicine in the ClinicIO
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Medicine {

    // Identity fields
    private final MedicineName name;
    private final MedicineType type;

    // Data fields
    private final MedicineDosage effectiveDosage;
    private final MedicineDosage lethalDosage;
    private final MedicinePrice price;
    private final Set<Tag> tags = new HashSet<>();

    private MedicineQuantity quantity;

    /**
     * Every field must be present and not null.
     */
    public Medicine(MedicineName name, MedicineType type, MedicineDosage effectiveDosage,
                    MedicineDosage lethalDosage, MedicinePrice price, MedicineQuantity quantity, Set<Tag> tags) {
        requireAllNonNull(name, type, effectiveDosage, lethalDosage, quantity);
        this.name = name;
        this.type = type;
        this.effectiveDosage = effectiveDosage;
        this.lethalDosage = lethalDosage;
        this.price = price;
        this.quantity = quantity;
        this.tags.addAll(tags);
    }

    public MedicineName getMedicineName() {
        return name;
    }

    public MedicineType getMedicineType() {
        return type;
    }

    public MedicineDosage getEffectiveDosage() {
        return effectiveDosage;
    }

    public MedicineDosage getLethalDosage() {
        return lethalDosage;
    }

    public MedicinePrice getPrice() {
        return price;
    }

    public MedicineQuantity getQuantity() {
        return quantity;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setQuantity(MedicineQuantity latestQuantity) {
        this.quantity = latestQuantity;
    }

    /**
     * Returns true if both medicines are the same.
     */
    public boolean isSameMedicine(Medicine other) {
        return this.equals(other);
    }

    /**
     * Returns true if both medicines have the same identity and data fields.
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
                && otherMedicine.getMedicineType().equals(getMedicineType())
                && otherMedicine.getEffectiveDosage().equals(getEffectiveDosage())
                && otherMedicine.getLethalDosage().equals(getLethalDosage())
                && otherMedicine.getPrice().equals(getPrice())
                && otherMedicine.getQuantity().equals(getQuantity())
                && otherMedicine.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, type, effectiveDosage, lethalDosage, price, quantity, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getMedicineName())
                .append(" Type: ")
                .append(getMedicineType())
                .append(" Effective Dosage: ")
                .append(getEffectiveDosage())
                .append(" Lethal Dosage: ")
                .append(getLethalDosage())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
