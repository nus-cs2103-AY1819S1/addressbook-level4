package seedu.meeting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code MeetingBook} that keeps track of its own history.
 */
public class VersionedMeetingBook extends MeetingBook {

    private final List<ReadOnlyMeetingBook> meetingBookStateList;
    private int currentStatePointer;

    public VersionedMeetingBook(ReadOnlyMeetingBook initialState) {
        super(initialState);

        meetingBookStateList = new ArrayList<>();
        meetingBookStateList.add(new MeetingBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code MeetingBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        meetingBookStateList.add(new MeetingBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        meetingBookStateList.subList(currentStatePointer + 1, meetingBookStateList.size()).clear();
    }

    /**
     * Restores the MeetingBook to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(meetingBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the MeetingBook to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(meetingBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has MeetingBook states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has MeetingBook states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < meetingBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedMeetingBook)) {
            return false;
        }

        VersionedMeetingBook otherVersionedMeetingBook = (VersionedMeetingBook) other;

        // state check
        return super.equals(otherVersionedMeetingBook)
                && meetingBookStateList.equals(otherVersionedMeetingBook.meetingBookStateList)
                && currentStatePointer == otherVersionedMeetingBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of meetingBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of meetingBookState list, unable to redo.");
        }
    }
}
