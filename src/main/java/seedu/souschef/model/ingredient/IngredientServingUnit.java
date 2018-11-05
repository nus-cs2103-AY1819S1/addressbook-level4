package seedu.souschef.model.ingredient;

import static seedu.souschef.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Map;

/**
 * class to store ingredient serving unit.
 */
public class IngredientServingUnit {
    public static final Map<String, IngredientServingUnitDefinition> DICTIONARY = new HashMap<>();
    public static final String MESSAGE_UNIT_CONSTRAINTS = "Invalid Ingredient Unit.";

    private final String unit;

    static {
        DICTIONARY.put("gram", new IngredientServingUnitDefinition("gram", "gram",
                1.0));
        DICTIONARY.put("g", new IngredientServingUnitDefinition("g", "gram",
                1.0));
        DICTIONARY.put("kg", new IngredientServingUnitDefinition("kg", "gram",
                1000.0));
        DICTIONARY.put("kilogram", new IngredientServingUnitDefinition("kilogram", "gram",
                1000.0));
        DICTIONARY.put("pinch", new IngredientServingUnitDefinition("pinch", "gram",
                0.4));
        DICTIONARY.put("piece", new IngredientServingUnitDefinition("piece", "gram",
                100.0));
        DICTIONARY.put("whole", new IngredientServingUnitDefinition("whole", "gram",
                100.0));
        DICTIONARY.put("clove", new IngredientServingUnitDefinition("clove", "gram",
                10.0));

        DICTIONARY.put("cm3", new IngredientServingUnitDefinition("cm3", "cm3",
                1.0));
        DICTIONARY.put("ml", new IngredientServingUnitDefinition("ml", "cm3",
                1.0));
        DICTIONARY.put("l", new IngredientServingUnitDefinition("l", "cm3",
                1000.0));

        DICTIONARY.put("tablespoon", new IngredientServingUnitDefinition("tablespoon", "cm3",
                14.8));
        DICTIONARY.put("teaspoon", new IngredientServingUnitDefinition("teaspoon", "cm3",
                4.9));
        DICTIONARY.put("cup", new IngredientServingUnitDefinition("cup", "cm3",
                240.0));
    }

    public IngredientServingUnit(String unit) {
        checkArgument(isValid(unit), MESSAGE_UNIT_CONSTRAINTS);
        this.unit = unit.toLowerCase();
    }

    public String getUnit() {
        return unit;
    }

    /**
     * checks if the serving unit is valid.
     */
    public static boolean isValid(String unit) {
        return DICTIONARY.containsKey(unit.toLowerCase());
    }

    /**
     * Display all units in a formatted string.
     */
    public static String allUnits() {
        StringBuilder units = new StringBuilder();
        DICTIONARY.forEach((k, v) -> units.append(k + ", "));
        units.deleteCharAt(units.length() - 1);
        units.deleteCharAt(units.length() - 1);
        return units.append(".").toString();
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

    @Override
    public String toString() {
        return unit;
    }
}
