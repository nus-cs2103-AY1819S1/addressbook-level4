package seedu.souschef.model.ingredient;

import java.util.ArrayList;

/**
 * class to store ingredient serving unit.
 */
public class IngredientServingUnit {
    public static final String MESSAGE_UNIT_CONSTRAINTS = "Invalid Ingredient Unit!";

    private final ArrayList<String> dictionary;
    private final String unit;

    public IngredientServingUnit(String unit) {
        dictionary = new ArrayList<String>();
        dictionary.add("gram");
        dictionary.add("piece");
        this.unit = unit;
    }

    /**
     * checks if the serving unit is valid.
     */
    public boolean isValid() {
        if (dictionary.contains(unit)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return unit;
    }
}
