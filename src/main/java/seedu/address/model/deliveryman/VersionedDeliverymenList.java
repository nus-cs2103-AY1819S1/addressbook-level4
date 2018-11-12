package seedu.address.model.deliveryman;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a versioned version of the deliverymen list.
 */
public class VersionedDeliverymenList extends DeliverymenList {

    private final List<DeliverymenList> deliverymenListStateList;
    private int currentStatePointer;

    public VersionedDeliverymenList(DeliverymenList initialState) {
        super(initialState);

        deliverymenListStateList = new ArrayList<>();
        deliverymenListStateList.add(new DeliverymenList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code DeliverymenList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        deliverymenListStateList.add(new DeliverymenList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        deliverymenListStateList.subList(currentStatePointer + 1, deliverymenListStateList.size()).clear();
    }

    /**
     * Restores the deliverymen list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(deliverymenListStateList.get(currentStatePointer));
    }

    /**
     * Restores the deliverymen list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(deliverymenListStateList.get(currentStatePointer));
    }

    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    public boolean canRedo() {
        return currentStatePointer < deliverymenListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedDeliverymenList)) {
            return false;
        }

        VersionedDeliverymenList otherList = (VersionedDeliverymenList) other;

        return super.equals(otherList)
                && deliverymenListStateList.equals(otherList.deliverymenListStateList)
                && currentStatePointer == otherList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of deliverymenListState list, unable to undo.");
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
