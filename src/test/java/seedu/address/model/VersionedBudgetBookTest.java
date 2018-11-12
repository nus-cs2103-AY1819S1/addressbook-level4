package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.FLOORBALL;
import static seedu.address.testutil.TypicalCcas.TRACK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BudgetBookBuilder;

//@@author ericyjw
public class VersionedBudgetBookTest {

    private final ReadOnlyBudgetBook budgetBookWithFloorball = new BudgetBookBuilder().withCca(FLOORBALL).build();
    private final ReadOnlyBudgetBook budgetBookWithTrack = new BudgetBookBuilder().withCca(TRACK).build();
    private final ReadOnlyBudgetBook budgetBookWithBasketball = new BudgetBookBuilder().withCca(BASKETBALL).build();
    private final ReadOnlyBudgetBook emptyBudgetBook = new BudgetBookBuilder().build();

    @Test
    public void commit_singleBudgetBook_noStatesRemovedCurrentStateSaved() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(emptyBudgetBook);

        versionedBudgetBook.commit();
        assertBudgetBookListStatus(versionedBudgetBook,
            Collections.singletonList(emptyBudgetBook),
            emptyBudgetBook,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleBudgetBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);

        versionedBudgetBook.commit();
        assertBudgetBookListStatus(versionedBudgetBook,
            Arrays.asList(emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack),
            budgetBookWithTrack,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleBudgetBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 2);

        versionedBudgetBook.commit();
        assertBudgetBookListStatus(versionedBudgetBook,
            Collections.singletonList(emptyBudgetBook),
            emptyBudgetBook,
            Collections.emptyList());
    }

    @Test
    public void canUndo_multipleBudgetBookPointerAtEndOfStateList_returnsTrue() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);

        assertTrue(versionedBudgetBook.canUndo());
    }

    @Test
    public void canUndo_multipleBudgetBookPointerAtStartOfStateList_returnsTrue() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 1);

        assertTrue(versionedBudgetBook.canUndo());
    }

    @Test
    public void canUndo_singleBudgetBook_returnsFalse() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(emptyBudgetBook);

        assertFalse(versionedBudgetBook.canUndo());
    }

    @Test
    public void canUndo_multipleBudgetBookPointerAtStartOfStateList_returnsFalse() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 2);

        assertFalse(versionedBudgetBook.canUndo());
    }

    @Test
    public void canRedo_multipleBudgetBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 1);

        assertTrue(versionedBudgetBook.canRedo());
    }

    @Test
    public void canRedo_multipleBudgetBookPointerAtStartOfStateList_returnsTrue() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 2);

        assertTrue(versionedBudgetBook.canRedo());
    }

    @Test
    public void canRedo_singleBudgetBook_returnsFalse() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(emptyBudgetBook);

        assertFalse(versionedBudgetBook.canRedo());
    }

    @Test
    public void canRedo_multipleBudgetBookPointerAtEndOfStateList_returnsFalse() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);

        assertFalse(versionedBudgetBook.canRedo());
    }

    @Test
    public void undo_multipleBudgetBookPointerAtEndOfStateList_success() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);

        versionedBudgetBook.undo();
        assertBudgetBookListStatus(versionedBudgetBook,
            Collections.singletonList(emptyBudgetBook),
            budgetBookWithFloorball,
            Collections.singletonList(budgetBookWithTrack));
    }

    @Test
    public void undo_multipleBudgetBookPointerNotAtStartOfStateList_success() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 1);

        versionedBudgetBook.undo();
        assertBudgetBookListStatus(versionedBudgetBook,
            Collections.emptyList(),
            emptyBudgetBook,
            Arrays.asList(budgetBookWithFloorball, budgetBookWithTrack));
    }

    @Test
    public void undo_singleBudgetBook_throwsNoUndoableStateException() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(emptyBudgetBook);

        assertThrows(VersionedBudgetBook.NoUndoableStateException.class, versionedBudgetBook::undo);
    }

    @Test
    public void undo_multipleBudgetBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 2);

        assertThrows(VersionedBudgetBook.NoUndoableStateException.class, versionedBudgetBook::undo);
    }

    @Test
    public void redo_multipleBudgetBookPointerNotAtEndOfStateList_success() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 1);

        versionedBudgetBook.redo();
        assertBudgetBookListStatus(versionedBudgetBook,
            Arrays.asList(emptyBudgetBook, budgetBookWithFloorball),
            budgetBookWithTrack,
            Collections.emptyList());
    }

    @Test
    public void redo_multipleBudgetBookPointerAtStartOfStateList_success() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 2);

        versionedBudgetBook.redo();
        assertBudgetBookListStatus(versionedBudgetBook,
            Collections.singletonList(emptyBudgetBook),
            budgetBookWithFloorball,
            Collections.singletonList(budgetBookWithTrack));
    }

    @Test
    public void redo_singleBudgetBook_throwsNoRedoableStateException() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(emptyBudgetBook);

        assertThrows(VersionedBudgetBook.NoRedoableStateException.class, versionedBudgetBook::redo);
    }

    @Test
    public void redo_multipleBudgetBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(
            emptyBudgetBook, budgetBookWithFloorball, budgetBookWithTrack);

        assertThrows(VersionedBudgetBook.NoRedoableStateException.class, versionedBudgetBook::redo);
    }

    @Test
    public void equals() {
        VersionedBudgetBook versionedBudgetBook = prepareBudgetBookList(budgetBookWithFloorball, budgetBookWithTrack);

        // same values -> returns true
        VersionedBudgetBook copy = prepareBudgetBookList(budgetBookWithFloorball, budgetBookWithTrack);
        assertTrue(versionedBudgetBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedBudgetBook.equals(versionedBudgetBook));

        // null -> returns false
        assertFalse(versionedBudgetBook.equals(null));

        // different types -> returns false
        assertFalse(versionedBudgetBook.equals(1));

        // different state list -> returns false
        VersionedBudgetBook differentBudgetBookList = prepareBudgetBookList(budgetBookWithTrack,
            budgetBookWithBasketball);
        assertFalse(versionedBudgetBook.equals(differentBudgetBookList));

        // different current pointer index -> returns false
        VersionedBudgetBook differentCurrentStatePointer = prepareBudgetBookList(
            budgetBookWithFloorball, budgetBookWithTrack);
        shiftCurrentStatePointerLeftwards(versionedBudgetBook, 1);
        assertFalse(versionedBudgetBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedBudgetBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedBudgetBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedBudgetBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertBudgetBookListStatus(VersionedBudgetBook versionedBudgetBook,
                                            List<ReadOnlyBudgetBook> expectedStatesBeforePointer,
                                            ReadOnlyBudgetBook expectedCurrentState,
                                            List<ReadOnlyBudgetBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new BudgetBook(versionedBudgetBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedBudgetBook.canUndo()) {
            versionedBudgetBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyBudgetBook expectedBudgetBook : expectedStatesBeforePointer) {
            assertEquals(expectedBudgetBook, new BudgetBook(versionedBudgetBook));
            versionedBudgetBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyBudgetBook expectedBudgetBook : expectedStatesAfterPointer) {
            versionedBudgetBook.redo();
            assertEquals(expectedBudgetBook, new BudgetBook(versionedBudgetBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedBudgetBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedBudgetBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedBudgetBook} with the {@code budgetBookStates} added into it, and the
     * {@code VersionedBudgetBook#currentStatePointer} at the end of list.
     */
    private VersionedBudgetBook prepareBudgetBookList(ReadOnlyBudgetBook... budgetBookStates) {
        assertFalse(budgetBookStates.length == 0);

        VersionedBudgetBook versionedBudgetBook = new VersionedBudgetBook(budgetBookStates[0]);
        for (int i = 1; i < budgetBookStates.length; i++) {
            versionedBudgetBook.resetData(budgetBookStates[i]);
            versionedBudgetBook.commit();
        }

        return versionedBudgetBook;
    }

    /**
     * Shifts the {@code versionedBudgetBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedBudgetBook versionedBudgetBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedBudgetBook.undo();
        }
    }
}
