package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupHasNoMeetingException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.util.PersonPropertyComparator;
import seedu.address.model.shared.Title;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true for persons
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true for groups
     */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true for meetings
     */
    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replace the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the address book.
     */
    void updateGroup(Group target, Group editedGroup) throws GroupNotFoundException;

    // @@author Derek-Hardy
    /**
     * Add a group object {@code group} to the address book.
     * The group must not already exist in the address book.
     *
     * @param group The new group to be added into address book
     */
    void addGroup(Group group);

    /**
     * Remove a group {@code group} from the address book.
     * Report error message if the given group does not exist inside the address book.
     *
     * @param group The group to be removed from address book
     */
    void removeGroup(Group group);

    /**
     * Join a {@code person} into a {@code group}.
     * Both person and group must exist in the {@code AddressBook}.
     *
     */
    void joinGroup(Person person, Group group);


    /**
     * Remove a {@code person} from a {@code group}.
     * The person must exist in the group.
     */
    void leaveGroup(Person person, Group group);

    // @@author


    // @@author NyxF4ll
    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    boolean hasGroup(Group group);

    /** Returns an unmodifiable view of the group list */
    ObservableList<Group> getGroupList();
    // @@author

    /** Returns an existing group that matches the {@code title} */
    Group getGroupByTitle(Title title);

    /** Returns an existing person that matches the {@code name} */
    Person getPersonByName(Name name);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /** Returns an unmodifiable view of the filtered group list */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * @return An unmodifiable view of the sorted person list
     */
    ObservableList<Person> getSortedPersonList();

    /**
     * Sets meeting field of {@code group} in the group list to {@code meeting}.
     */
    public void setMeeting(Group group, Meeting meeting) throws GroupNotFoundException;

    /**
     * Resets meeting field of {@code group} in the group list to an empty optional.
     */
    public void cancelMeeting(Group group) throws GroupNotFoundException, GroupHasNoMeetingException;

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    /**
     * @return An unmodifiable view of the filtered meeting list
     */
    ObservableList<Meeting> getFilteredMeetingList();

    /**
     * Updates the sorting of the sorted person list to sort by the given {@code comparator}.
     * @param personPropertyComparator The comparator to sort the list by.
     */
    void updateSortedPersonList(PersonPropertyComparator personPropertyComparator);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Export the current address book.
     */
    void exportAddressBook(Path filepath);

    /**
     * Import the current address book.
     */
    void importAddressBook(ReadOnlyAddressBook importAddressBook, boolean overwrite);

    /**
     * Change User Preferences.
     */
    void changeUserPrefs(Path filepath);

    /**
     * Get Current Address Book File Path.
     */
    Path getAddressBookFilePath();
}
