package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book.
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the person list, with only existing persons.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
