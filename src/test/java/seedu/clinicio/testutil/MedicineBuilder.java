package seedu.clinicio.testutil;

//@@author aaronseahyh

import java.util.HashSet;
import java.util.Set;

import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineDosage;
import seedu.clinicio.model.medicine.MedicineName;
import seedu.clinicio.model.medicine.MedicinePrice;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.MedicineType;
import seedu.clinicio.model.tag.Tag;
import seedu.clinicio.model.util.SampleDataUtil;

/**
 * A utility class to help with building Medicine objects.
 */
public class MedicineBuilder {

    public static final String DEFAULT_MEDICINE_NAME = "Paracetamol";
    public static final String DEFAULT_MEDICINE_TYPE = "Tablet";
    public static final String DEFAULT_EFFECTIVE_DOSAGE = "2";
    public static final String DEFAULT_LETHAL_DOSAGE = "8";
    public static final String DEFAULT_PRICE = "0.05";
    public static final String DEFAULT_QUANTITY = "1000";

    private MedicineName medicineName;
    private MedicineType type;
    private MedicineDosage effectiveDosage;
    private MedicineDosage lethalDosage;
    private MedicinePrice price;
    private MedicineQuantity quantity;
    private Set<Tag> tags;

    public MedicineBuilder() {
        medicineName = new MedicineName(DEFAULT_MEDICINE_NAME);
        type = new MedicineType(DEFAULT_MEDICINE_TYPE);
        effectiveDosage = new MedicineDosage(DEFAULT_EFFECTIVE_DOSAGE);
        lethalDosage = new MedicineDosage(DEFAULT_LETHAL_DOSAGE);
        price = new MedicinePrice(DEFAULT_PRICE);
        quantity = new MedicineQuantity(DEFAULT_QUANTITY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MedicineBuilder with the data of {@code medicineToCopy}.
     */
    public MedicineBuilder(Medicine medicineToCopy) {
        medicineName = medicineToCopy.getMedicineName();
        type = medicineToCopy.getMedicineType();
        effectiveDosage = medicineToCopy.getEffectiveDosage();
        lethalDosage = medicineToCopy.getLethalDosage();
        price = medicineToCopy.getPrice();
        quantity = medicineToCopy.getQuantity();
        tags = new HashSet<>(medicineToCopy.getTags());
    }

    /**
     * Sets the {@code MedicineName} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withMedicineName(String medicineName) {
        this.medicineName = new MedicineName(medicineName);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Medicine} that we are building.
     */
    public MedicineBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code MedicineType} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withMedicineType(String medicineType) {
        this.type = new MedicineType(medicineType);
        return this;
    }

    /**
     * Sets the {@code EffectiveDosage} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withEffectiveDosage(String effectiveDosage) {
        this.effectiveDosage = new MedicineDosage(effectiveDosage);
        return this;
    }

    /**
     * Sets the {@code LethalDosage} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withLethalDosage(String lethalDosage) {
        this.lethalDosage = new MedicineDosage(lethalDosage);
        return this;
    }

    /**
     * Sets the {@code MedicineName} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withMedicinePrice(String medicinePrice) {
        this.price = new MedicinePrice(medicinePrice);
        return this;
    }

    /**
     * Sets the {@code MedicineName} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withMedicineQuantity(String medicineQuantity) {
        this.quantity = new MedicineQuantity(medicineQuantity);
        return this;
    }

    /**
     * Contructs a Medicine based on the MedicineBuilder.
     * @return a medicine.
     */
    public Medicine build() {
        Medicine medicine = new Medicine(medicineName, type, effectiveDosage, lethalDosage, price, quantity, tags);
        return medicine;
    }
}
