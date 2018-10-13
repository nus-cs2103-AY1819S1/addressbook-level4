package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Task's dependency to another task in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDependency(String)}
 */
public class Dependency {

    public static final String MESSAGE_DEPENDENCY_CONSTRAINTS =
            "Dependency can only be specified using the hashcode of object";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */ //TODO: CHANGE TO CHECK FOR HASHCODE
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";

    private List<Task> value;

    /**
     * Constructs an {@code Dependency}.
     *
     * @param dependencies A list of task dependencies.
     */
    public Dependency(List<Task> dependencies) {
        requireNonNull(dependencies);
        //checkArgument(isValidDependency(dependencies), MESSAGE_DEPENDENCY_CONSTRAINTS);
        value = dependencies;
    }

    public Dependency(){
        value = new ArrayList<Task>();
    }

    /**
     * Returns true if a given string is a valid dependency.
     */
    public static boolean isValidDependency(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Dependency // instanceof handles nulls
                && value.equals(((Dependency) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
