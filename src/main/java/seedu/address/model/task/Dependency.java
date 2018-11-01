package seedu.address.model.task;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents a Task's dependency to another task in the task manager.
 * Guarantees: immutable;
 */
public class Dependency {

    private Set<String> hashes = new HashSet<>();

    /**
     * Constructs an {@code Dependency}.
     *
     * @param hashes A list of hashes of task dependencies.
     */
    public Dependency(Set<String> hashes) {
        this.hashes = new HashSet<String>(hashes);
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
        Set<String> newValue = new HashSet<>(hashes);
        newValue.add(Integer.toString(task.hashCode()));
        return new Dependency(newValue);
    }

    /**
     * Removes a dependency to a task
     * @param task
     * @return new dependency object without hashcode of given task
     */
    public Dependency removeDependency(Task task) {
        Set<String> newValue = new HashSet<>(hashes);
        newValue.remove(Integer.toString(task.hashCode()));
        return new Dependency(newValue);
    }

    /**
     * Checks if task is contained within the internal representation
     * @param task
     * @return boolean hashes of whether task is contained within hashset
     */
    public boolean containsDependency(Task task) {
        return hashes.contains(Integer.toString(task.hashCode()));
    }

    /**
     * Returns the hashes of all the tasks specified in the dependency
     * @return set of all hashes
     */
    public Set<String> getHashes() {
        return hashes;
    }

    /**
     * Returns the number of dependencies present.
     * @return an Integer representing the number of dependencies present.
     */
    public Integer getDependencyCount() {
        return hashes.size();
    }

    /**
     * Returns the hashes of all the tasks specified in the dependency
     * @return set of all hashes
     */
    public Dependency updateHash(String oldHash, String newHash) {
        Set<String> newValue = new HashSet<>(hashes);
        newValue.remove(oldHash);
        newValue.add(newHash);
        return new Dependency(newValue);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String hash: hashes) {
            builder.append(hash);
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Dependency // instanceof handles nulls
                && hashes.equals(((Dependency) other).hashes)); // state check
    }
    @Override
    public int hashCode() {
        return hashes.hashCode();
    }

}
