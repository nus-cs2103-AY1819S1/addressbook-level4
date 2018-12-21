package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
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
     * Adds the given person at the given index
     * {@code person} must not already exist in the address book.
     */
    void addPersonAt(Person person, int index);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Returns true if an assignment with the same identity as {@code assignment} exists in the address book.
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
     * The assignment identity of {@code editedAssignment} must not be the same as another existing assignment in the
     * address book.
     */
    void updateAssignment(Assignment target, Assignment editedAssignment);

    /** Returns an unmodifiable view of the assignment list */
    ObservableList<Assignment> getFilteredAssignmentList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the attendance list */
    ObservableList<Attendance> getFilteredAttendanceList();

    /** Sorts the persons in the AddressBook lexicographically */
    void sort();

    /** Sorts the assignments in the AddressBook by deadline */
    void sortAssignment();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

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
     * Returns true if an lesson with the same identity as {@code assignment} exists in the address book.
     */
    boolean hasAttendance(Attendance attendance);

    /**
     * Deletes the given lesson.
     * The lesson must exist in the address book.
     */
    void deleteAttendance(Attendance target);

    /**
     * Adds the given lesson.
     * {@code lesson} must not already exist in the address book.
     */
    void addAttendance(Attendance attendance);

    /**
     * Replaces the given lesson {@code target} with {@code editedLesson}.
     * {@code target} must exist in the address book.
     * The lesson identity of {@code editedAssignment} must not be the same as another existing lesson in the
     * address book.
     */
    void updateAttendance(Attendance target, Attendance editedAttendance);
}
