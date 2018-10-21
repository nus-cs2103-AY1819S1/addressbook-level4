package seedu.clinicio.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ClinicIo} that keeps track of its own history.
 */
public class VersionedClinicIo extends ClinicIo {

    private final List<ReadOnlyClinicIo> clinicIoStateList;
    private int currentStatePointer;

    public VersionedClinicIo(ReadOnlyClinicIo initialState) {
        super(initialState);

        clinicIoStateList = new ArrayList<>();
        clinicIoStateList.add(new ClinicIo(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ClinicIo} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        clinicIoStateList.add(new ClinicIo(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        clinicIoStateList.subList(currentStatePointer + 1, clinicIoStateList.size()).clear();
    }

    /**
     * Restores the ClinicIO to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(clinicIoStateList.get(currentStatePointer));
    }

    /**
     * Restores the ClinicIO to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(clinicIoStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has ClinicIO states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has ClinicIO states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < clinicIoStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedClinicIo)) {
            return false;
        }

        VersionedClinicIo otherVersionedClinicIo = (VersionedClinicIo) other;

        // state check
        return super.equals(otherVersionedClinicIo)
                && clinicIoStateList.equals(otherVersionedClinicIo.clinicIoStateList)
                && currentStatePointer == otherVersionedClinicIo.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of clinicIoState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of clinicIoState list, unable to redo.");
        }
    }
}
