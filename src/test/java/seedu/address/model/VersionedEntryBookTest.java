package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEntrys.AWARD_WITH_NO_ENTRYINFO_NO_DESC;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EntryBookBuilder;

public class VersionedEntryBookTest {

    private final ReadOnlyEntryBook entryBookWithFacebook = new EntryBookBuilder().withEntry(WORK_FACEBOOK).build();
    private final ReadOnlyEntryBook entryBookWithNus = new EntryBookBuilder().withEntry(NUS_EDUCATION).build();
    private final ReadOnlyEntryBook entryBookWithMinorEntry = new EntryBookBuilder()
            .withEntry(AWARD_WITH_NO_ENTRYINFO_NO_DESC).build();
    private final ReadOnlyEntryBook emptyEntryBook = new EntryBookBuilder().build();

    @Test
    public void commit_singleeEntryBook_noStatesRemovedCurrentStateSaved() {
        // simulating addition of 1 empty eEntrybook
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(emptyEntryBook);

        versionedEntryBook.commit();
        assertEntryBookListStatus(versionedEntryBook,
                Collections.singletonList(emptyEntryBook),
                emptyEntryBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEntryBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        // simulating 3 commits of three entrybook,
        // where each entrybook only contains one entry, i.e add, edit and edit procedure
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        versionedEntryBook.commit();
        assertEntryBookListStatus(versionedEntryBook,
                Arrays.asList(emptyEntryBook, entryBookWithFacebook, entryBookWithNus),
                entryBookWithNus,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEntryBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        // undo twice to the 1st entrybook so that pointer not at the end of list
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 2);


        versionedEntryBook.commit();
        assertEntryBookListStatus(versionedEntryBook,
                Collections.singletonList(emptyEntryBook),
                emptyEntryBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleEntryBookPointerAtEndOfStateList_returnsTrue() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);

        assertTrue(versionedEntryBook.canUndo());
    }

    @Test
    public void canUndo_multipleEntryBookPointerAtStartOfStateList_returnsTrue() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 1);

        assertTrue(versionedEntryBook.canUndo());
    }

    @Test
    public void canUndo_singleEntryBook_returnsFalse() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(emptyEntryBook);

        assertFalse(versionedEntryBook.canUndo());
    }

    @Test
    public void canUndo_multipleEntryBookPointerAtStartOfStateList_returnsFalse() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 2);

        assertFalse(versionedEntryBook.canUndo());
    }

    @Test
    public void canRedo_multipleEntryBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 1);

        assertTrue(versionedEntryBook.canRedo());
    }

    @Test
    public void canRedo_multipleEntryBookPointerAtStartOfStateList_returnsTrue() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 2);

        assertTrue(versionedEntryBook.canRedo());
    }

    @Test
    public void canRedo_singleEntryBook_returnsFalse() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(emptyEntryBook);

        assertFalse(versionedEntryBook.canRedo());
    }

    @Test
    public void canRedo_multipleEntryBookPointerAtEndOfStateList_returnsFalse() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);

        assertFalse(versionedEntryBook.canRedo());
    }

    @Test
    public void undo_multipleEntryBookPointerAtEndOfStateList_success() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);

        versionedEntryBook.undo();
        assertEntryBookListStatus(versionedEntryBook,
                Collections.singletonList(emptyEntryBook),
                entryBookWithFacebook,
                Collections.singletonList(entryBookWithNus));
    }

    @Test
    public void undo_multipleEntryBookPointerNotAtStartOfStateList_success() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 1);

        versionedEntryBook.undo();
        assertEntryBookListStatus(versionedEntryBook,
                Collections.emptyList(),
                emptyEntryBook,
                Arrays.asList(entryBookWithFacebook, entryBookWithNus));
    }

    @Test
    public void undo_singleEntryBook_throwsNoUndoableStateException() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(emptyEntryBook);

        assertThrows(VersionedEntryBook.NoUndoableStateException.class, versionedEntryBook::undo);
    }

    @Test
    public void undo_multipleEntryBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 2);

        assertThrows(VersionedEntryBook.NoUndoableStateException.class, versionedEntryBook::undo);
    }

    @Test
    public void redo_multipleEntryBookPointerNotAtEndOfStateList_success() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 1);

        versionedEntryBook.redo();
        assertEntryBookListStatus(versionedEntryBook,
                Arrays.asList(emptyEntryBook, entryBookWithFacebook),
                entryBookWithNus,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleeEntryBookPointerAtStartOfStateList_success() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 2);

        versionedEntryBook.redo();
        assertEntryBookListStatus(versionedEntryBook,
                Collections.singletonList(emptyEntryBook),
                entryBookWithFacebook,
                Collections.singletonList(entryBookWithNus));
    }

    @Test
    public void redo_singleeEntryBook_throwsNoRedoableStateException() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(emptyEntryBook);

        assertThrows(VersionedEntryBook.NoRedoableStateException.class, versionedEntryBook::redo);
    }

    @Test
    public void redo_multipleeEntryBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(
                emptyEntryBook, entryBookWithFacebook, entryBookWithNus);

        assertThrows(VersionedEntryBook.NoRedoableStateException.class, versionedEntryBook::redo);
    }

    @Test
    public void equals() {
        VersionedEntryBook versionedEntryBook = prepareEntryBookList(entryBookWithFacebook, entryBookWithNus);

        // same values -> returns true
        VersionedEntryBook copy = prepareEntryBookList(entryBookWithFacebook, entryBookWithNus);
        assertTrue(versionedEntryBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedEntryBook.equals(versionedEntryBook));

        // null -> returns false
        assertFalse(versionedEntryBook.equals(null));

        // different types -> returns false
        assertFalse(versionedEntryBook.equals(1));

        // different state list -> returns false
        VersionedEntryBook differentEntryBookList = prepareEntryBookList(entryBookWithMinorEntry, entryBookWithNus);
        assertFalse(versionedEntryBook.equals(differentEntryBookList));

        // different current pointer index -> returns false
        VersionedEntryBook differentCurrentStatePointer = prepareEntryBookList(entryBookWithFacebook, entryBookWithNus);
        shiftCurrentStatePointerLeftwards(versionedEntryBook, 1);
        assertFalse(versionedEntryBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedEntryBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedEntryBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedEntryBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertEntryBookListStatus(VersionedEntryBook versionedEntryBook,
                                             List<ReadOnlyEntryBook> expectedStatesBeforePointer,
                                             ReadOnlyEntryBook expectedCurrentState,
                                             List<ReadOnlyEntryBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new EntryBook(versionedEntryBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedEntryBook.canUndo()) {
            versionedEntryBook.undo();
        }

        // check states before pointer are correct --> here the state refers to currentEntryBook
        for (ReadOnlyEntryBook expectedEntryBook : expectedStatesBeforePointer) {
            assertEquals(expectedEntryBook, new EntryBook(versionedEntryBook));
            versionedEntryBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyEntryBook expectedEntryBook : expectedStatesAfterPointer) {
            versionedEntryBook.redo();
            assertEquals(expectedEntryBook, new EntryBook(versionedEntryBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedEntryBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedEntryBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedEntryBook} with the {@code entryBookStates} added into it, and the
     * {@code VersionedEntryBook#currentStatePointer} at the end of list.
     */
    private VersionedEntryBook prepareEntryBookList(ReadOnlyEntryBook... entryBookStates) {
        assertFalse(entryBookStates.length == 0);

        VersionedEntryBook versionedEntryBook = new VersionedEntryBook(entryBookStates[0]);
        for (int i = 1; i < entryBookStates.length; i++) {
            versionedEntryBook.resetData(entryBookStates[i]);
            versionedEntryBook.commit();
        }

        return versionedEntryBook;
    }

    /**
     * Shifts the {@code versionedEntryBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedEntryBook versionedEntryBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedEntryBook.undo();
        }
    }
}
