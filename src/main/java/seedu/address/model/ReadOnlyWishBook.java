package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.wish.Wish;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyWishBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Wish> getWishList();

}
