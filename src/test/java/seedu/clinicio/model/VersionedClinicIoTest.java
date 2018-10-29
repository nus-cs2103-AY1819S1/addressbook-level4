package seedu.clinicio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.AMY;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BOB;
import static seedu.clinicio.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.clinicio.testutil.ClinicIoBuilder;

public class VersionedClinicIoTest {

    private final ReadOnlyClinicIo clinicIoWithAmy = new ClinicIoBuilder().withPerson(AMY)
            .withStaff(ADAM).build();
    private final ReadOnlyClinicIo clinicIoWithBob = new ClinicIoBuilder().withPerson(BOB)
            .withStaff(BEN).build();
    private final ReadOnlyClinicIo clinicIoWithCarl = new ClinicIoBuilder().withPerson(CARL).build();
    private final ReadOnlyClinicIo emptyClinicIo = new ClinicIoBuilder().build();

    @Test
    public void commit_singleClinicIo_noStatesRemovedCurrentStateSaved() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(emptyClinicIo);

        versionedClinicIo.commit();
        assertClinicIoListStatus(versionedClinicIo,
                Collections.singletonList(emptyClinicIo),
                emptyClinicIo,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleClinicIoPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);

        versionedClinicIo.commit();
        assertClinicIoListStatus(versionedClinicIo,
                Arrays.asList(emptyClinicIo, clinicIoWithAmy, clinicIoWithBob),
                clinicIoWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleClinicIoPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 2);

        versionedClinicIo.commit();
        assertClinicIoListStatus(versionedClinicIo,
                Collections.singletonList(emptyClinicIo),
                emptyClinicIo,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleClinicIoPointerAtEndOfStateList_returnsTrue() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);

        assertTrue(versionedClinicIo.canUndo());
    }

    @Test
    public void canUndo_multipleClinicIoPointerAtStartOfStateList_returnsTrue() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 1);

        assertTrue(versionedClinicIo.canUndo());
    }

    @Test
    public void canUndo_singleClinicIo_returnsFalse() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(emptyClinicIo);

        assertFalse(versionedClinicIo.canUndo());
    }

    @Test
    public void canUndo_multipleClinicIoPointerAtStartOfStateList_returnsFalse() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 2);

        assertFalse(versionedClinicIo.canUndo());
    }

    @Test
    public void canRedo_multipleClinicIoPointerNotAtEndOfStateList_returnsTrue() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 1);

        assertTrue(versionedClinicIo.canRedo());
    }

    @Test
    public void canRedo_multipleClinicIoPointerAtStartOfStateList_returnsTrue() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 2);

        assertTrue(versionedClinicIo.canRedo());
    }

    @Test
    public void canRedo_singleClinicIo_returnsFalse() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(emptyClinicIo);

        assertFalse(versionedClinicIo.canRedo());
    }

    @Test
    public void canRedo_multipleClinicIoPointerAtEndOfStateList_returnsFalse() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);

        assertFalse(versionedClinicIo.canRedo());
    }

    @Test
    public void undo_multipleClinicIPointerAtEndOfStateList_success() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);

        versionedClinicIo.undo();
        assertClinicIoListStatus(versionedClinicIo,
                Collections.singletonList(emptyClinicIo),
                clinicIoWithAmy,
                Collections.singletonList(clinicIoWithBob));
    }

    @Test
    public void undo_multipleClinicIoPointerNotAtStartOfStateList_success() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 1);

        versionedClinicIo.undo();
        assertClinicIoListStatus(versionedClinicIo,
                Collections.emptyList(),
                emptyClinicIo,
                Arrays.asList(clinicIoWithAmy, clinicIoWithBob));
    }

    @Test
    public void undo_singleClinicIo_throwsNoUndoableStateException() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(emptyClinicIo);

        assertThrows(VersionedClinicIo.NoUndoableStateException.class, versionedClinicIo::undo);
    }

    @Test
    public void undo_multipleClinicIoPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 2);

        assertThrows(VersionedClinicIo.NoUndoableStateException.class, versionedClinicIo::undo);
    }

    @Test
    public void redo_multipleClinicIoPointerNotAtEndOfStateList_success() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 1);

        versionedClinicIo.redo();
        assertClinicIoListStatus(versionedClinicIo,
                Arrays.asList(emptyClinicIo, clinicIoWithAmy),
                clinicIoWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleClinicIoPointerAtStartOfStateList_success() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 2);

        versionedClinicIo.redo();
        assertClinicIoListStatus(versionedClinicIo,
                Collections.singletonList(emptyClinicIo),
                clinicIoWithAmy,
                Collections.singletonList(clinicIoWithBob));
    }

    @Test
    public void redo_singleClinicIo_throwsNoRedoableStateException() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(emptyClinicIo);

        assertThrows(VersionedClinicIo.NoRedoableStateException.class, versionedClinicIo::redo);
    }

    @Test
    public void redo_multipleClinicIoPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(
                emptyClinicIo, clinicIoWithAmy, clinicIoWithBob);

        assertThrows(VersionedClinicIo.NoRedoableStateException.class, versionedClinicIo::redo);
    }

    @Test
    public void equals() {
        VersionedClinicIo versionedClinicIo = prepareClinicIoList(clinicIoWithAmy,
                clinicIoWithBob);

        // same values -> returns true
        VersionedClinicIo copy = prepareClinicIoList(clinicIoWithAmy, clinicIoWithBob);
        assertTrue(versionedClinicIo.equals(copy));

        // same object -> returns true
        assertTrue(versionedClinicIo.equals(versionedClinicIo));

        // null -> returns false
        assertFalse(versionedClinicIo.equals(null));

        // different types -> returns false
        assertFalse(versionedClinicIo.equals(1));

        // different state list -> returns false
        VersionedClinicIo differentClinicIoList = prepareClinicIoList(clinicIoWithBob,
                clinicIoWithCarl);
        assertFalse(versionedClinicIo.equals(differentClinicIoList));

        // different current pointer index -> returns false
        VersionedClinicIo differentCurrentStatePointer = prepareClinicIoList(
                clinicIoWithAmy, clinicIoWithBob);
        shiftCurrentStatePointerLeftwards(versionedClinicIo, 1);
        assertFalse(versionedClinicIo.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedClinicIo} is currently pointing at {@code expectedCurrentState}, states
     * before {@code versionedClinicIo#currentStatePointer} is equal to {@code
     * expectedStatesBeforePointer}, and states after {@code versionedClinicIo#currentStatePointer} is
     * equal to {@code expectedStatesAfterPointer}.
     */
    private void assertClinicIoListStatus(VersionedClinicIo versionedClinicIo,
            List<ReadOnlyClinicIo> expectedStatesBeforePointer,
            ReadOnlyClinicIo expectedCurrentState,
            List<ReadOnlyClinicIo> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ClinicIo(versionedClinicIo), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedClinicIo.canUndo()) {
            versionedClinicIo.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyClinicIo expectedClinicIo : expectedStatesBeforePointer) {
            assertEquals(expectedClinicIo, new ClinicIo(versionedClinicIo));
            versionedClinicIo.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyClinicIo expectedClinicIo : expectedStatesAfterPointer) {
            versionedClinicIo.redo();
            assertEquals(expectedClinicIo, new ClinicIo(versionedClinicIo));
        }

        // check that there are no more states after pointer
        assertFalse(versionedClinicIo.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedClinicIo.undo());
    }

    /**
     * Creates and returns a {@code VersionedClinicIo} with the {@code clinicIoStates} added into it,
     * and the {@code VersionedClinicIo#currentStatePointer} at the end of list.
     */
    private VersionedClinicIo prepareClinicIoList(ReadOnlyClinicIo... clinicIoStates) {
        assertFalse(clinicIoStates.length == 0);

        VersionedClinicIo versionedClinicIo = new VersionedClinicIo(clinicIoStates[0]);
        for (int i = 1; i < clinicIoStates.length; i++) {
            versionedClinicIo.resetData(clinicIoStates[i]);
            versionedClinicIo.commit();
        }

        return versionedClinicIo;
    }

    /**
     * Shifts the {@code versionedClinicIo#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedClinicIo versionedClinicIo, int count) {
        for (int i = 0; i < count; i++) {
            versionedClinicIo.undo();
        }
    }
}
