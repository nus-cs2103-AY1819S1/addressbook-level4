package seedu.souschef.model.ingredient;

/**
 * class to store ingredient name.
 */
public class IngredientName {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Invalid Ingredient Name!";

    private final String fullName;

    public IngredientName(String fullName) {
        this.fullName = fullName.toLowerCase();
    }

    public static boolean isValid(String name) {
        return name.matches("^[a-zA-Z\\s][a-zA-Z\\s]+$");
    }

    /**
     * return true if the name contains given keyword.
     */
    public boolean containsKeyword(String keyword) {
        String[] tokens = fullName.split("\\s+");
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

        IngredientName otherName = (IngredientName) other;

        return otherName != null
                && this.fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode();
    }

    public String toString() {
        return fullName;
    }
}
