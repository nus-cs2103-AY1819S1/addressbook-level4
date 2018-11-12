package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.model.task.exceptions.InvalidPredicateOperatorException;
import seedu.address.model.task.exceptions.InvalidPredicateTestPhraseException;

/**
 * Represents a Task's deadline in the deadline manager. Guarantees: immutable; represents a valid date
 */
public class Deadline implements Comparable<Deadline> {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline has to be a valid date";

    private static DateFormat dateFormatter;

    public final Date value;

    static {
        dateFormatter = new DeadlineDateFormat(new Locale("en", "SG"));
        dateFormatter.setLenient(false);
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(Date deadline) {
        requireNonNull(deadline);
        this.value = deadline;
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);

        ParsePosition parsePosition = new ParsePosition(0);
        this.value = dateFormatter.parse(deadline, parsePosition);
        if (this.value == null || parsePosition.getIndex() != deadline.length()) {
            throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    /**
     * Returns a new {@code Deadline}
     */

    public Deadline addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        cal.add(Calendar.DATE, days);
        return new Deadline(cal.getTime());
    }

    /**
     * Constructs a predicate from the given operator and test phrase.
     *
     * @param operator   The operator for this predicate.
     * @param testPhrase The test phrase for this predicate.
     */
    public static Predicate<Deadline> makeFilter(FilterOperator operator, String testPhrase)
            throws InvalidPredicateTestPhraseException, InvalidPredicateOperatorException {
        Deadline testDeadline;
        try {
            testDeadline = new Deadline(testPhrase);
        } catch (IllegalArgumentException e) {
            throw new InvalidPredicateTestPhraseException(e);
        }
        switch (operator) {
        case EQUAL:
            return deadline -> deadline.equals(testDeadline);
        case CONVENIENCE: // convenience operator, works the same as "<"
            //Fallthrough
        case LESS:
            return deadline -> deadline.compareTo(testDeadline) <= 0;
        case GREATER:
            return deadline -> deadline.compareTo(testDeadline) >= 0;
        default:
            throw new InvalidPredicateOperatorException();
        }
    }

    @Override
    public String toString() {
        return dateFormatter.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Deadline other) {
        return this.value.compareTo(other.value);
    }
}
