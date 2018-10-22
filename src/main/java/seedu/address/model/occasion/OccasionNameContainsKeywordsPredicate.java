package seedu.address.model.occasion;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that an {@code Occasion}'s {@code Name} matches any of the keywords given.
 */
public class OccasionNameContainsKeywordsPredicate implements Predicate<Occasion> {
    private final List<String> keywords;

    public OccasionNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Occasion occasion) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        occasion.getOccasionName().fullOccasionName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OccasionNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OccasionNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
