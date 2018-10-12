package seedu.learnvocabulary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code LearnVocabulary} that keeps track of its own history.
 */
public class VersionedLearnVocabulary extends LearnVocabulary {

    private final List<ReadOnlyLearnVocabulary> learnVocabularyStateList;
    private int currentStatePointer;

    public VersionedLearnVocabulary(ReadOnlyLearnVocabulary initialState) {
        super(initialState);

        learnVocabularyStateList = new ArrayList<>();
        learnVocabularyStateList.add(new LearnVocabulary(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code LearnVocabulary} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        learnVocabularyStateList.add(new LearnVocabulary(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        learnVocabularyStateList.subList(currentStatePointer + 1, learnVocabularyStateList.size()).clear();
    }

    /**
     * Restores the learnvocabulary book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(learnVocabularyStateList.get(currentStatePointer));
    }

    /**
     * Restores the learnvocabulary book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(learnVocabularyStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has learnvocabulary book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has learnvocabulary book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < learnVocabularyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedLearnVocabulary)) {
            return false;
        }

        VersionedLearnVocabulary otherVersionedLearnVocabulary = (VersionedLearnVocabulary) other;

        // state check
        return super.equals(otherVersionedLearnVocabulary)
                && learnVocabularyStateList.equals(otherVersionedLearnVocabulary.learnVocabularyStateList)
                && currentStatePointer == otherVersionedLearnVocabulary.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of learnVocabularyState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of learnVocabularyState list, unable to redo.");
        }
    }
}
