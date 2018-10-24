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

    public String toString() {
        return fullName.replaceAll("_", " ");
    }
}
