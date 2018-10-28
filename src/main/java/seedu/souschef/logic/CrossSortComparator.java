package seedu.souschef.logic;

import java.util.Comparator;
import java.util.Map;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.CrossRecipe;

/**
 * Comparator class for Recipe which compares Recipe objects by Number of optional ingredients in it.
 */
public class CrossSortComparator implements Comparator<CrossRecipe> {
    private Map<Recipe, Map<IngredientDefinition, Double>> map;

    public CrossSortComparator(Map<Recipe, Map<IngredientDefinition, Double>> map) {
        this.map = map;
    }

    public int compare(CrossRecipe a, CrossRecipe b) {
        return -(Integer.valueOf(map.get(a.getRecipe()).size())
                .compareTo(Integer.valueOf(map.get(b.getRecipe()).size())));
    }
}
