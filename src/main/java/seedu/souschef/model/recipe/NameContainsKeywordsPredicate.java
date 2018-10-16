package seedu.souschef.model.recipe;

import java.util.List;
import java.util.function.Predicate;

import seedu.souschef.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Recipe> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(recipe.getName().fullName, keyword)
                        || recipe.getCookTime().toString().toLowerCase().equalsIgnoreCase(keyword.toLowerCase())
                        || recipe.getDifficulty().toString().equals(keyword)
                        || recipe.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))
                ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
