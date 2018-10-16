package seedu.souschef.model.ingredient;

public class IngredientName {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Invalid Ingredient Name!";

    private final String fullName;

    public IngredientName(String fullName) {
        this.fullName = fullName;
    }

    public IngredientName(String[] tokens) {
        String fullName = "";
        for (int i = 0; i < tokens.length; i++) {
            if (i == tokens.length - 1) {
                fullName = fullName + tokens[i];
                break;
            }
            fullName = fullName + tokens[i] + "_";
        }
        this.fullName =  fullName;
    }

    public boolean isValid() {
        return fullName.matches("^(\\p{Alpha}+)(_\\p{Alpha}+)*");
    }

    public String[] getTokens() {
        return fullName.split("_");
    }


    public String toString() {
        return fullName;
    }
}
