package seedu.meeting.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.meeting.testutil.TypicalPersons.AMY;
import static seedu.meeting.testutil.TypicalPersons.BOB;
import static seedu.meeting.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.meeting.testutil.MeetingBookBuilder;

public class VersionedMeetingBookTest {

    private final ReadOnlyMeetingBook meetingBookWithAmy = new MeetingBookBuilder().withPerson(AMY).build();
    private final ReadOnlyMeetingBook meetingBookWithBob = new MeetingBookBuilder().withPerson(BOB).build();
    private final ReadOnlyMeetingBook meetingBookWithCarl = new MeetingBookBuilder().withPerson(CARL).build();
    private final ReadOnlyMeetingBook emptyMeetingBook = new MeetingBookBuilder().build();

    @Test
    public void commit_singleMeetingBook_noStatesRemovedCurrentStateSaved() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(emptyMeetingBook);

        versionedMeetingBook.commit();
        assertMeetingBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyMeetingBook),
                emptyMeetingBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleMeetingBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);

        versionedMeetingBook.commit();
        assertMeetingBookListStatus(versionedMeetingBook,
                Arrays.asList(emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob),
                meetingBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleMeetingBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        versionedMeetingBook.commit();
        assertMeetingBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyMeetingBook),
                emptyMeetingBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleMeetingBookPointerAtEndOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);

        assertTrue(versionedMeetingBook.canUndo());
    }

    @Test
    public void canUndo_multipleMeetingBookPointerAtStartOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        assertTrue(versionedMeetingBook.canUndo());
    }

    @Test
    public void canUndo_singleMeetingBook_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(emptyMeetingBook);

        assertFalse(versionedMeetingBook.canUndo());
    }

    @Test
    public void canUndo_multipleMeetingBookPointerAtStartOfStateList_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        assertFalse(versionedMeetingBook.canUndo());
    }

    @Test
    public void canRedo_multipleMeetingBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        assertTrue(versionedMeetingBook.canRedo());
    }

    @Test
    public void canRedo_multipleMeetingBookPointerAtStartOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        assertTrue(versionedMeetingBook.canRedo());
    }

    @Test
    public void canRedo_singleMeetingBook_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(emptyMeetingBook);

        assertFalse(versionedMeetingBook.canRedo());
    }

    @Test
    public void canRedo_multipleMeetingBookPointerAtEndOfStateList_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);

        assertFalse(versionedMeetingBook.canRedo());
    }

    @Test
    public void undo_multipleMeetingBookPointerAtEndOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);

        versionedMeetingBook.undo();
        assertMeetingBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyMeetingBook),
                meetingBookWithAmy,
                Collections.singletonList(meetingBookWithBob));
    }

    @Test
    public void undo_multipleMeetingBookPointerNotAtStartOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        versionedMeetingBook.undo();
        assertMeetingBookListStatus(versionedMeetingBook,
                Collections.emptyList(),
                emptyMeetingBook,
                Arrays.asList(meetingBookWithAmy, meetingBookWithBob));
    }

    @Test
    public void undo_singleMeetingBook_throwsNoUndoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(emptyMeetingBook);

        assertThrows(VersionedMeetingBook.NoUndoableStateException.class, versionedMeetingBook::undo);
    }

    @Test
    public void undo_multipleMeetingBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        assertThrows(VersionedMeetingBook.NoUndoableStateException.class, versionedMeetingBook::undo);
    }

    @Test
    public void redo_multipleMeetingBookPointerNotAtEndOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        versionedMeetingBook.redo();
        assertMeetingBookListStatus(versionedMeetingBook,
                Arrays.asList(emptyMeetingBook, meetingBookWithAmy),
                meetingBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleMeetingBookPointerAtStartOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        versionedMeetingBook.redo();
        assertMeetingBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyMeetingBook),
                meetingBookWithAmy,
                Collections.singletonList(meetingBookWithBob));
    }

    @Test
    public void redo_singleMeetingBook_throwsNoRedoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(emptyMeetingBook);

        assertThrows(VersionedMeetingBook.NoRedoableStateException.class, versionedMeetingBook::redo);
    }

    @Test
    public void redo_multipleMeetingBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(
                emptyMeetingBook, meetingBookWithAmy, meetingBookWithBob);

        assertThrows(VersionedMeetingBook.NoRedoableStateException.class, versionedMeetingBook::redo);
    }

    @Test
    public void equals() {
        VersionedMeetingBook versionedMeetingBook = prepareMeetingBookList(meetingBookWithAmy, meetingBookWithBob);

        // same values -> returns true
        VersionedMeetingBook copy = prepareMeetingBookList(meetingBookWithAmy, meetingBookWithBob);
        assertTrue(versionedMeetingBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedMeetingBook.equals(versionedMeetingBook));

        // null -> returns false
        assertFalse(versionedMeetingBook.equals(null));

        // different types -> returns false
        assertFalse(versionedMeetingBook.equals(1));

        // different state list -> returns false
        VersionedMeetingBook differentMeetingBookList = prepareMeetingBookList(meetingBookWithBob, meetingBookWithCarl);
        assertFalse(versionedMeetingBook.equals(differentMeetingBookList));

        // different current pointer index -> returns false
        VersionedMeetingBook differentCurrentStatePointer = prepareMeetingBookList(
                meetingBookWithAmy, meetingBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);
        assertFalse(versionedMeetingBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedMeetingBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedMeetingBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedMeetingBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertMeetingBookListStatus(VersionedMeetingBook versionedMeetingBook,
                                             List<ReadOnlyMeetingBook> expectedStatesBeforePointer,
                                             ReadOnlyMeetingBook expectedCurrentState,
                                             List<ReadOnlyMeetingBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new MeetingBook(versionedMeetingBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedMeetingBook.canUndo()) {
            versionedMeetingBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyMeetingBook expectedMeetingBook : expectedStatesBeforePointer) {
            assertEquals(expectedMeetingBook, new MeetingBook(versionedMeetingBook));
            versionedMeetingBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyMeetingBook expectedMeetingBook : expectedStatesAfterPointer) {
            versionedMeetingBook.redo();
            assertEquals(expectedMeetingBook, new MeetingBook(versionedMeetingBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedMeetingBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedMeetingBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedMeetingBook} with the {@code meetingBookStates} added into it, and the
     * {@code VersionedMeetingBook#currentStatePointer} at the end of list.
     */
    private VersionedMeetingBook prepareMeetingBookList(ReadOnlyMeetingBook... meetingBookStates) {
        assertFalse(meetingBookStates.length == 0);

        VersionedMeetingBook versionedMeetingBook = new VersionedMeetingBook(meetingBookStates[0]);
        for (int i = 1; i < meetingBookStates.length; i++) {
            versionedMeetingBook.resetData(meetingBookStates[i]);
            versionedMeetingBook.commit();
        }

        return versionedMeetingBook;
    }

    /**
     * Shifts the {@code versionedMeetingBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedMeetingBook versionedMeetingBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedMeetingBook.undo();
        }
    }
}
