package seedu.address.model.volunteer;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Volunteer}'s {@code Name} matches any of the keywords given.
 */
public class VolunteerNameContainsKeywordsPredicate implements Predicate<Volunteer> {
    private final List<String> keywords;

    public VolunteerNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Volunteer volunteer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(volunteer.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VolunteerNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((VolunteerNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
