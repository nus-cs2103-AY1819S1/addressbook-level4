package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.entity.Entity;
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
     * Returns true if an entity with the same identity as {@code entity} exists in the address book.
     */
    boolean hasEntity(Entity entity);

    /**
     * Deletes the given entity.
     * The entity must exist in the address book.
     */
    void deleteEntity(Entity target);

    /**
     * Adds the given entity.
     * {@code entity} must not already exist in the address book.
     */
    void addEntity(Entity entity);

    /**
     * Replaces the given entity {@code target} with {@code editedEntity}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedPerson} must not be the same as another existing entity in the address book.
     */
    void updateEntity(Entity target, Entity editedEntity);

    /** Returns an unmodifiable view of the filtered persons list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // TODO: Include Filtered Module and Occasion lists

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
}
