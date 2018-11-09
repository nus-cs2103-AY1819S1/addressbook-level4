package seedu.restaurant.model.sales;

import java.util.List;
import java.util.function.Predicate;

import seedu.restaurant.commons.util.StringUtil;

/**
 * Tests that a {@code SalesRecord}'s {@code ItemName} matches any of the keywords given.
 */
public class ItemNameContainsKeywordsPredicate implements Predicate<SalesRecord> {
    private final List<String> keywords;

    public ItemNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(SalesRecord salesRecord) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(salesRecord.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemNameContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((ItemNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
