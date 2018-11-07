package seedu.clinicio.testutil;

//@@author aaronseahyh

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinicio.model.MedicineInventory;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.person.Person;

/**
 * A utility class containing a list of {@code Medicine} objects to be used in tests.
 */
public class TypicalMedicines {

    public static final Medicine PARACETAMOL = new MedicineBuilder().withMedicineName("Paracetamol")
            .withMedicineType("Tablet").withEffectiveDosage("2").withLethalDosage("8")
            .withMedicinePrice("0.05").withMedicineQuantity("1000")
            .withTags("take one or two tablets at a time", "every six hours").build();
    public static final Medicine CHLORPHENIRAMINE = new MedicineBuilder().withMedicineName("Chlorpheniramine")
            .withMedicineType("Liquid").withEffectiveDosage("4").withLethalDosage("32")
            .withMedicinePrice("18.90").withMedicineQuantity("500")
            .withTags("take 4mg orally every four to six hours").build();
    public static final Medicine ORACORT = new MedicineBuilder().withMedicineName("Oracort")
            .withMedicineType("Topical").withEffectiveDosage("5").withLethalDosage("30")
            .withMedicinePrice("8.90").withMedicineQuantity("100")
            .withTags("apply 5mm on ulcer area", "two or three times a day").build();
    public static final Medicine VENTOLIN = new MedicineBuilder().withMedicineName("Ventolin")
            .withMedicineType("Inhaler").withEffectiveDosage("2").withLethalDosage("10")
            .withMedicinePrice("13.55").withMedicineQuantity("200")
            .withTags("take two puffs four times a day").build();

    // Manually added - Medicine's details found in {@code CommandTestUtil}
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_PARACETAMOL = "Paracetamol"; // A keyword that matches PARACETAMOL

    private TypicalMedicines() {
    } // prevents instantiation

    /**
     * Returns an {@code ClinicIo} with all the typical persons.
     */
    public static MedicineInventory getTypicalMedicineInventory() {
        MedicineInventory medicineInventory = new MedicineInventory();
        for (Medicine medicine : getTypicalMedicines()) {
            medicineInventory.addMedicine(medicine);
        }
        return medicineInventory;
    }

    public static List<Medicine> getTypicalMedicines() {
        return new ArrayList<>(Arrays.asList(PARACETAMOL, CHLORPHENIRAMINE, ORACORT, VENTOLIN));
    }

}
