package seedu.souschef.model.ingredient;

/**
 * class to store ingredient name.
 */
public class IngredientName {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Invalid Ingredient Name!";

    private final String fullName;

    public IngredientName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isValid() {
        return fullName.matches("^(\\p{Alpha}+)(_\\p{Alpha}+)*");
    }

    /**
     * return true if the name contains given keyword.
     */
    public boolean containsKeyword(String keyword) {
        String[] tokens = fullName.split("_");
        for (String token : tokens) {
            if (token.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientName)) {
            return false;
        }

        IngredientName otherIngredient = (IngredientName) other;

        return otherIngredient != null
                && otherIngredient.fullName.equalsIgnoreCase(fullName);
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode();
    }

    public String toString() {
        return fullName.replaceAll("_", " ");
    }
}
