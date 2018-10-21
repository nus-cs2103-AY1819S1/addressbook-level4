package seedu.address.ui;

import seedu.address.logic.commands.SortCommand.SortOrder;

//@@author snajef
/**
 * Interface to indicate that this panel view is sortable by column index
 * @author Darien Chong
 *
 */
public interface Sortable {

    /**
     * Method to sort the panel view by a given column index.
     * Multiple column indices indicate the relative order in which to sort by.
     */
    public void sortView(SortOrder order, int... colIdx);
}
