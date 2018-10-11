package seedu.address.model.route;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code RouteList} that keeps track of its own history.
 */
public class VersionedRouteList extends RouteList {

    private final List<ReadOnlyRouteList> routeBookStateList;
    private int currentStatePointer;

    public VersionedRouteList(ReadOnlyRouteList initialState) {
        super(initialState);

        routeBookStateList = new ArrayList<>();
        routeBookStateList.add(new RouteList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code RouteList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        routeBookStateList.add(new RouteList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        routeBookStateList.subList(currentStatePointer + 1, routeBookStateList.size()).clear();
    }

    /**
     * Restores the route book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(routeBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the route book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(routeBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has route book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has route book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < routeBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedRouteList)) {
            return false;
        }

        VersionedRouteList otherVersionedRouteList = (VersionedRouteList) other;

        // state check
        return super.equals(otherVersionedRouteList)
                && routeBookStateList.equals(otherVersionedRouteList.routeBookStateList)
                && currentStatePointer == otherVersionedRouteList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of routeBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of routeBookState list, unable to redo.");
        }
    }
}
