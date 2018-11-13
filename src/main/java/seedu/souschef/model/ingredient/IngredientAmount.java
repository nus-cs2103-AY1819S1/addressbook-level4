package seedu.souschef.model.ingredient;

/**
 * class to store ingredient amount.
 */
public class IngredientAmount {
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Invalid Ingredient Amount!";

    private final double amount;

    public IngredientAmount(double amount) {
        this.amount = amount;
    }

    public Double getValue() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientAmount)) {
            return false;
        }

        IngredientAmount otherAmount = (IngredientAmount) other;

        return otherAmount != null
                && this.amount == otherAmount.amount;
    }

    public String toString() {
        return Double.toString(amount);
    }
}
