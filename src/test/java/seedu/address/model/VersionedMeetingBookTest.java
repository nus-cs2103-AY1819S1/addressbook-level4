package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.MeetingBookBuilder;

public class VersionedMeetingBookTest {

    private final ReadOnlyMeetingBook addressBookWithAmy = new MeetingBookBuilder().withPerson(AMY).build();
    private final ReadOnlyMeetingBook addressBookWithBob = new MeetingBookBuilder().withPerson(BOB).build();
    private final ReadOnlyMeetingBook addressBookWithCarl = new MeetingBookBuilder().withPerson(CARL).build();
    private final ReadOnlyMeetingBook emptyAddressBook = new MeetingBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(emptyAddressBook);

        versionedMeetingBook.commit();
        assertAddressBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedMeetingBook.commit();
        assertAddressBookListStatus(versionedMeetingBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        versionedMeetingBook.commit();
        assertAddressBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedMeetingBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        assertTrue(versionedMeetingBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedMeetingBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        assertFalse(versionedMeetingBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        assertTrue(versionedMeetingBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        assertTrue(versionedMeetingBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedMeetingBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedMeetingBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedMeetingBook.undo();
        assertAddressBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        versionedMeetingBook.undo();
        assertAddressBookListStatus(versionedMeetingBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedMeetingBook.NoUndoableStateException.class, versionedMeetingBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        assertThrows(VersionedMeetingBook.NoUndoableStateException.class, versionedMeetingBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);

        versionedMeetingBook.redo();
        assertAddressBookListStatus(versionedMeetingBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 2);

        versionedMeetingBook.redo();
        assertAddressBookListStatus(versionedMeetingBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedMeetingBook.NoRedoableStateException.class, versionedMeetingBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedMeetingBook.NoRedoableStateException.class, versionedMeetingBook::redo);
    }

    @Test
    public void equals() {
        VersionedMeetingBook versionedMeetingBook = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedMeetingBook copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedMeetingBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedMeetingBook.equals(versionedMeetingBook));

        // null -> returns false
        assertFalse(versionedMeetingBook.equals(null));

        // different types -> returns false
        assertFalse(versionedMeetingBook.equals(1));

        // different state list -> returns false
        VersionedMeetingBook differentAddressBookList = prepareAddressBookList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedMeetingBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedMeetingBook differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedMeetingBook, 1);
        assertFalse(versionedMeetingBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedMeetingBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedMeetingBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedMeetingBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedMeetingBook versionedMeetingBook,
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
        for (ReadOnlyMeetingBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new MeetingBook(versionedMeetingBook));
            versionedMeetingBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyMeetingBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedMeetingBook.redo();
            assertEquals(expectedAddressBook, new MeetingBook(versionedMeetingBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedMeetingBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedMeetingBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedMeetingBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedMeetingBook#currentStatePointer} at the end of list.
     */
    private VersionedMeetingBook prepareAddressBookList(ReadOnlyMeetingBook... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedMeetingBook versionedMeetingBook = new VersionedMeetingBook(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedMeetingBook.resetData(addressBookStates[i]);
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
