package seedu.souschef.model.ingredient;

import java.util.HashMap;

import seedu.souschef.model.UniqueType;

/**
 * ingredient class that stores ingredient name.
 */
public class IngredientDefinition extends UniqueType {
    private final IngredientName name;
    private final IngredientServingUnit unit;

    private final HashMap<String, String> dictionary = new HashMap<>();

    public IngredientDefinition(IngredientName name, IngredientServingUnit unit) {
        this.name = name;
        this.unit = unit;
    }

    // temp
    public IngredientDefinition(String name, String unit) {
        this.name = new IngredientName(name);
        this.unit = new IngredientServingUnit(unit);
    }

    public IngredientDefinition(String name) {
        dictionary.put("chicken", "gram");
        dictionary.put("onion", "piece");
        dictionary.put("egg", "piece");
        dictionary.put("garlic", "clove");
        dictionary.put("flour", "gram");
        dictionary.put("octopus", "gram");

        this.name = new IngredientName(name);
        if (dictionary.containsKey(name)) {
            this.unit = new IngredientServingUnit(dictionary.get(name));
        } else {
            this.unit = new IngredientServingUnit("none");
        }
    }

    public IngredientName getName() {
        return name;
    }

    public IngredientServingUnit getUnit() {
        return unit;
    }

    /**
     * Returns true if both ingredients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    @Override
    public boolean isSame(UniqueType other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientDefinition)) {
            return false;
        }

        IngredientDefinition otherIngredient = (IngredientDefinition) other;

        return otherIngredient != null
                && otherIngredient.getName().equals(getName());
    }

    /**
     * Basically same with isSame method above.
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientDefinition)) {
            return false;
        }

        IngredientDefinition otherIngredient = (IngredientDefinition) other;

        return otherIngredient != null
                && otherIngredient.getName().equals(getName())
                && otherIngredient.getUnit().equals(getUnit());
    }

    public String toString() {
        return getName().toString() + " " + getUnit().toString();
    }
}
