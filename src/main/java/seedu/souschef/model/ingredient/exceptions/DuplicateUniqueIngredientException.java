package seedu.souschef.model.ingredient.exceptions;

/**
 * Signals that the operation will result in duplicate unique ingredient (Unique ingredient are considered duplicates
 * if they have the same identity).
 */
public class DuplicateUniqueIngredientException extends RuntimeException {
    public DuplicateUniqueIngredientException() {
        super("Operation would result in duplicate unique ingredients");
    }
}
