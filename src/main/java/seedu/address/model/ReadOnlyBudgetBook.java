package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of a budget book
 */
public interface ReadOnlyBudgetBook {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Tag> getCcaTagList();
}
