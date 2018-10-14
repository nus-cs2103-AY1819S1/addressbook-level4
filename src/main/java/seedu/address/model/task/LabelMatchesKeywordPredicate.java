package seedu.address.model.task;

import java.util.function.Predicate;

import seedu.address.model.tag.Label;

// TODO: Refactor code and rewrite class to look for labels
/**
 * Tests that a {@code Task}'s {@code Name} matches any of the keywords given.
 */
public class LabelMatchesKeywordPredicate implements Predicate<Task> {
    private final Label keyLabel;

    public LabelMatchesKeywordPredicate(String keyword) {
        Label keyLabel = new Label(keyword);
        this.keyLabel = keyLabel;
    }

    @Override
    public boolean test(Task task) {
        return task.getLabels().contains(keyLabel);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LabelMatchesKeywordPredicate // instanceof handles nulls
                && keyLabel.equals(((LabelMatchesKeywordPredicate) other).keyLabel)); // state check
    }

}
