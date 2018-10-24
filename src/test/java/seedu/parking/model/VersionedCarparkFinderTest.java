package seedu.parking.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.parking.testutil.TypicalCarparks.CHARLIE;
import static seedu.parking.testutil.TypicalCarparks.JULIETT;
import static seedu.parking.testutil.TypicalCarparks.KILO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.parking.testutil.AddressBookBuilder;

public class VersionedAddressBookTest {

    private final ReadOnlyCarparkFinder addressBookWithJuliett = new AddressBookBuilder().withCarpark(JULIETT).build();
    private final ReadOnlyCarparkFinder addressBookWithKilo = new AddressBookBuilder().withCarpark(KILO).build();
    private final ReadOnlyCarparkFinder addressBookWithCharlie = new AddressBookBuilder().withCarpark(CHARLIE).build();
    private final ReadOnlyCarparkFinder emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithJuliett, addressBookWithKilo),
                addressBookWithKilo,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithJuliett,
                Collections.singletonList(addressBookWithKilo));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithJuliett, addressBookWithKilo));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedCarparkFinder.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertThrows(VersionedCarparkFinder.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithJuliett),
                addressBookWithKilo,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithJuliett,
                Collections.singletonList(addressBookWithKilo));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedCarparkFinder.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithJuliett, addressBookWithKilo);

        assertThrows(VersionedCarparkFinder.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void equals() {
        VersionedCarparkFinder versionedAddressBook = prepareAddressBookList(addressBookWithJuliett, addressBookWithKilo);

        // same values -> returns true
        VersionedCarparkFinder copy = prepareAddressBookList(addressBookWithJuliett, addressBookWithKilo);
        assertTrue(versionedAddressBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // different state list -> returns false
        VersionedCarparkFinder differentAddressBookList = prepareAddressBookList(
                addressBookWithKilo, addressBookWithCharlie);
        assertFalse(versionedAddressBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedCarparkFinder differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithJuliett, addressBookWithKilo);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedCarparkFinder versionedAddressBook,
                                             List<ReadOnlyCarparkFinder> expectedStatesBeforePointer,
                                             ReadOnlyCarparkFinder expectedCurrentState,
                                             List<ReadOnlyCarparkFinder> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new CarparkFinder(versionedAddressBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyCarparkFinder expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new CarparkFinder(versionedAddressBook));
            versionedAddressBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyCarparkFinder expectedAddressBook : expectedStatesAfterPointer) {
            versionedAddressBook.redo();
            assertEquals(expectedAddressBook, new CarparkFinder(versionedAddressBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAddressBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAddressBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedCarparkFinder} with the {@code addressBookStates} added into it, and the
     * {@code VersionedCarparkFinder#currentStatePointer} at the end of list.
     */
    private VersionedCarparkFinder prepareAddressBookList(ReadOnlyCarparkFinder... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedCarparkFinder versionedAddressBook = new VersionedCarparkFinder(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedAddressBook.resetData(addressBookStates[i]);
            versionedAddressBook.commit();
        }

        return versionedAddressBook;
    }

    /**
     * Shifts the {@code versionedAddressBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedCarparkFinder versionedAddressBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAddressBook.undo();
        }
    }
}
