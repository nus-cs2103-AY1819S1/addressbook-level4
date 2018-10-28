package seedu.souschef.logic;

import java.util.Comparator;
import java.util.Map;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * Comparator class for Recipe which compares Recipe objects by Number of optional ingredients in it.
 */
public class CrossSortComparator implements Comparator<CrossRecipe> {
    private Map<Recipe, Map<IngredientDefinition, Double>> map;

    public CrossSortComparator(Map<Recipe, Map<IngredientDefinition, Double>> map) {
        this.map = map;
    }

    /**
     * Compare two CrossRecipe by their number of matched ingredient.
     */
    public int compare(CrossRecipe a, CrossRecipe b) {
        return -(Integer.valueOf(map.get(a.getRecipe()).size())
                .compareTo(Integer.valueOf(map.get(b.getRecipe()).size())));
    }
}
