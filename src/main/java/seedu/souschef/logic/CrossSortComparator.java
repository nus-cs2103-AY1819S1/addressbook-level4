package seedu.souschef.logic;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * Comparator class for Recipe which compares Recipe objects by Number of optional ingredients in it.
 */
public class CrossSortComparator implements Comparator<CrossRecipe> {
    private Map<Recipe, List<IngredientDefinition>> matchedCrossRecipeMap;

    public CrossSortComparator(Map<Recipe, List<IngredientDefinition>> matchedCrossRecipeMap) {
        this.matchedCrossRecipeMap = matchedCrossRecipeMap;
    }

    /**
     * Compare two CrossRecipe by their number of matched ingredient.
     */
    public int compare(CrossRecipe a, CrossRecipe b) {
        return -(Integer.valueOf(matchedCrossRecipeMap.get(a.getRecipe()).size())
                .compareTo(Integer.valueOf(matchedCrossRecipeMap.get(b.getRecipe()).size())));
    }
}
