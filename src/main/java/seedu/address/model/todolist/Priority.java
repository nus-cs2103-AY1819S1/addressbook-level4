package seedu.address.model.todolist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the priority of a todoList event.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 * Setting Rule: user can set 1 for High; 2 for Medium; 3 for Low
 */

public class Priority {

    public static final String DEFAULT_PRIORITY = "1"; // default field name
    public static final String MESSAGE_CONSTRAINTS =
            "The valid user input for priority is p/[priority]."
                    + "While [priority] is a integer from 1 to 3,"
                    + "and 1 for high priority, 2 for medium priority, 3 for low priority";

    /*
     * There should be only one integer, and the integer should be 1, 2 or 3.
     */
    public static final String VALIDATION_REGEX = "\\d";

    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValid(priority), MESSAGE_CONSTRAINTS);
        value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValid(String priority) {
        if(priority.matches(VALIDATION_REGEX))
        {
            int i = Integer.parseInt(priority);
            if(i==1||i==2||i==3)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.todolist.Priority // instanceof handles nulls
                && value.equals(((seedu.address.model.todolist.Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
