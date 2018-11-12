package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BudgetBook} that keeps track of its own history.
 */
public class VersionedBudgetBook extends BudgetBook {

    private final List<ReadOnlyBudgetBook> budgetBookStateList;
    private int currentStatePointer;

    public VersionedBudgetBook(ReadOnlyBudgetBook initialState) {
        super(initialState);

        budgetBookStateList = new ArrayList<>();
        budgetBookStateList.add(new BudgetBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code BudgetBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        budgetBookStateList.add(new BudgetBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        budgetBookStateList.subList(currentStatePointer + 1, budgetBookStateList.size()).clear();
    }

    /**
     * Restores the budget book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(budgetBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the budget book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(budgetBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has budget book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has budget book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < budgetBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedBudgetBook)) {
            return false;
        }

        VersionedBudgetBook otherVersionedBudgetBook = (VersionedBudgetBook) other;

        // state check
        return super.equals(otherVersionedBudgetBook)
            && budgetBookStateList.equals(otherVersionedBudgetBook.budgetBookStateList)
            && currentStatePointer == otherVersionedBudgetBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of budgetBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of budgetBookState list, unable to redo.");
        }
    }
}

