package seedu.souschef.model.ingredient;

import seedu.souschef.model.UniqueType;

/**
 * ingredient class that stores ingredient name, amount, serving unit.
 */
public class IngredientPortion extends IngredientDefinition {
    public static final String MESSAGE_INGREDIENTPORTION_CONSTRAINTS =
            "Ingredient portion should have \"#ingredient name amount unit\", where amount should be a number.";
    public static final String INGREDIENTPORTION_VALIDATION_REGEX =
            "^(?<name>(\\p{Alpha}|\\s)+)(?<amt>(((\\d)+.(\\d)+)|((\\d)+)))\\s(?<unit>(\\p{Alpha})+)(?<ins>(.*))";
    private final IngredientServingUnit unit;
    private final IngredientAmount amount;

    public IngredientPortion(IngredientName name, IngredientServingUnit unit, IngredientAmount amount) {
        super(name);
        this.unit = unit;
        this.amount = amount;
    }

    public IngredientPortion(String name, String unit, Double amount) {
        super(name);
        this.unit = new IngredientServingUnit(unit);
        this.amount = new IngredientAmount(amount);
    }

    public IngredientServingUnit getUnit() {
        return unit;
    }

    public IngredientAmount getAmount() {
        return amount;
    }

    /**
     * Add amount between ingredients
     */
    public IngredientPortion addAmount(Object other) {
        IngredientPortion otherIngredient = (IngredientPortion) other;
        double total = this.getAmount().getValue() + otherIngredient.getAmount().getValue();
        return new IngredientPortion(getName(), getUnit(), new IngredientAmount(total));
    }

    /**
     * Subtract amount between ingredients
     */
    public IngredientPortion subtractAmount(Object other) {
        IngredientPortion otherIngredient = (IngredientPortion) other;
        double total = this.getAmount().getValue() - otherIngredient.getAmount().getValue();
        if (total <= 0) {
            total = 0.0;
        }
        return new IngredientPortion(getName(), getUnit(), new IngredientAmount(total));
    }

    /**
     * multiply double to ingredient amount
     */
    public IngredientPortion multiplyAmount(double numberOfServings) {
        double amount = getAmount().getValue() * numberOfServings;
        return new IngredientPortion(getName(), getUnit(), new IngredientAmount(amount));
    }

    /**
     * Convert amount of Ingredient to common unit
     */
    public IngredientPortion convertToCommonUnit() {
        IngredientServingUnitDefinition definition = IngredientServingUnit.DICTIONARY.get(this.getUnit().toString());
        IngredientName ingredientName = getName();
        IngredientServingUnit ingredientUnit = new IngredientServingUnit(definition.getCommonUnit());
        IngredientAmount ingredientAmount =
                new IngredientAmount(amount.getValue().doubleValue() * definition.getConversionValue().doubleValue());

        return new IngredientPortion(ingredientName, ingredientUnit, ingredientAmount);
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

        if (!(other instanceof IngredientPortion)) {
            return false;
        }

        IngredientPortion otherIngredient = (IngredientPortion) other;

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

        if (!(other instanceof IngredientPortion)) {
            return false;
        }

        IngredientPortion otherIngredient = (IngredientPortion) other;

        return otherIngredient != null
                && otherIngredient.getName().equals(getName())
                && otherIngredient.getAmount().equals(getAmount())
                && otherIngredient.getUnit().equals(getUnit());
    }

    @Override
    public String toString() {
        return super.toString() + " Amount: " + getAmount().toString() + " " + getUnit();
    }
}
