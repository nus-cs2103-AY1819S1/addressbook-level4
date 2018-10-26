package seedu.souschef.logic;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;

/**
 * Comparator class for Recipe which compares Recipe objects by Number of optional ingredients in it.
 */
public class InventoryComparator implements Comparator<Recipe> {
    private Map<Recipe, HashMap<IngredientDefinition, Double>> map;

    public InventoryComparator(Map<Recipe, HashMap<IngredientDefinition, Double>> map) {
        this.map = map;
    }

    public int compare(Recipe a, Recipe b) {
        return -(Integer.valueOf(map.get(a).size()).compareTo(Integer.valueOf(map.get(b).size())));
    }
}
