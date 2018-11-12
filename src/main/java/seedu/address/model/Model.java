package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySsenisub newData);

    /** Returns Ssenisub */
    ReadOnlySsenisub getSsenisub();

    /**
     * Returns true if a person with the same identity as {@code person} exists in Ssenisub.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same Name as {@code person} exists in Ssenisub.
     */
    boolean hasName(Person person);

    /**
     * Returns true if a person has the same Phone Number as {@code person} exists in Ssenisub.
     */
    boolean hasPhoneNumber(Person person);

    /**
     * Returns true if a person has the same Email as {@code person} exists in Ssenisub.
     */
    boolean hasEmail(Person person);

    /**
     * Deletes the given person.
     * The person must exist in Ssenisub.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in Ssenisub.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in Ssenisub.
     * The person identity of {@code editedPerson} must not be the same as another existing person in Ssenisub.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Adds the given person to favourite.
     * The person must exist in Ssenisub.
     */
    void favouritePerson(Person target, Person favouritedPerson);

    /**
     * Removes the given person from favourite.
     * The person must exist in the favourites.
     */
    void unfavouritePerson(Person target, Person unfavouritedPerson);

    /**
     * Sorts the person list by name
     */
    void sortByName();

    /**
     * Sorts the person list by dept
     */
    void sortByDept();

    /**
     * Sorts the person list by rating from lowest to highest
     */
    void sortByRatingUp();

    /**
     * Sorts the person list by rating from highest to lowest
     */
    void sortByRatingDown();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoSsenisub();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoSsenisub();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoSsenisub();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoSsenisub();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitSsenisub();
}
