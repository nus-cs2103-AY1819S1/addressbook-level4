package seedu.address.model.ingredient;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an ingredient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient {
    // Identity fields
    private String name;

    // Data fields
    private double amount;
    private ServingUnit unit;
    private Date date;

    public Ingredient(UniqueIngredient uniqueIngredient, double amount, ServingUnit unit, Date date) {
        this.name = uniqueIngredient.getName();
        this.amount = amount;
        this.unit = unit;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public ServingUnit getUnit() {
        return unit;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns true if both ingredients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSameIngredient(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && otherIngredient.getName().equals(getName())
                && (otherIngredient.getAmount() == getAmount()) || otherIngredient.getUnit().equals(getUnit());
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
                && otherIngredient.getAmount() == getAmount()
                && otherIngredient.getUnit().equals(getUnit())
                && otherIngredient.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, unit, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Unit: ")
                .append(getUnit())
                .append(" Date: ")
                .append(getDate());
        return builder.toString();
    }
}
