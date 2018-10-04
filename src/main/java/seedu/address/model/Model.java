package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import org.simplejavamail.email.Email;

import javafx.collections.ObservableList;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the BudgetBook
     */
    ReadOnlyBudgetBook getBudgetBook();

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
     * Clears the given persons.
     * The persons must exist in the address book.
     */
    void clearMultiplePersons(List<Person> target);

    /**
     * Removes tag(s) from given persons.
     * The persons must exist in the address book.
     */
    void removeTagsFromPersons(List<Person> target, List<Person> original);

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
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered cca list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCcaList(Predicate<Cca> predicate);

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
     * Returns an unmodifiable view of the filtered CCA list
     */
    ObservableList<Cca> getFilteredCcaList();

    /**
     * Saves the email to hard disk
     */
    void saveEmail(Email email);

    /**
     * Returns true if the model already has a calendar with the same month and year
     */
    boolean isExistingCalendar(Year year, Month month);

    /**
     * Creates the given calendar
     * {@code year} and {@code month} must not already be an existing calendar
     */
    void createCalendar(Year year, Month month);

    /**
     * Updates the existing calendar map inside UserPrefs Json file
     */
    void updateExistingCalendar();

}
