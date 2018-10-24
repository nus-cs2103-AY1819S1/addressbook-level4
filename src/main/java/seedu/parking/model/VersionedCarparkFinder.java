package seedu.parking.model;

import java.util.ArrayList;
import java.util.List;

import seedu.parking.model.carpark.Carpark;

/**
 * {@code CarparkFinder} that keeps track of its own history.
 */
public class VersionedCarparkFinder extends CarparkFinder {

    private final List<ReadOnlyCarparkFinder> carparkFinderStateList;
    private int currentStatePointer;

    public VersionedCarparkFinder(ReadOnlyCarparkFinder initialState) {
        super(initialState);

        carparkFinderStateList = new ArrayList<>();
        carparkFinderStateList.add(new CarparkFinder(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code CarparkFinder} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        carparkFinderStateList.add(new CarparkFinder(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        carparkFinderStateList.subList(currentStatePointer + 1, carparkFinderStateList.size()).clear();
    }

    /**
     * Restores the car park finder to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(carparkFinderStateList.get(currentStatePointer));
    }

    /**
     * Restores the car park finder to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(carparkFinderStateList.get(currentStatePointer));
    }

    /**
     * Compares previous and current car park finder for changes.
     */
    public int compare() {
        if (!canUndo()) {
            throw new NoComparableStateException();
        }
        int copyPointer = currentStatePointer;

        List<Carpark> currList = new ArrayList<>(carparkFinderStateList.get(copyPointer).getCarparkList());
        copyPointer--;
        List<Carpark> prevList = new ArrayList<>(carparkFinderStateList.get(copyPointer).getCarparkList());

        currList.removeAll(prevList);

        return currList.size();
    }

    /**
     * Returns true if {@code undo()} has car park finder states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has car park finder states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < carparkFinderStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedCarparkFinder)) {
            return false;
        }

        VersionedCarparkFinder otherVersionedCarparkFinder = (VersionedCarparkFinder) other;

        // state check
        return super.equals(otherVersionedCarparkFinder)
                && carparkFinderStateList.equals(otherVersionedCarparkFinder.carparkFinderStateList)
                && currentStatePointer == otherVersionedCarparkFinder.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of carparkFinderState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of carparkFinderState list, unable to redo.");
        }
    }

    /**
     * Thrown when trying to {@code compare()} but can't.
     */
    public static class NoComparableStateException extends RuntimeException {
        private NoComparableStateException() {
            super("Current state pointer at start of carparkFinderState list, unable to compare.");
        }
    }
}
