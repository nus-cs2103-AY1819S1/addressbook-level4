package seedu.souschef.model.ingredient;

import java.text.ParseException;
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

    public IngredientDate(String date) throws ParseException {
        this.date = (new SimpleDateFormat("MM-dd-yyyy")).parse(date);
    }

    public String toString() {
        return new SimpleDateFormat("MM-dd-yyyy").format(date);
    }
}
