package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.tag.Label;

/**
 * Tests that a {@code Task}'s {@code Name} matches any of the keywords given.
 */
public class LabelMatchesAnyKeywordPredicate implements Predicate<Task> {
    private final Set<Label> keyLabels;

    public LabelMatchesAnyKeywordPredicate(Set<Label> keyLabels) {
        requireNonNull(keyLabels);
        // Standardises keyword toLowerCase such that the label predicate
        // is case-insensitive
        this.keyLabels = keyLabels
            .parallelStream()
            .map(keyword -> new Label(keyword.labelName.toLowerCase()))
            .collect(Collectors.toSet());
    }

    /**
     * Precondition: The predicate's keylabel should be in lowercase in order to
     * perform a case insensitive check.
     * <p>
     * Checks the {@code Task}'s set of labels for a case-insensitive match.
     *
     * @param task to check for the interested label
     * @return True if the task contains any of the predicate's keyLabels
     */
    @Override
    public boolean test(Task task) {
        return task
            .getLabels()
            .parallelStream()
            .map(label -> new Label(label.labelName.toLowerCase()))
            .anyMatch(label -> keyLabels.contains(label));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LabelMatchesAnyKeywordPredicate // instanceof handles nulls
            && keyLabels
            .equals(((LabelMatchesAnyKeywordPredicate) other).keyLabels)); // state check
    }

}
