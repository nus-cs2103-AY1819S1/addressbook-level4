package seedu.souschef.logic;

import java.util.Comparator;

import seedu.souschef.model.ingredient.Ingredient;

/**
 * Comparator class for Day which compares Day objects by LocalDate date.
 */
public class IngredientDateComparator implements Comparator<Ingredient> {
    @Override
    public int compare(Ingredient a, Ingredient b) {
        return a.getDate().getValue().compareTo(b.getDate().getValue());
    }
}
