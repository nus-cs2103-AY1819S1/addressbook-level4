package seedu.souschef.model.ingredient;

import static seedu.souschef.commons.util.AppUtil.checkArgument;

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


        checkArgument(isValid(unit), MESSAGE_UNIT_CONSTRAINTS);
        this.unit = unit.toLowerCase();
    }

    /**
     * checks if the serving unit is valid.
     */
    public boolean isValid() {
        return dictionary.contains(unit);
    }

    public boolean isValid(String unit) {
        return dictionary.contains(unit);
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
