package seedu.souschef.model.recipe;

import java.util.List;
import java.util.function.Predicate;

import seedu.souschef.commons.util.StringUtil;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.ingredient.IngredientDefinition;

/**
 * Tests that a {@code Favourite}'s {@code Name} matches any of the keywords given.
 */
public class FavouriteNameContainsKeywordsPredicate implements Predicate<Favourites> {
    private final List<String> keywords;

    public FavouriteNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Favourites favourites) {
        if (keywords.size() == 0) {
            return false;
        }
        return (keywords.stream()
                .allMatch(keyword -> {
                    return StringUtil.containsWordIgnoreCase(favourites.getName().fullName, keyword)
                            || favourites.getCookTime().toString().toLowerCase().equalsIgnoreCase(keyword.toLowerCase())
                            || favourites.getDifficulty().toString().equals(keyword)
                            || favourites.getTags().stream()
                            .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))
                            || favourites.getIngredients().containsKey(new IngredientDefinition(keyword));
                }
                ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FavouriteNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
