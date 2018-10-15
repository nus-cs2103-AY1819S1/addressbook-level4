package seedu.address.model.task;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents a Task's dependency to another task in the task manager.
 * Guarantees: immutable;
 */
public class Dependency {

    private Set<String> value = new HashSet<>();

    /**
     * Constructs an {@code Dependency}.
     *
     * @param hashes A list of hashes of task dependencies.
     */
    public Dependency(Set<String> hashes) {
        value = new HashSet<String>(hashes);
    }

    /**
     * Constructs an empty dependency object
     */
    public Dependency(){}

    /**
     * Adds a task that it is dependent on.
     * @param task
     * @return new dependency with the additional dependent task's hashcode
     */
    public Dependency addDependency(Task task) {
        Set<String> newValue = new HashSet<>(value);
        newValue.add(Integer.toString(task.hashCode()));
        return new Dependency(newValue);
    }

    /**
     * Removes a dependency to a task
     * @param task
     * @return new dependency object without hashcode of given task
     */
    public Dependency removeDependency(Task task) {
        Set<String> newValue = new HashSet<>(value);
        newValue.remove(Integer.toString(task.hashCode()));
        return new Dependency(newValue);
    }

    /**
     * Checks if task is contained within the internal representation
     * @param task
     * @return boolean value of whether task is contained within hashset
     */
    public boolean containsDependency(Task task) {
        return value.contains(Integer.toString(task.hashCode()));
    }

    /**
     * Returns the hashes of all the tasks specified in the dependency
     * @return set of all hashes
     */
    public Set<String> getHashes() {
        return value;
    }

    /**
     * Returns the hashes of all the tasks specified in the dependency
     * @return set of all hashes
     */
    public Dependency updateHash(String oldHash, String newHash) {
        Set<String> newValue = new HashSet<>(value);
        newValue.remove(oldHash);
        newValue.add(newHash);
        return new Dependency(newValue);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String hash: value) {
            builder.append(hash);
            builder.append(" ");
        }
        return builder.toString();
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
