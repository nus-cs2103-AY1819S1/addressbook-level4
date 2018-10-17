package seedu.inventory.model.purchaseorder;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represents the required date of item to be approved in the purchase order.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class RequiredDate {


    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date must be in the format of 'yyyy-MM-dd' beyond the current date and comply with the calendar year";
    public static final String DATE_VALIDATION_REGEX = "^2\\d\\d\\d-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])";
    public final String requiredDate;

    /**
     * Constructs a {@code RequiredDate}.
     *
     * @param date A valid date
     */
    public RequiredDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        requiredDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        if (!date.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }

        Date tempDate;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false); //Ensure the date complies with the day in a month and also leap years
            tempDate = df.parse(date);
        } catch (ParseException e) {
            return false;
        }

        Date currentDate = new Date();

        //Ensure the date entered is after the current date
        return currentDate.getTime() < tempDate.getTime();
    }


    @Override
    public String toString() {
        return requiredDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequiredDate // instanceof handles nulls
                && requiredDate.equals(((RequiredDate) other).requiredDate)); // state check
    }

    @Override
    public int hashCode() {
        return requiredDate.hashCode();
    }

}
