package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code OrderBook} that keeps track of its own history.
 */
public class VersionedOrderBook extends OrderBook {

    private final List<ReadOnlyOrderBook> orderBookStateList;
    private int currentStatePointer;

    public VersionedOrderBook(ReadOnlyOrderBook initialState) {
        super(initialState);

        orderBookStateList = new ArrayList<>();
        orderBookStateList.add(new OrderBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code OrderBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        orderBookStateList.add(new OrderBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        orderBookStateList.subList(currentStatePointer + 1, orderBookStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(orderBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(orderBookStateList.get(currentStatePointer));
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
        return currentStatePointer < orderBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedOrderBook)) {
            return false;
        }

        VersionedOrderBook otherVersionedOrderBook = (VersionedOrderBook) other;

        // state check
        return super.equals(otherVersionedOrderBook)
                && orderBookStateList.equals(otherVersionedOrderBook.orderBookStateList)
                && currentStatePointer == otherVersionedOrderBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of orderBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of orderBookState list, unable to redo.");
        }
    }
}
