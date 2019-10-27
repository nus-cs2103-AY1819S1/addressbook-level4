package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entry.ResumeEntry;

/**
 * Unmodifiable view of an entry book
 */
public interface ReadOnlyEntryBook {
    /**
     * Returns an unmodifiable view of the entries list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<ResumeEntry> getEntryList();
}
