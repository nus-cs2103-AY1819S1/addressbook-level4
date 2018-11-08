package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.model.project.Assignment;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<LeaveApplicationWithEmployee> PREDICATE_SHOW_ALL_LEAVEAPPLICATIONS = unused -> true;
    Predicate<Assignment> PREDICATE_SHOW_ALL_ASSIGNMENTS = unused -> true;
    Predicate<LeaveApplicationWithEmployee> PREDICATE_SHOW_PENDING_LEAVEAPPLICATIONS =
        leaveApplication -> leaveApplication.getLeaveStatus().value.equals(StatusEnum.Status.PENDING);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetArchive(ReadOnlyArchiveList newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the AssignmentList */
    ReadOnlyAssignmentList getAssignmentList();

    /** Returns the ArchiveList */
    ReadOnlyArchiveList getArchiveList();

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
     * Restores the given person in the archive list.
     * The person must exist in the archive list.
     */
    void restorePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Checks if the given username is already in the addressbook, for the current version.
     * You can choose to ignore a person's username when doing so (i.e. you'er planning to update that person)
     * @param username the Username to check against
     * @param ignore The person to ignore, if any. null for no one.
     * @return True if it's already in the address book, false otherwise
     */
    boolean alreadyContainsUsername(String username, Person ignore);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Gets the currently logged in user.
     * @return The currently logged in user.
     */
    User getLoggedInUser();

    /**
     * Sets the currently logged in user.
     * @param u The currently logged in use
     */
    void setLoggedInUser(User u);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the archived person list */
    ObservableList<Person> getArchivedPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the archived person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateArchivedPersonList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered leave application list */
    ObservableList<LeaveApplicationWithEmployee> getFilteredLeaveApplicationList();

    /**
     * Updates the filter of the filtered leave application list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLeaveApplicationList(Predicate<LeaveApplicationWithEmployee> predicate);

    /**
     * Updates the filter of the filtered leave application list to filter by the given {@code person}.
     * @throws NullPointerException if {@code person} is null.
     */
    void updateFilteredLeaveApplicationListForPerson(Person person);

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

    /*---------------------------------------------------------------*/

    /**
     * Returns true if a assignment with the same identity as {@code assignment} exists in the address book.
     */
    boolean hasAssignment(Assignment assignment);

    /**
     * Deletes the given assignment.
     * The assignment must exist in the address book.
     */
    void deleteAssignment(Assignment target);

    /**
     * Adds the given assignment.
     * {@code assignment} must not already exist in the address book.
     */
    void addAssignment(Assignment assignment);

    /**
     * Replaces the given assignment {@code target} with {@code editedAssignment}.
     * {@code target} must exist in the address book.
     * The assignment identity of {@code editedAssignment} must not be the same as another existing
     * assignment in the address book.
     */
    void updateAssignment(Assignment target, Assignment editedAssignment);

    /**
     * Checks if the given assignment name is already in the assignmentlist, for the current version.
     * You can choose to ignore an assignment's name when doing so (i.e. you'er planning to update that assignment)
     * @param newAssignment the AssignmentName to check against
     * @param ignore The person to ignore, if any. null for no one.
     * @return False if it's already in the assignment list, true otherwise
     */
    boolean containsAssignment(String newAssignment, Assignment ignore);

    /** Returns an unmodifiable view of the filtered assignment list */
    ObservableList<Assignment> getFilteredAssignmentList();

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAssignmentList(Predicate<Assignment> predicate);

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code person}.
     * @throws NullPointerException if {@code person} is null.
     */
    void updateFilteredAssignmentListForPerson(Person person);

    /**
     * Updates the address book to remove all undo and redo saved versions, as if it had been re-initalized.
     */
    void restartAddressBook();
}
