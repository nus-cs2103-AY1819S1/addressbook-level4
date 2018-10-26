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
        dictionary = new ArrayList<>();
        dictionary.add("gram");
        dictionary.add("piece");
        dictionary.add("cup");
        dictionary.add("pinch");
        dictionary.add("clove");
        dictionary.add("tablespoon");
        dictionary.add("teaspoon");
        dictionary.add("ml");
        dictionary.add("none");

        this.unit = unit.toLowerCase();
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientServingUnit)) {
            return false;
        }

        IngredientServingUnit otherUnit = (IngredientServingUnit) other;

        return otherUnit != null
                && this.unit.equals(otherUnit.unit);
    }

    public String toString() {
        return unit;
    }
}
