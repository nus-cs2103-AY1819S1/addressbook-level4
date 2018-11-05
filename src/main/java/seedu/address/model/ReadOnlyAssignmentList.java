package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.project.Assignment;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAssignmentList {

    /**
     * Returns an unmodifiable view of the projects list.
     * This list will not contain any duplicate projects.
     */
    ObservableList<Assignment> getAssignmentList();
}
