package seedu.souschef.model.recipe;

import java.util.List;
import java.util.function.Predicate;

import seedu.souschef.commons.util.StringUtil;
import seedu.souschef.model.ingredient.IngredientDefinition;

/**
 * Tests that a {@code Recipe}'s {@code Name} matches any of the keywords given.
 */
public class RecipeContainsKeywordsPredicate implements Predicate<Recipe> {
    private final List<String> keywords;

    public RecipeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        if (keywords.size() == 0) {
            return false;
        }
        return (keywords.stream()
                .allMatch(keyword -> {
                    return StringUtil.containsWordIgnoreCase(recipe.getName().fullName, keyword)
                            || recipe.getCookTime().toString().toLowerCase().equalsIgnoreCase(keyword.toLowerCase())
                            || recipe.getDifficulty().toString().equals(keyword)
                            || recipe.getTags().stream()
                            .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))
                            || recipe.getIngredients().containsKey(new IngredientDefinition(keyword));
                }
                ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RecipeContainsKeywordsPredicate) other).keywords)); // state check
    }

}
