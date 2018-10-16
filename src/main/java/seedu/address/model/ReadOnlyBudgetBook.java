package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.cca.Cca;

/**
 * Unmodifiable view of a budget book
 */
public interface ReadOnlyBudgetBook {
    /**
     * Returns an unmodifiable view of the cca list.
     * This list will not contain any duplicate cca.
     */
    ObservableList<Cca> getCcaList();
}
