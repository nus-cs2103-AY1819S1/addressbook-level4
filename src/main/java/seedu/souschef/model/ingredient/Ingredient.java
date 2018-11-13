package seedu.souschef.model.ingredient;

import java.util.Date;
import java.util.Objects;

import seedu.souschef.model.UniqueType;

/**
 * Represents an ingredient in SousChef.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient extends IngredientPortion {
    private final IngredientDate date;

    public Ingredient(IngredientName name, IngredientAmount amount, IngredientServingUnit unit, IngredientDate date) {
        super(name, unit, amount);
        this.date = date;
    }

    public Ingredient(String name, String unit, Double amount, Date date) {
        super(name, unit, amount);
        this.date = new IngredientDate(date);
    }

    public IngredientDate getDate() {
        return date;
    }

    @Override
    public Ingredient convertToCommonUnit() {
        IngredientServingUnitDefinition definition = IngredientServingUnit.DICTIONARY.get(this.getUnit().toString());
        IngredientName ingredientName = getName();
        IngredientServingUnit ingredientUnit = new IngredientServingUnit(definition.getCommonUnit());
        IngredientAmount ingredientAmount = new IngredientAmount(getAmount().getValue().doubleValue()
                * definition.getConversionValue().doubleValue());
        IngredientDate ingredientDate = date;

        return new Ingredient(ingredientName, ingredientAmount, ingredientUnit, ingredientDate);
    }

    /**
     * Returns true if both ingredients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSame(UniqueType other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Ingredient)) {
            return false;
        }

        Ingredient otherIngredient = (Ingredient) other;

        return otherIngredient != null
                && otherIngredient.getName().equals(getName())
                && otherIngredient.getUnit().equals(getUnit())
                && otherIngredient.getDate().equals(getDate());
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
                && otherIngredient.getAmount().equals(getAmount())
                && otherIngredient.getUnit().equals(getUnit())
                && otherIngredient.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getAmount(), getUnit(), date);
    }

    @Override
    public String toString() {
        return super.toString() + " Date: " + getDate().toString();
    }
}
