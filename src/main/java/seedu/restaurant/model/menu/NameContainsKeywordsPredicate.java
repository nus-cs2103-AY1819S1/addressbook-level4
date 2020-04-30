package seedu.restaurant.model.menu;

import java.util.List;
import java.util.function.Predicate;

import seedu.restaurant.commons.util.StringUtil;

//@@author yican95
/**
 * Tests that a {@code Item}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(item.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
