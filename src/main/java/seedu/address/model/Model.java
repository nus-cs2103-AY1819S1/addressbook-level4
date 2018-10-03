package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.carpark.Carpark;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Carpark> PREDICATE_SHOW_ALL_CARPARK = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a car park with the same identity as {@code carpark} exists in the address book.
     */
    boolean hasCarpark(Carpark carpark);

    /**
     * Deletes the given car park.
     * The car park must exist in the address book.
     */
    void deleteCarpark(Carpark target);

    /**
     * Adds the given car park.
     * {@code carpark} must not already exist in the address book.
     */
    void addCarpark(Carpark carpark);

    /**
     * Replaces the given car park {@code target} with {@code editedCarpark}.
     * {@code target} must exist in the address book.
     * The car park identity of {@code editedCarpark} must not be the same
     * as another existing car park in the address book.
     */
    void updateCarpark(Carpark target, Carpark editedCarpark);

    /**
     * Updates with a list of car parks {@code listCarpark}.
     */
    void loadCarpark(List<Carpark> listCarpark);

    /** Returns an unmodifiable view of the filtered car park list */
    ObservableList<Carpark> getFilteredCarparkList();

    /**
     * Updates the filter of the filtered car park list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCarparkList(Predicate<Carpark> predicate);

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
