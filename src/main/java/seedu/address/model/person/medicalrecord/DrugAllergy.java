package seedu.address.model.person.medicalrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class DrugAllergy {

    public static final String MESSAGE_DRUGALLERGY_CONSTRAINTS =
            "Drug allergies should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String DRUGALLERGY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    /**
     * Constructs a {@code DrugAllergy}.
     *
     * @param drugAllergy A valid drug allergy
     */
    public DrugAllergy(String drugAllergy) {
        requireNonNull(drugAllergy);
        checkArgument(isValidDrugAllergy(drugAllergy), MESSAGE_DRUGALLERGY_CONSTRAINTS);
        value = drugAllergy;
    }

    /**
     * Returns true if a given string is a valid drug allergy.
     */
    public static boolean isValidDrugAllergy(String test) {
        return test.matches(DRUGALLERGY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DrugAllergy // instanceof handles nulls
                && value.equals(((DrugAllergy) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
