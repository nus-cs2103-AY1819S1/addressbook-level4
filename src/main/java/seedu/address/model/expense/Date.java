package seedu.address.model.expense;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@@author jonathantjm
/**
 * Represents the date the Expense was added into the Expense tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date extends ExpenseField {
    public static final String DATE_FORMAT_CONSTRAINTS =
            "Date should be valid. Format dd-MM-yyyy";

    public static final String DATE_VALIDATION_REGEX = "(\\d{1,2})(\\-)(\\d{1,2})(\\-)(\\d{4})";
    private LocalDateTime fullDate;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    /**
     * Constructs a {@code Date} with current date.
     *
     */
    public Date() {
        this(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        super(date);
        checkArgument(isValidDate(date), DATE_FORMAT_CONSTRAINTS);
        String [] parsedDate = date.split("-");
        setFullDate(
                Integer.parseInt(parsedDate[2]),
                Integer.parseInt(parsedDate[1]),
                Integer.parseInt(parsedDate[0])
        );
    }

    /**
     * Returns a
     * @return
     */
    public LocalDateTime getFullDate() {
        return this.fullDate;
    }

    public void setFullDate(int year, int month, int day) {
        this.fullDate = LocalDateTime.of(year, month, day, 0, 0);

    }

    /**
     * return true is the given date is in the valid format.
     * */
    public static boolean isValidDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)) {
            String [] parsedDate = test.split("-");
            try {
                LocalDateTime date = LocalDateTime.of(Integer.parseInt(parsedDate[2]),
                        Integer.parseInt(parsedDate[1]),
                        Integer.parseInt(parsedDate[0]),
                        0,
                        0
                );
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return fullDate.format(dateFormat);
    }

    /**
     * Returns true if both Dates represent the same calendar date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return
            this.fullDate.getDayOfMonth() == otherDate.fullDate.getDayOfMonth()
            && this.fullDate.getMonthValue() == otherDate.fullDate.getMonthValue()
            && this.fullDate.getYear() == otherDate.fullDate.getYear();
    }

    /**
     *
     * @param a - First Date to compare
     * @param b - Second Date to compare
     * @return 1 if b is after a, -1 if b is before a and 0 if they are equal
     */
    public static int compare(Date a, Date b) {
        LocalDateTime first = a.fullDate;
        LocalDateTime second = b.fullDate;

        return second.compareTo(first);
    }
}
