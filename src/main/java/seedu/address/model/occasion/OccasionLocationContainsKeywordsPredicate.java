package seedu.address.model.occasion;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that an {@code Occasion}'s {@code OccasionLocation} matches any of the keywords given.
 */
public class OccasionLocationContainsKeywordsPredicate implements Predicate<Occasion> {
    private final List<String> keywords;

    public OccasionLocationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Occasion occasion) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        occasion.getOccasionLocation().fullOccasionLocation, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OccasionLocationContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OccasionLocationContainsKeywordsPredicate) other).keywords)); // state check
    }
}
