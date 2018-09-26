package seedu.address.model.doctor;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

//@@author jjlee050
/**
 * Tests that a {@code Doctor}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Doctor> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Doctor doctor) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(doctor.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
