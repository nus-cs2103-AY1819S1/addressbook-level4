package seedu.restaurant.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.restaurant.testutil.TypicalPersons.AMY;
import static seedu.restaurant.testutil.TypicalPersons.BOB;
import static seedu.restaurant.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.testutil.RestaurantBookBuilder;

public class VersionedRestaurantBookTest {

    private final ReadOnlyRestaurantBook restaurantBookWithAmy = new RestaurantBookBuilder().withPerson(AMY).build();
    private final ReadOnlyRestaurantBook restaurantBookWithBob = new RestaurantBookBuilder().withPerson(BOB).build();
    private final ReadOnlyRestaurantBook restaurantBookWithCarl = new RestaurantBookBuilder().withPerson(CARL).build();
    private final ReadOnlyRestaurantBook emptyRestaurantBook = new RestaurantBookBuilder().build();

    @Test
    public void commit_singleRestaurantBook_noStatesRemovedCurrentStateSaved() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(emptyRestaurantBook);

        versionedRestaurantBook.commit();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Collections.singletonList(emptyRestaurantBook),
                emptyRestaurantBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRestaurantBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);

        versionedRestaurantBook.commit();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Arrays.asList(emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob),
                restaurantBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRestaurantBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 2);

        versionedRestaurantBook.commit();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Collections.singletonList(emptyRestaurantBook),
                emptyRestaurantBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleRestaurantBookPointerAtEndOfStateList_returnsTrue() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);

        assertTrue(versionedRestaurantBook.canUndo());
    }

    @Test
    public void canUndo_multipleRestaurantBookPointerAtStartOfStateList_returnsTrue() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 1);

        assertTrue(versionedRestaurantBook.canUndo());
    }

    @Test
    public void canUndo_singleRestaurantBook_returnsFalse() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(emptyRestaurantBook);

        assertFalse(versionedRestaurantBook.canUndo());
    }

    @Test
    public void canUndo_multipleRestaurantBookPointerAtStartOfStateList_returnsFalse() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 2);

        assertFalse(versionedRestaurantBook.canUndo());
    }

    @Test
    public void canRedo_multipleRestaurantBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 1);

        assertTrue(versionedRestaurantBook.canRedo());
    }

    @Test
    public void canRedo_multipleRestaurantBookPointerAtStartOfStateList_returnsTrue() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 2);

        assertTrue(versionedRestaurantBook.canRedo());
    }

    @Test
    public void canRedo_singleRestaurantBook_returnsFalse() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(emptyRestaurantBook);

        assertFalse(versionedRestaurantBook.canRedo());
    }

    @Test
    public void canRedo_multipleRestaurantBookPointerAtEndOfStateList_returnsFalse() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);

        assertFalse(versionedRestaurantBook.canRedo());
    }

    @Test
    public void undo_multipleRestaurantBookPointerAtEndOfStateList_success() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);

        versionedRestaurantBook.undo();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Collections.singletonList(emptyRestaurantBook),
                restaurantBookWithAmy,
                Collections.singletonList(restaurantBookWithBob));
    }

    @Test
    public void undo_multipleRestaurantBookPointerNotAtStartOfStateList_success() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 1);

        versionedRestaurantBook.undo();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Collections.emptyList(),
                emptyRestaurantBook,
                Arrays.asList(restaurantBookWithAmy, restaurantBookWithBob));
    }

    @Test
    public void undo_singleRestaurantBook_throwsNoUndoableStateException() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(emptyRestaurantBook);

        assertThrows(VersionedRestaurantBook.NoUndoableStateException.class, versionedRestaurantBook::undo);
    }

    @Test
    public void undo_multipleRestaurantBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 2);

        assertThrows(VersionedRestaurantBook.NoUndoableStateException.class, versionedRestaurantBook::undo);
    }

    @Test
    public void redo_multipleRestaurantBookPointerNotAtEndOfStateList_success() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 1);

        versionedRestaurantBook.redo();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Arrays.asList(emptyRestaurantBook, restaurantBookWithAmy),
                restaurantBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleRestaurantBookPointerAtStartOfStateList_success() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 2);

        versionedRestaurantBook.redo();
        assertRestaurantBookListStatus(versionedRestaurantBook,
                Collections.singletonList(emptyRestaurantBook),
                restaurantBookWithAmy,
                Collections.singletonList(restaurantBookWithBob));
    }

    @Test
    public void redo_singleRestaurantBook_throwsNoRedoableStateException() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(emptyRestaurantBook);

        assertThrows(VersionedRestaurantBook.NoRedoableStateException.class, versionedRestaurantBook::redo);
    }

    @Test
    public void redo_multipleRestaurantBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(
                emptyRestaurantBook, restaurantBookWithAmy, restaurantBookWithBob);

        assertThrows(VersionedRestaurantBook.NoRedoableStateException.class, versionedRestaurantBook::redo);
    }

    //@@author AZhiKai
    @Test
    public void reset() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(restaurantBookWithBob);

        versionedRestaurantBook.commit();
        assertTrue(versionedRestaurantBook.canUndo());
        assertFalse(versionedRestaurantBook.canRedo());

        versionedRestaurantBook.reset();

        assertFalse(versionedRestaurantBook.canUndo());
        assertFalse(versionedRestaurantBook.canRedo());
    }

    @Test
    public void equals() {
        VersionedRestaurantBook versionedRestaurantBook = prepareRestaurantBookList(restaurantBookWithAmy,
                restaurantBookWithBob);

        // same values -> returns true
        VersionedRestaurantBook copy = prepareRestaurantBookList(restaurantBookWithAmy, restaurantBookWithBob);
        assertTrue(versionedRestaurantBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedRestaurantBook.equals(versionedRestaurantBook));

        // null -> returns false
        assertFalse(versionedRestaurantBook.equals(null));

        // different types -> returns false
        assertFalse(versionedRestaurantBook.equals(1));

        // different state list -> returns false
        VersionedRestaurantBook differentRestaurantBookList = prepareRestaurantBookList(restaurantBookWithBob,
                restaurantBookWithCarl);
        assertFalse(versionedRestaurantBook.equals(differentRestaurantBookList));

        // different current pointer index -> returns false
        VersionedRestaurantBook differentCurrentStatePointer = prepareRestaurantBookList(
                restaurantBookWithAmy, restaurantBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedRestaurantBook, 1);
        assertFalse(versionedRestaurantBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedRestaurantBook} is currently pointing at {@code expectedCurrentState}, states before
     * {@code versionedRestaurantBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer}, and states
     * after {@code versionedRestaurantBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertRestaurantBookListStatus(VersionedRestaurantBook versionedRestaurantBook,
            List<ReadOnlyRestaurantBook> expectedStatesBeforePointer,
            ReadOnlyRestaurantBook expectedCurrentState,
            List<ReadOnlyRestaurantBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new RestaurantBook(versionedRestaurantBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedRestaurantBook.canUndo()) {
            versionedRestaurantBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyRestaurantBook expectedRestaurantBook : expectedStatesBeforePointer) {
            assertEquals(expectedRestaurantBook, new RestaurantBook(versionedRestaurantBook));
            versionedRestaurantBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyRestaurantBook expectedRestaurantBook : expectedStatesAfterPointer) {
            versionedRestaurantBook.redo();
            assertEquals(expectedRestaurantBook, new RestaurantBook(versionedRestaurantBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedRestaurantBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedRestaurantBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedRestaurantBook} with the {@code restaurantBookStates} added into it, and
     * the {@code VersionedRestaurantBook#currentStatePointer} at the end of list.
     */
    private VersionedRestaurantBook prepareRestaurantBookList(ReadOnlyRestaurantBook... restaurantBookStates) {
        assertFalse(restaurantBookStates.length == 0);

        VersionedRestaurantBook versionedRestaurantBook = new VersionedRestaurantBook(restaurantBookStates[0]);
        for (int i = 1; i < restaurantBookStates.length; i++) {
            versionedRestaurantBook.resetData(restaurantBookStates[i]);
            versionedRestaurantBook.commit();
        }

        return versionedRestaurantBook;
    }

    /**
     * Shifts the {@code versionedRestaurantBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedRestaurantBook versionedRestaurantBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedRestaurantBook.undo();
        }
    }
}
