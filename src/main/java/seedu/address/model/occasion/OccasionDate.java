package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;


/**
 * Represents an Occasions date in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidDate(String)}
 * @author kongzijin
 */
public class OccasionDate {

    public static final String MESSAGE_OCCASIONDATE_CONSTRAINTS = "Occasion "
        + "dates should be in the format YYYY-MM-DD, and should not be in the "
        + "blank.";

    /*
     * The date should be in the format YYYY-MM-DD. The date value should be
     * valid.
     * TODO: Further constrain date to be a date in the future.
     */
    public static final String OCCASIONDATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String fullOccasionDate;

    /**
     * Empty Constructor.
     */
    public OccasionDate() {
        fullOccasionDate = "";
    }
    /**
     * Constructs a {@code OccasionDate}.
     *
     * @param date An occasion date.
     */
    public OccasionDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_OCCASIONDATE_CONSTRAINTS);
        fullOccasionDate = date;
    }

    /**
     * Check whether a given string is in correct format and is a valid date.
     *
     * @return A boolean value indicating the validation of this date.
     */
    // @@author KongZijin-reused
    // Reused from
    // https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
    // with minor modifications.
    public static boolean isValidDate(String test) {
        if (!test.matches(OCCASIONDATE_VALIDATION_REGEX)) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        dateFormat.setLenient(false);

        dateFormat.parse(test, new ParsePosition(1));
        return true;

    }
    //@@author

    @Override
    public String toString() {
        return fullOccasionDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OccasionDate // instanceof handles nulls
            && fullOccasionDate.equals(((OccasionDate) other).fullOccasionDate));
    }

    @Override
    public int hashCode() {
        return fullOccasionDate.hashCode();
    }
}
