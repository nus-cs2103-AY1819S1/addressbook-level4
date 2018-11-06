package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's medication in the ClinicIO.
 * Guarantees: immutable; is valid as declared in {@link #isValidMed(String)}
 */
public class Medication {
    public static final String MESSAGE_MED_CONSTRAINTS = "Medications should be alphanumeric";
    public static final String MED_VALIDATION_REGEX = "[\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code MedicalProblem}.
     *
     * @param medProb A valid medications.
     */
    public Medication(String medProb) {
        requireNonNull(medProb);
        checkArgument(isValidMed(medProb), MESSAGE_MED_CONSTRAINTS);
        value = medProb;
    }

    /**
     * Returns true if a given string is a valid medications.
     */
    public static boolean isValidMed(String test) {
        return test.matches(MED_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Medication // instanceof handles nulls
                && value.equals(((Medication) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + value + ']';
    }

}
