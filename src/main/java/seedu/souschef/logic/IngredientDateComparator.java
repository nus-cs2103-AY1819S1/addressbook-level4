package seedu.souschef.logic;

import java.util.Comparator;

import seedu.souschef.model.ingredient.IngredientDate;

/**
 * Comparator class for Day which compares Day objects by LocalDate date.
 */
public class IngredientDateComparator implements Comparator<IngredientDate> {
    @Override
    public int compare(IngredientDate a, IngredientDate b) {
        return a.getDate().compareTo(b.getDate());
    }
}
