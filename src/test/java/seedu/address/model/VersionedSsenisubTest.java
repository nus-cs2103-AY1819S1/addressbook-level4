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

import seedu.address.testutil.SsenisubBuilder;

public class VersionedSsenisubTest {

    private final ReadOnlySsenisub ssenisubWithAmy = new SsenisubBuilder().withPerson(AMY).build();
    private final ReadOnlySsenisub ssenisubWithBob = new SsenisubBuilder().withPerson(BOB).build();
    private final ReadOnlySsenisub ssenisubWithCarl = new SsenisubBuilder().withPerson(CARL).build();
    private final ReadOnlySsenisub emptySsenisub = new SsenisubBuilder().build();

    @Test
    public void commit_singleSsenisub_noStatesRemovedCurrentStateSaved() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(emptySsenisub);

        versionedSsenisub.commit();
        assertSsenisubListStatus(versionedSsenisub,
                Collections.singletonList(emptySsenisub),
                emptySsenisub,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleSsenisubPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);

        versionedSsenisub.commit();
        assertSsenisubListStatus(versionedSsenisub,
                Arrays.asList(emptySsenisub, ssenisubWithAmy, ssenisubWithBob),
                ssenisubWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleSsenisubPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 2);

        versionedSsenisub.commit();
        assertSsenisubListStatus(versionedSsenisub,
                Collections.singletonList(emptySsenisub),
                emptySsenisub,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleSsenisubPointerAtEndOfStateList_returnsTrue() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);

        assertTrue(versionedSsenisub.canUndo());
    }

    @Test
    public void canUndo_multipleSsenisubPointerAtStartOfStateList_returnsTrue() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 1);

        assertTrue(versionedSsenisub.canUndo());
    }

    @Test
    public void canUndo_singleSsenisub_returnsFalse() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(emptySsenisub);

        assertFalse(versionedSsenisub.canUndo());
    }

    @Test
    public void canUndo_multipleSsenisubPointerAtStartOfStateList_returnsFalse() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 2);

        assertFalse(versionedSsenisub.canUndo());
    }

    @Test
    public void canRedo_multipleSsenisubPointerNotAtEndOfStateList_returnsTrue() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 1);

        assertTrue(versionedSsenisub.canRedo());
    }

    @Test
    public void canRedo_multipleSsenisubPointerAtStartOfStateList_returnsTrue() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 2);

        assertTrue(versionedSsenisub.canRedo());
    }

    @Test
    public void canRedo_singleSsenisub_returnsFalse() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(emptySsenisub);

        assertFalse(versionedSsenisub.canRedo());
    }

    @Test
    public void canRedo_multipleSsenisubPointerAtEndOfStateList_returnsFalse() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);

        assertFalse(versionedSsenisub.canRedo());
    }

    @Test
    public void undo_multipleSsenisubPointerAtEndOfStateList_success() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);

        versionedSsenisub.undo();
        assertSsenisubListStatus(versionedSsenisub,
                Collections.singletonList(emptySsenisub),
                ssenisubWithAmy,
                Collections.singletonList(ssenisubWithBob));
    }

    @Test
    public void undo_multipleSsenisubPointerNotAtStartOfStateList_success() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 1);

        versionedSsenisub.undo();
        assertSsenisubListStatus(versionedSsenisub,
                Collections.emptyList(),
                emptySsenisub,
                Arrays.asList(ssenisubWithAmy, ssenisubWithBob));
    }

    @Test
    public void undo_singleSsenisub_throwsNoUndoableStateException() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(emptySsenisub);

        assertThrows(VersionedSsenisub.NoUndoableStateException.class, versionedSsenisub::undo);
    }

    @Test
    public void undo_multipleSsenisubPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 2);

        assertThrows(VersionedSsenisub.NoUndoableStateException.class, versionedSsenisub::undo);
    }

    @Test
    public void redo_multipleSsenisubPointerNotAtEndOfStateList_success() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 1);

        versionedSsenisub.redo();
        assertSsenisubListStatus(versionedSsenisub,
                Arrays.asList(emptySsenisub, ssenisubWithAmy),
                ssenisubWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleSsenisubPointerAtStartOfStateList_success() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 2);

        versionedSsenisub.redo();
        assertSsenisubListStatus(versionedSsenisub,
                Collections.singletonList(emptySsenisub),
                ssenisubWithAmy,
                Collections.singletonList(ssenisubWithBob));
    }

    @Test
    public void redo_singleSsenisub_throwsNoRedoableStateException() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(emptySsenisub);

        assertThrows(VersionedSsenisub.NoRedoableStateException.class, versionedSsenisub::redo);
    }

    @Test
    public void redo_multipleSsenisubPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(
                emptySsenisub, ssenisubWithAmy, ssenisubWithBob);

        assertThrows(VersionedSsenisub.NoRedoableStateException.class, versionedSsenisub::redo);
    }

    @Test
    public void equals() {
        VersionedSsenisub versionedSsenisub = prepareSsenisubList(ssenisubWithAmy, ssenisubWithBob);

        // same values -> returns true
        VersionedSsenisub copy = prepareSsenisubList(ssenisubWithAmy, ssenisubWithBob);
        assertTrue(versionedSsenisub.equals(copy));

        // same object -> returns true
        assertTrue(versionedSsenisub.equals(versionedSsenisub));

        // null -> returns false
        assertFalse(versionedSsenisub.equals(null));

        // different types -> returns false
        assertFalse(versionedSsenisub.equals(1));

        // different state list -> returns false
        VersionedSsenisub differentSsenisubList = prepareSsenisubList(ssenisubWithBob, ssenisubWithCarl);
        assertFalse(versionedSsenisub.equals(differentSsenisubList));

        // different current pointer index -> returns false
        VersionedSsenisub differentCurrentStatePointer = prepareSsenisubList(
                ssenisubWithAmy, ssenisubWithBob);
        shiftCurrentStatePointerLeftwards(versionedSsenisub, 1);
        assertFalse(versionedSsenisub.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedSsenisub} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedSsenisub#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedSsenisub#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertSsenisubListStatus(VersionedSsenisub versionedSsenisub,
                                             List<ReadOnlySsenisub> expectedStatesBeforePointer,
                                             ReadOnlySsenisub expectedCurrentState,
                                             List<ReadOnlySsenisub> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Ssenisub(versionedSsenisub), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedSsenisub.canUndo()) {
            versionedSsenisub.undo();
        }

        // check states before pointer are correct
        for (ReadOnlySsenisub expectedSsenisub : expectedStatesBeforePointer) {
            assertEquals(expectedSsenisub, new Ssenisub(versionedSsenisub));
            versionedSsenisub.redo();
        }

        // check states after pointer are correct
        for (ReadOnlySsenisub expectedSsenisub : expectedStatesAfterPointer) {
            versionedSsenisub.redo();
            assertEquals(expectedSsenisub, new Ssenisub(versionedSsenisub));
        }

        // check that there are no more states after pointer
        assertFalse(versionedSsenisub.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedSsenisub.undo());
    }

    /**
     * Creates and returns a {@code VersionedSsenisub} with the {@code ssenisubStates} added into it, and the
     * {@code VersionedSsenisub#currentStatePointer} at the end of list.
     */
    private VersionedSsenisub prepareSsenisubList(ReadOnlySsenisub... ssenisubStates) {
        assertFalse(ssenisubStates.length == 0);

        VersionedSsenisub versionedSsenisub = new VersionedSsenisub(ssenisubStates[0]);
        for (int i = 1; i < ssenisubStates.length; i++) {
            versionedSsenisub.resetData(ssenisubStates[i]);
            versionedSsenisub.commit();
        }

        return versionedSsenisub;
    }

    /**
     * Shifts the {@code versionedSsenisub#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedSsenisub versionedSsenisub, int count) {
        for (int i = 0; i < count; i++) {
            versionedSsenisub.undo();
        }
    }
}
