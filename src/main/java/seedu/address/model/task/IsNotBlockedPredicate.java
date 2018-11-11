package seedu.address.model.task;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.Model;

/**
 * Tests that a {@code Task} is not blocked. This means that it does not have a dependency.
 */
public class IsNotBlockedPredicate implements Predicate<Task> {
    private Model model;

    private IsNotBlockedPredicate() {}

    public IsNotBlockedPredicate(Model model) {
        this.model = model;
    }

    @Override
    public boolean test(Task task) {
        return task
                .getDependency()
                .getHashes()
                .stream()
                .map(hash -> model
                        .getTaskManager()
                        .getTaskList()
                        .filtered(t -> String.valueOf(t.hashCode()).equals(hash))
                        .get(0))
                .filter(t -> !t.isStatusCompleted())
                .collect(Collectors.toList())
                .size() == 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsNotBlockedPredicate); // instanceof handles nulls
    }

}
