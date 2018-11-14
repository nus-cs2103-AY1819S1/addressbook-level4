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

import seedu.parking.testutil.CarparkFinderBuilder;

public class VersionedCarparkFinderTest {

    private final ReadOnlyCarparkFinder carparkFinderWithJuliett = new CarparkFinderBuilder()
            .withCarpark(JULIETT).build();
    private final ReadOnlyCarparkFinder carparkFinderWithKilo = new CarparkFinderBuilder()
            .withCarpark(KILO).build();
    private final ReadOnlyCarparkFinder carparkFinderWithCharlie = new CarparkFinderBuilder()
            .withCarpark(CHARLIE).build();
    private final ReadOnlyCarparkFinder emptyCarparkFinder = new CarparkFinderBuilder().build();

    @Test
    public void commit_singleCarparkFinder_noStatesRemovedCurrentStateSaved() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(emptyCarparkFinder);

        versionedCarparkFinder.commit();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Collections.singletonList(emptyCarparkFinder),
                emptyCarparkFinder,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleCarparkFinderPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);

        versionedCarparkFinder.commit();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Arrays.asList(emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo),
                carparkFinderWithKilo,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleCarparkFinderPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        versionedCarparkFinder.commit();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Collections.singletonList(emptyCarparkFinder),
                emptyCarparkFinder,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleCarparkFinderPointerAtEndOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);

        assertTrue(versionedCarparkFinder.canUndo());
    }

    @Test
    public void canUndo_multipleCarparkFinderPointerAtStartOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 1);

        assertTrue(versionedCarparkFinder.canUndo());
    }

    @Test
    public void canUndo_singleCarparkFinder_returnsFalse() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(emptyCarparkFinder);

        assertFalse(versionedCarparkFinder.canUndo());
    }

    @Test
    public void canUndo_multipleCarparkFinderPointerAtStartOfStateList_returnsFalse() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        assertFalse(versionedCarparkFinder.canUndo());
    }

    @Test
    public void canRedo_multipleCarparkFinderPointerNotAtEndOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 1);

        assertTrue(versionedCarparkFinder.canRedo());
    }

    @Test
    public void canRedo_multipleCarparkFinderPointerAtStartOfStateList_returnsTrue() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        assertTrue(versionedCarparkFinder.canRedo());
    }

    @Test
    public void canRedo_singleCarparkFinder_returnsFalse() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(emptyCarparkFinder);

        assertFalse(versionedCarparkFinder.canRedo());
    }

    @Test
    public void canRedo_multipleCarparkFinderPointerAtEndOfStateList_returnsFalse() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);

        assertFalse(versionedCarparkFinder.canRedo());
    }

    @Test
    public void undo_multipleCarparkFinderPointerAtEndOfStateList_success() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);

        versionedCarparkFinder.undo();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Collections.singletonList(emptyCarparkFinder),
                carparkFinderWithJuliett,
                Collections.singletonList(carparkFinderWithKilo));
    }

    @Test
    public void undo_multipleCarparkFinderPointerNotAtStartOfStateList_success() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 1);

        versionedCarparkFinder.undo();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Collections.emptyList(),
                emptyCarparkFinder,
                Arrays.asList(carparkFinderWithJuliett, carparkFinderWithKilo));
    }

    @Test
    public void undo_singleCarparkFinder_throwsNoUndoableStateException() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(emptyCarparkFinder);

        assertThrows(VersionedCarparkFinder.NoUndoableStateException.class, versionedCarparkFinder::undo);
    }

    @Test
    public void undo_multipleCarparkFinderPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        assertThrows(VersionedCarparkFinder.NoUndoableStateException.class, versionedCarparkFinder::undo);
    }

    @Test
    public void redo_multipleCarparkFinderPointerNotAtEndOfStateList_success() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 1);

        versionedCarparkFinder.redo();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Arrays.asList(emptyCarparkFinder, carparkFinderWithJuliett),
                carparkFinderWithKilo,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleCarparkFinderPointerAtStartOfStateList_success() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        versionedCarparkFinder.redo();
        assertCarparkFinderListStatus(versionedCarparkFinder,
                Collections.singletonList(emptyCarparkFinder),
                carparkFinderWithJuliett,
                Collections.singletonList(carparkFinderWithKilo));
    }

    @Test
    public void redo_singleCarparkFinder_throwsNoRedoableStateException() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(emptyCarparkFinder);

        assertThrows(VersionedCarparkFinder.NoRedoableStateException.class, versionedCarparkFinder::redo);
    }

    @Test
    public void redo_multipleCarparkFinderPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);

        assertThrows(VersionedCarparkFinder.NoRedoableStateException.class, versionedCarparkFinder::redo);
    }

    @Test
    public void compare_multipleCarparkFinderHaveTwoLeftOne_success() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);

        assertEquals(1, versionedCarparkFinder.compare());
    }

    @Test
    public void compare_multipleCarparkFinderHaveThreeLeftOne_success() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo, carparkFinderWithCharlie);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        assertEquals(1, versionedCarparkFinder.compare());
    }

    @Test
    public void compare_singleCarparkFinder_throwsNoComparableStateException() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(emptyCarparkFinder);

        assertThrows(VersionedCarparkFinder.NoComparableStateException.class, versionedCarparkFinder::compare);
    }

    @Test
    public void compare_multipleCarparkFinderPointerAtStartOfStateList_throwsNoComparableStateException() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(
                emptyCarparkFinder, carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 2);

        assertThrows(VersionedCarparkFinder.NoComparableStateException.class, versionedCarparkFinder::compare);
    }

    @Test
    public void equals() {
        VersionedCarparkFinder versionedCarparkFinder = prepareCarparkFinderList(carparkFinderWithJuliett,
                carparkFinderWithKilo);

        // same values -> returns true
        VersionedCarparkFinder copy = prepareCarparkFinderList(carparkFinderWithJuliett, carparkFinderWithKilo);
        assertTrue(versionedCarparkFinder.equals(copy));

        // same object -> returns true
        assertTrue(versionedCarparkFinder.equals(versionedCarparkFinder));

        // null -> returns false
        assertFalse(versionedCarparkFinder.equals(null));

        // different types -> returns false
        assertFalse(versionedCarparkFinder.equals(1));

        // different state list -> returns false
        VersionedCarparkFinder differentCarparkFinderList = prepareCarparkFinderList(
                carparkFinderWithKilo, carparkFinderWithCharlie);
        assertFalse(versionedCarparkFinder.equals(differentCarparkFinderList));

        // different current pointer index -> returns false
        VersionedCarparkFinder differentCurrentStatePointer = prepareCarparkFinderList(
                carparkFinderWithJuliett, carparkFinderWithKilo);
        shiftCurrentStatePointerLeftwards(versionedCarparkFinder, 1);
        assertFalse(versionedCarparkFinder.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedCarparkFinder} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedCarparkFinder#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedCarparkFinder#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertCarparkFinderListStatus(VersionedCarparkFinder versionedCarparkFinder,
                                             List<ReadOnlyCarparkFinder> expectedStatesBeforePointer,
                                             ReadOnlyCarparkFinder expectedCurrentState,
                                             List<ReadOnlyCarparkFinder> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new CarparkFinder(versionedCarparkFinder), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedCarparkFinder.canUndo()) {
            versionedCarparkFinder.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyCarparkFinder expectedCarparkFinder : expectedStatesBeforePointer) {
            assertEquals(expectedCarparkFinder, new CarparkFinder(versionedCarparkFinder));
            versionedCarparkFinder.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyCarparkFinder expectedCarparkFinder : expectedStatesAfterPointer) {
            versionedCarparkFinder.redo();
            assertEquals(expectedCarparkFinder, new CarparkFinder(versionedCarparkFinder));
        }

        // check that there are no more states after pointer
        assertFalse(versionedCarparkFinder.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedCarparkFinder.undo());
    }

    /**
     * Creates and returns a {@code VersionedCarparkFinder} with the {@code CarparkFinderStates} added into it, and the
     * {@code VersionedCarparkFinder#currentStatePointer} at the end of list.
     */
    private VersionedCarparkFinder prepareCarparkFinderList(ReadOnlyCarparkFinder... carparkFinderStates) {
        assertFalse(carparkFinderStates.length == 0);

        VersionedCarparkFinder versionedCarparkFinder = new VersionedCarparkFinder(carparkFinderStates[0]);
        for (int i = 1; i < carparkFinderStates.length; i++) {
            versionedCarparkFinder.resetData(carparkFinderStates[i]);
            versionedCarparkFinder.commit();
        }

        return versionedCarparkFinder;
    }

    /**
     * Shifts the {@code versionedCarparkFinder#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedCarparkFinder versionedCarparkFinder, int count) {
        for (int i = 0; i < count; i++) {
            versionedCarparkFinder.undo();
        }
    }
}
