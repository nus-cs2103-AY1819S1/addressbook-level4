package ssp.scheduleplanner.model.task;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's priority in the Schedule Planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Priority should be only 1, 2 or 3\n"
            + "Where 1 is the lowest priority level and 3 is the highest priority level\n";
    // value 1,2 or 3
    public static final String PRIORITY_VALIDATION_REGEX = "[123]";


    public final String value;

    /**
     * Constructs an {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_PRIORITY_CONSTRAINTS);
        value = priority;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * @return Integer value of this priority.
     */
    public int toInt() {
        return Integer.parseInt(value);
    }

    /**
     * @param a
     * @param b
     * @return -1 if a has smaller integer value than b,
     * 0 if integer value of a equals to b.
     * 1 if integer value of a is larger than b.
     */
    public static int compare(Priority a, Priority b) {
        if (a.toInt() > b.toInt()) {
            return -1;
        } else if (a.toInt() == b.toInt()) {
            return 0;
        } else {
            return 1;
        }
    }

}
