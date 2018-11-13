package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an HealthBase
 */
public interface ReadOnlyHealthBase {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the checked out persons lists.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getCheckedOutPersonList();

}
