package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's medical problem in the ClinicIO.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMedProb(String)}
 */
public class MedicalProblem {
    public static final String MESSAGE_MED_PROB_CONSTRAINTS = "Medical problems should be alphanumeric";
    public static final String MED_PROB_VALIDATION_REGEX = "[\\p{Alnum} ]*";

    public final String medProb;

    /**
     * Constructs a {@code MedicalProblem}.
     *
     * @param medProb A valid medical problem.
     */
    public MedicalProblem(String medProb) {
        requireNonNull(medProb);
        checkArgument(isValidMedProb(medProb), MESSAGE_MED_PROB_CONSTRAINTS);
        this.medProb = medProb;
    }

    /**
     * Returns true if a given string is a valid medical problem.
     */
    public static boolean isValidMedProb(String test) {
        return test.matches(MED_PROB_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalProblem // instanceof handles nulls
                && medProb.equals(((MedicalProblem) other).medProb)); // state check
    }

    @Override
    public int hashCode() {
        return medProb.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + medProb + ']';
    }

}
