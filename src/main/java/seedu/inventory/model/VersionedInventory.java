package seedu.inventory.model;

import java.util.ArrayList;
import java.util.List;

import seedu.inventory.model.staff.Staff;

/**
 * {@code Inventory} that keeps track of its own history.
 */
public class VersionedInventory extends Inventory {

    private final List<ReadOnlyInventory> inventoryStateList;
    private int currentStatePointer;
    private UserSession session;

    public VersionedInventory(ReadOnlyInventory initialState) {
        super(initialState);

        inventoryStateList = new ArrayList<>();
        inventoryStateList.add(new Inventory(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Inventory} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        inventoryStateList.add(new Inventory(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        inventoryStateList.subList(currentStatePointer + 1, inventoryStateList.size()).clear();
    }

    /**
     * Authenticate users.
     */
    public void authenticateUser(Staff staff) {
        session = new UserSession(staff);
    }

    /**
     * Restores the inventory to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(inventoryStateList.get(currentStatePointer));
    }

    /**
     * Restores the inventory to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(inventoryStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has inventory states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has inventory states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < inventoryStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedInventory)) {
            return false;
        }

        VersionedInventory otherVersionedInventory = (VersionedInventory) other;

        // state check
        return super.equals(otherVersionedInventory)
                && inventoryStateList.equals(otherVersionedInventory.inventoryStateList)
                && currentStatePointer == otherVersionedInventory.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of inventoryState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of inventoryState list, unable to redo.");
        }
    }
}
