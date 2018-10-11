package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BENSON;
import static seedu.address.testutil.TypicalOrders.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OrderBookBuilder;

public class VersionedOrderBookTest {

    private final ReadOnlyOrderBook orderBookWithAlice = new OrderBookBuilder().withOrder(ALICE).build();
    private final ReadOnlyOrderBook orderBookWithBob = new OrderBookBuilder().withOrder(BENSON).build();
    private final ReadOnlyOrderBook orderBookWithCarl = new OrderBookBuilder().withOrder(CARL).build();
    private final ReadOnlyOrderBook emptyOrderBook = new OrderBookBuilder().build();

    @Test
    public void commit_singleOrderBook_noStatesRemovedCurrentStateSaved() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(emptyOrderBook);

        versionedOrderBook.commit();
        assertOrderBookListStatus(versionedOrderBook,
                Collections.singletonList(emptyOrderBook),
                emptyOrderBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleOrderBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);

        versionedOrderBook.commit();
        assertOrderBookListStatus(versionedOrderBook,
                Arrays.asList(emptyOrderBook, orderBookWithAlice, orderBookWithBob),
                orderBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleOrderBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 2);

        versionedOrderBook.commit();
        assertOrderBookListStatus(versionedOrderBook,
                Collections.singletonList(emptyOrderBook),
                emptyOrderBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleOrderBookPointerAtEndOfStateList_returnsTrue() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);

        assertTrue(versionedOrderBook.canUndo());
    }

    @Test
    public void canUndo_multipleOrderBookPointerAtStartOfStateList_returnsTrue() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 1);

        assertTrue(versionedOrderBook.canUndo());
    }

    @Test
    public void canUndo_singleOrderBook_returnsFalse() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(emptyOrderBook);

        assertFalse(versionedOrderBook.canUndo());
    }

    @Test
    public void canUndo_multipleOrderBookPointerAtStartOfStateList_returnsFalse() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 2);

        assertFalse(versionedOrderBook.canUndo());
    }

    @Test
    public void canRedo_multipleOrderBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 1);

        assertTrue(versionedOrderBook.canRedo());
    }

    @Test
    public void canRedo_multipleOrderBookPointerAtStartOfStateList_returnsTrue() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 2);

        assertTrue(versionedOrderBook.canRedo());
    }

    @Test
    public void canRedo_singleOrderBook_returnsFalse() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(emptyOrderBook);

        assertFalse(versionedOrderBook.canRedo());
    }

    @Test
    public void canRedo_multipleOrderBookPointerAtEndOfStateList_returnsFalse() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);

        assertFalse(versionedOrderBook.canRedo());
    }

    @Test
    public void undo_multipleOrderBookPointerAtEndOfStateList_success() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);

        versionedOrderBook.undo();
        assertOrderBookListStatus(versionedOrderBook,
                Collections.singletonList(emptyOrderBook),
                orderBookWithAlice,
                Collections.singletonList(orderBookWithBob));
    }

    @Test
    public void undo_multipleOrderBookPointerNotAtStartOfStateList_success() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 1);

        versionedOrderBook.undo();
        assertOrderBookListStatus(versionedOrderBook,
                Collections.emptyList(),
                emptyOrderBook,
                Arrays.asList(orderBookWithAlice, orderBookWithBob));
    }

    @Test
    public void undo_singleOrderBook_throwsNoUndoableStateException() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(emptyOrderBook);

        assertThrows(VersionedOrderBook.NoUndoableStateException.class, versionedOrderBook::undo);
    }

    @Test
    public void undo_multipleOrderBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 2);

        assertThrows(VersionedOrderBook.NoUndoableStateException.class, versionedOrderBook::undo);
    }

    @Test
    public void redo_multipleOrderBookPointerNotAtEndOfStateList_success() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 1);

        versionedOrderBook.redo();
        assertOrderBookListStatus(versionedOrderBook,
                Arrays.asList(emptyOrderBook, orderBookWithAlice),
                orderBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleOrderBookPointerAtStartOfStateList_success() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 2);

        versionedOrderBook.redo();
        assertOrderBookListStatus(versionedOrderBook,
                Collections.singletonList(emptyOrderBook),
                orderBookWithAlice,
                Collections.singletonList(orderBookWithBob));
    }

    @Test
    public void redo_singleOrderBook_throwsNoRedoableStateException() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(emptyOrderBook);

        assertThrows(VersionedOrderBook.NoRedoableStateException.class, versionedOrderBook::redo);
    }

    @Test
    public void redo_multipleOrderBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(
                emptyOrderBook, orderBookWithAlice, orderBookWithBob);

        assertThrows(VersionedOrderBook.NoRedoableStateException.class, versionedOrderBook::redo);
    }

    @Test
    public void equals() {
        VersionedOrderBook versionedOrderBook = prepareOrderBookList(orderBookWithAlice, orderBookWithBob);

        // same values -> returns true
        VersionedOrderBook copy = prepareOrderBookList(orderBookWithAlice, orderBookWithBob);
        assertTrue(versionedOrderBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedOrderBook.equals(versionedOrderBook));

        // null -> returns false
        assertFalse(versionedOrderBook.equals(null));

        // different types -> returns false
        assertFalse(versionedOrderBook.equals(1));

        // different state list -> returns false
        VersionedOrderBook differentOrderBookList = prepareOrderBookList(orderBookWithBob, orderBookWithCarl);
        assertFalse(versionedOrderBook.equals(differentOrderBookList));

        // different current pointer index -> returns false
        VersionedOrderBook differentCurrentStatePointer = prepareOrderBookList(
                orderBookWithAlice, orderBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedOrderBook, 1);
        assertFalse(versionedOrderBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedOrderBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertOrderBookListStatus(VersionedOrderBook versionedOrderBook,
                                           List<ReadOnlyOrderBook> expectedStatesBeforePointer,
                                           ReadOnlyOrderBook expectedCurrentState,
                                           List<ReadOnlyOrderBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new OrderBook(versionedOrderBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedOrderBook.canUndo()) {
            versionedOrderBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyOrderBook expectedOrderBook : expectedStatesBeforePointer) {
            assertEquals(expectedOrderBook, new OrderBook(versionedOrderBook));
            versionedOrderBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyOrderBook expectedOrderBook : expectedStatesAfterPointer) {
            versionedOrderBook.redo();
            assertEquals(expectedOrderBook, new OrderBook(versionedOrderBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedOrderBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedOrderBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedOrderBook} with the {@code orderBookStates} added into it, and the
     * {@code VersionedOrderBook#currentStatePointer} at the end of list.
     */
    private VersionedOrderBook prepareOrderBookList(ReadOnlyOrderBook... orderBookStates) {
        assertFalse(orderBookStates.length == 0);

        VersionedOrderBook versionedOrderBook = new VersionedOrderBook(orderBookStates[0]);
        for (int i = 1; i < orderBookStates.length; i++) {
            versionedOrderBook.resetData(orderBookStates[i]);
            versionedOrderBook.commit();
        }

        return versionedOrderBook;
    }

    /**
     * Shifts the {@code versionedOrderBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedOrderBook versionedOrderBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedOrderBook.undo();
        }
    }
}
