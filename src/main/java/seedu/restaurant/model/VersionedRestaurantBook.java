package seedu.restaurant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code RestaurantBook} that keeps track of its own history.
 */
public class VersionedRestaurantBook extends RestaurantBook {

    private final List<ReadOnlyRestaurantBook> restaurantBookStateList;
    private int currentStatePointer;

    public VersionedRestaurantBook(ReadOnlyRestaurantBook initialState) {
        super(initialState);

        restaurantBookStateList = new ArrayList<>();
        restaurantBookStateList.add(new RestaurantBook(initialState));
        currentStatePointer = 0;
    }

    //@@author AZhiKai
    /**
     * Resets the pointer of the versioned restaurant book after saving the latest state.
     */
    public void reset() {
        ReadOnlyRestaurantBook finalState = restaurantBookStateList.get(currentStatePointer);
        restaurantBookStateList.clear();
        restaurantBookStateList.add(finalState);
        currentStatePointer = 0;
    }

    //@@author
    /**
     * Saves a copy of the current {@code RestaurantBook} state at the end of the state list. Undone states are removed
     * from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        restaurantBookStateList.add(new RestaurantBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        restaurantBookStateList.subList(currentStatePointer + 1, restaurantBookStateList.size()).clear();
    }

    /**
     * Restores the restaurant book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(restaurantBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the restaurant book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(restaurantBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has restaurant book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has restaurant book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < restaurantBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedRestaurantBook)) {
            return false;
        }

        VersionedRestaurantBook otherVersionedRestaurantBook = (VersionedRestaurantBook) other;

        // state check
        return super.equals(otherVersionedRestaurantBook)
                && restaurantBookStateList.equals(otherVersionedRestaurantBook.restaurantBookStateList)
                && currentStatePointer == otherVersionedRestaurantBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {

        private NoUndoableStateException() {
            super("Current state pointer at start of restaurantBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {

        private NoRedoableStateException() {
            super("Current state pointer at end of restaurantBookState list, unable to redo.");
        }
    }
}
