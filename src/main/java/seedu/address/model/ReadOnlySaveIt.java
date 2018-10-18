package seedu.address.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an saveIt
 */
public interface ReadOnlySaveIt {

    /**
     * Returns an unmodifiable view of the issues list.
     * This list will not contain any duplicate issues.
     */
    ObservableList<Issue> getIssueList();

    int getCurrentDirectory();
}
