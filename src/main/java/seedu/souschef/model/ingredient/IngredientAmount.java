package seedu.souschef.model.ingredient;

public class IngredientAmount {
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Invalid Ingredient Amount!";

    private final double amount;

    public IngredientAmount(double amount) {
        this.amount = amount;
    }

    public IngredientAmount(String amount) throws NumberFormatException {
        this.amount = Double.parseDouble(amount);
    }

    public String toString() {
        return Double.toString(amount);
    }
}
