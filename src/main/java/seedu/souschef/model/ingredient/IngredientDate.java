package seedu.souschef.model.ingredient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class to store ingredient date.
 */
public class IngredientDate {
    public static final String MESSAGE_DATE_CONSTRAINTS = "Invalid Ingredient Date!";

    private final Date date;

    public IngredientDate(Date date) {
        this.date = date;
    }

    public Date getValue() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientDate)) {
            return false;
        }

        IngredientDate otherDate = (IngredientDate) other;

        return otherDate != null
                && this.date.equals(otherDate.date);
    }

    public String toString() {
        return new SimpleDateFormat("MM-dd-yyyy").format(date);
    }
}
