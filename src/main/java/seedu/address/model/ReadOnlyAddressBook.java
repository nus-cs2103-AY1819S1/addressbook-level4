package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the groups list.
     * This list will not contain any duplicate groups.
     * NOTE: this class is created temporarily to create the initial working UI as {@code Group} functionality is
     * still being developed. In the meantime, this class will be used instead to showcase how the UI will look like,
     * and will be deprecated progressively when Group implementation is updated.
     */
    @Deprecated
    ObservableList<Tag> getGroupTagList();
    /**
     * Returns an unmodifiable view of the group list.
     * This list will not contain any duplicate groups.
     */
    ObservableList<Group> getGroupList();

}
