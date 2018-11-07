package seedu.souschef.model.ingredient;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Ingredient}'s {@code IngredientName} matches any of the keywords given.
 */
public class IngredientNameContainsKeywordsPredicate implements Predicate<Ingredient> {
    private final List<String> keywords;

    public IngredientNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Ingredient ingredient) {
        for (String keyword : keywords) {
            if (ingredient.getName().containsKeyword(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IngredientNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
