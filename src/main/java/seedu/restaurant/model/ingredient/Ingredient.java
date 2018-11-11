package seedu.restaurant.model.ingredient;

import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an ingredient in the restaurant management app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient {

    // Identity fields
    private final IngredientName name;

    //Data fields
    private final IngredientUnit unit;
    private final IngredientPrice price;
    private final MinimumUnit minimum;
    private final NumUnits numUnits;

    /**
     * Every field must be present and not null.
     */
    public Ingredient(IngredientName name, IngredientUnit unit, IngredientPrice price, MinimumUnit minimum,
                      NumUnits numUnits) {
        requireAllNonNull(name);
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.minimum = minimum;
        this.numUnits = numUnits;
    }

    public IngredientName getName() {
        return name;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    public IngredientPrice getPrice() {
        return price;
    }

    public MinimumUnit getMinimum() {
        return minimum;
    }

    public NumUnits getNumUnits() {
        return numUnits;
    }


    /**
     * Returns true if both ingredients have the same name.
     * This defines a weaker notion of equality between two ingredients.
     */
    public boolean isSameIngredient(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && otherIngredient.getName().equalsIgnoreCase(getName());
    }

    /**
     * Returns true if both ingredients have the same identity and data fields.
     * This defines a stronger notion of equality between two ingredients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Ingredient)) {
            return false;
        }

        Ingredient otherIngredient = (Ingredient) other;
        return otherIngredient.getName().equals(getName())
                && otherIngredient.getUnit().equals(getUnit())
                && otherIngredient.getPrice().equals(getPrice())
                && otherIngredient.getMinimum().equals(getMinimum())
                && otherIngredient.getNumUnits().equals(getNumUnits());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, unit, price, minimum, numUnits);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Ingredient Name: ");
        builder.append(getName())
                .append(" Unit: ")
                .append(getUnit())
                .append(" Price: ")
                .append(getPrice())
                .append(" Minimum: ")
                .append(getMinimum())
                .append(" Number of Units: ")
                .append(getNumUnits());
        return builder.toString();
    }
}
