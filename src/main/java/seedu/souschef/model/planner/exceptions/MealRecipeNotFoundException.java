package seedu.souschef.model.planner.exceptions;

/**
 * Signals that no recipe is found at the specified meal index of the day.
 */
public class MealRecipeNotFoundException extends RuntimeException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public MealRecipeNotFoundException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public MealRecipeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
