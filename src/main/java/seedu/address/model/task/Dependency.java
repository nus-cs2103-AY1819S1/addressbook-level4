package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.TaskManager;

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


    //TODO: Add information about how to initialize hashes
    private Set<String> value = new HashSet<>();

    /**
     * Constructs an {@code Dependency}.
     *
     * @param dependencies A list of task dependencies.
     */
    //TODO: Remove below
    /**
    public Dependency(Set<Task> dependencies) {
        requireNonNull(dependencies);
        //checkArgument(isValidDependency(dependencies), MESSAGE_DEPENDENCY_CONSTRAINTS);
        value = dependencies;
    }
     **/
    public Dependency(Set<String> hashes) {
        if (hashes != null)
            value = new HashSet<String>(hashes);
    }

    /**
     * Constructs an empty dependency object
     */
    public Dependency(){}

    public boolean checkDependency(Task task) {
        return value.contains(task);
    }

    //TODO: Add an uninitialized check
    /**
     * Adds a task that it is dependent on.
     * @param task
     * @return same dependency with the additional dependent task
     */
    public Dependency addDependency(Task task) {
        Set<String> newValue = new HashSet<>(value);
        newValue.add(Integer.toString(task.hashCode()));
        return new Dependency(newValue);
    }

    public Dependency removeDependency(Task task) {
        Set<String> newValue = new HashSet<>(value);
        newValue.remove(Integer.toString(task.hashCode()));
        return new Dependency(newValue);
    }

    }

    /**
     * Returns true if a given string is a valid dependency.
     */
    public static boolean isValidDependency(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
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
