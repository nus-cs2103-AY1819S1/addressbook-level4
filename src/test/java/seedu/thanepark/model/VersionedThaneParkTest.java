package seedu.thanepark.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.thanepark.testutil.TypicalRides.AMY;
import static seedu.thanepark.testutil.TypicalRides.BOB;
import static seedu.thanepark.testutil.TypicalRides.CASTLE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.thanepark.testutil.ThaneParkBuilder;

public class VersionedThaneParkTest {

    private final ReadOnlyThanePark thaneParkWithAmy = new ThaneParkBuilder().withRide(AMY).build();
    private final ReadOnlyThanePark thaneParkWithBob = new ThaneParkBuilder().withRide(BOB).build();
    private final ReadOnlyThanePark thaneParkWithCastle = new ThaneParkBuilder().withRide(CASTLE).build();
    private final ReadOnlyThanePark emptyThanePark = new ThaneParkBuilder().build();

    @Test
    public void commit_singleThanePark_noStatesRemovedCurrentStateSaved() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(emptyThanePark);

        versionedThanePark.commit();
        assertThaneParkListStatus(versionedThanePark,
                Collections.singletonList(emptyThanePark),
                emptyThanePark,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleThaneParkPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);

        versionedThanePark.commit();
        assertThaneParkListStatus(versionedThanePark,
                Arrays.asList(emptyThanePark, thaneParkWithAmy, thaneParkWithBob),
                thaneParkWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleThaneParkPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 2);

        versionedThanePark.commit();
        assertThaneParkListStatus(versionedThanePark,
                Collections.singletonList(emptyThanePark),
                emptyThanePark,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleThaneParkPointerAtEndOfStateList_returnsTrue() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);

        assertTrue(versionedThanePark.canUndo());
    }

    @Test
    public void canUndo_multipleThaneParkPointerAtStartOfStateList_returnsTrue() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 1);

        assertTrue(versionedThanePark.canUndo());
    }

    @Test
    public void canUndo_singleThanePark_returnsFalse() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(emptyThanePark);

        assertFalse(versionedThanePark.canUndo());
    }

    @Test
    public void canUndo_multipleThaneParkPointerAtStartOfStateList_returnsFalse() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 2);

        assertFalse(versionedThanePark.canUndo());
    }

    @Test
    public void canRedo_multipleThaneParkPointerNotAtEndOfStateList_returnsTrue() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 1);

        assertTrue(versionedThanePark.canRedo());
    }

    @Test
    public void canRedo_multipleThaneParkPointerAtStartOfStateList_returnsTrue() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 2);

        assertTrue(versionedThanePark.canRedo());
    }

    @Test
    public void canRedo_singleThanePark_returnsFalse() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(emptyThanePark);

        assertFalse(versionedThanePark.canRedo());
    }

    @Test
    public void canRedo_multipleThaneParkPointerAtEndOfStateList_returnsFalse() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);

        assertFalse(versionedThanePark.canRedo());
    }

    @Test
    public void undo_multipleThaneParkPointerAtEndOfStateList_success() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);

        versionedThanePark.undo();
        assertThaneParkListStatus(versionedThanePark,
                Collections.singletonList(emptyThanePark),
                thaneParkWithAmy,
                Collections.singletonList(thaneParkWithBob));
    }

    @Test
    public void undo_multipleThaneParkPointerNotAtStartOfStateList_success() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 1);

        versionedThanePark.undo();
        assertThaneParkListStatus(versionedThanePark,
                Collections.emptyList(),
                emptyThanePark,
                Arrays.asList(thaneParkWithAmy, thaneParkWithBob));
    }

    @Test
    public void undo_singleThanePark_throwsNoUndoableStateException() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(emptyThanePark);

        assertThrows(VersionedThanePark.NoUndoableStateException.class, versionedThanePark::undo);
    }

    @Test
    public void undo_multipleThaneParkPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 2);

        assertThrows(VersionedThanePark.NoUndoableStateException.class, versionedThanePark::undo);
    }

    @Test
    public void redo_multipleThaneParkPointerNotAtEndOfStateList_success() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 1);

        versionedThanePark.redo();
        assertThaneParkListStatus(versionedThanePark,
                Arrays.asList(emptyThanePark, thaneParkWithAmy),
                thaneParkWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleThaneParkPointerAtStartOfStateList_success() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 2);

        versionedThanePark.redo();
        assertThaneParkListStatus(versionedThanePark,
                Collections.singletonList(emptyThanePark),
                thaneParkWithAmy,
                Collections.singletonList(thaneParkWithBob));
    }

    @Test
    public void redo_singleThanePark_throwsNoRedoableStateException() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(emptyThanePark);

        assertThrows(VersionedThanePark.NoRedoableStateException.class, versionedThanePark::redo);
    }

    @Test
    public void redo_multipleThaneParkPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(
                emptyThanePark, thaneParkWithAmy, thaneParkWithBob);

        assertThrows(VersionedThanePark.NoRedoableStateException.class, versionedThanePark::redo);
    }

    @Test
    public void equals() {
        VersionedThanePark versionedThanePark = prepareThaneParkList(thaneParkWithAmy, thaneParkWithBob);

        // same values -> returns true
        VersionedThanePark copy = prepareThaneParkList(thaneParkWithAmy, thaneParkWithBob);
        assertTrue(versionedThanePark.equals(copy));

        // same object -> returns true
        assertTrue(versionedThanePark.equals(versionedThanePark));

        // null -> returns false
        assertFalse(versionedThanePark.equals(null));

        // different types -> returns false
        assertFalse(versionedThanePark.equals(1));

        // different state list -> returns false
        VersionedThanePark differentThaneParkList = prepareThaneParkList(thaneParkWithBob, thaneParkWithCastle);
        assertFalse(versionedThanePark.equals(differentThaneParkList));

        // different current pointer index -> returns false
        VersionedThanePark differentCurrentStatePointer = prepareThaneParkList(
                thaneParkWithAmy, thaneParkWithBob);
        shiftCurrentStatePointerLeftwards(versionedThanePark, 1);
        assertFalse(versionedThanePark.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedThanePark} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedThanePark#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedThanePark#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertThaneParkListStatus(VersionedThanePark versionedThanePark,
                                             List<ReadOnlyThanePark> expectedStatesBeforePointer,
                                             ReadOnlyThanePark expectedCurrentState,
                                             List<ReadOnlyThanePark> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ThanePark(versionedThanePark), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedThanePark.canUndo()) {
            versionedThanePark.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyThanePark expectedThanePark : expectedStatesBeforePointer) {
            assertEquals(expectedThanePark, new ThanePark(versionedThanePark));
            versionedThanePark.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyThanePark expectedThanePark : expectedStatesAfterPointer) {
            versionedThanePark.redo();
            assertEquals(expectedThanePark, new ThanePark(versionedThanePark));
        }

        // check that there are no more states after pointer
        assertFalse(versionedThanePark.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedThanePark.undo());
    }

    /**
     * Creates and returns a {@code VersionedThanePark} with the {@code thaneParkStates} added into it, and the
     * {@code VersionedThanePark#currentStatePointer} at the end of list.
     */
    private VersionedThanePark prepareThaneParkList(ReadOnlyThanePark... thaneParkStates) {
        assertFalse(thaneParkStates.length == 0);

        VersionedThanePark versionedThanePark = new VersionedThanePark(thaneParkStates[0]);
        for (int i = 1; i < thaneParkStates.length; i++) {
            versionedThanePark.resetData(thaneParkStates[i]);
            versionedThanePark.commit();
        }

        return versionedThanePark;
    }

    /**
     * Shifts the {@code versionedThanePark#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedThanePark versionedThanePark, int count) {
        for (int i = 0; i < count; i++) {
            versionedThanePark.undo();
        }
    }
}
