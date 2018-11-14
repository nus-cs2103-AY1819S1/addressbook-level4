package seedu.address.model.occasion;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that an {@code Occasion}'s {@code OccasionDate} matches any of the keywords given.
 */
public class OccasionDateContainsKeywordsPredicate implements Predicate<Occasion> {
    private final List<String> keywords;

    public OccasionDateContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Occasion occasion) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        occasion.getOccasionDate().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OccasionDateContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OccasionDateContainsKeywordsPredicate) other).keywords)); // state check
    }
}
