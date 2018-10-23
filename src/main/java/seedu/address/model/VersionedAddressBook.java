package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    private final List<ObservableList<Person>> personListStateList;
    private int currentPersonStatePointer;

    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;

        personListStateList = new ArrayList<>();
        currentPersonStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the addressBook state
     * list.
     * Saves a copy of the current {@code UniquePersonList} state at the end of the person
     * list state list.
     * Undone states are removed from both the addressBook and the personList state list.
     */
    public void commitPerson() {
        commit();

        removePersonStatesAfterCurrentPointer();
      //  personListStateList.add(new UniquePersonList(this.getPersonList()));
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    private void removePersonStatesAfterCurrentPointer() {
        personListStateList.subList(currentPersonStatePointer + 1, personListStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undoAll() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restore the person list in the address book to its previous state.
     */
    public void undoPerson() {
        if (!canUndoPerson()) {
            throw new NoUndoablePersonStateException();
        }
        currentPersonStatePointer--;
        resetPersonData(personListStateList.get(currentPersonStatePointer));

    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redoAll() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    /**
     * Returns true if if {@code undoPerson()} has person list states in address book to undo.
     */
    public boolean canUndoPerson() { return currentPersonStatePointer > 0; }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code undoPerson()} but can't.
     */
    public static class NoUndoablePersonStateException extends RuntimeException {
        private NoUndoablePersonStateException() {
            super("Current state pointer at start of personListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
