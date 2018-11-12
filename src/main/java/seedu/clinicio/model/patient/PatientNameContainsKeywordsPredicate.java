package seedu.clinicio.model.patient;

import java.util.List;
import java.util.function.Predicate;

import seedu.clinicio.commons.util.StringUtil;

/**
 * Tests that a {@code Patient}'s {@code Name} matches any of the keywords given.
 */
public class PatientNameContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public PatientNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient patient) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientNameContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((PatientNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
