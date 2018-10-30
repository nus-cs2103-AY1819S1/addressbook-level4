package seedu.souschef.model.ingredient;

import seedu.souschef.model.UniqueType;

/**
 * ingredient class that stores ingredient name.
 */
public class IngredientDefinition extends UniqueType {
    private final IngredientName name;

    public IngredientDefinition(IngredientName name) {
        this.name = name;
    }

    public IngredientDefinition(String name) {
        this.name = new IngredientName(name);
    }

    public IngredientName getName() {
        return name;
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
                && otherIngredient.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return getName().toString();
    }
}
