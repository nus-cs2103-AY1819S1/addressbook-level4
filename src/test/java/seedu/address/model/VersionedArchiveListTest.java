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

import seedu.address.testutil.ArchiveListBuilder;

public class VersionedArchiveListTest {

    private final ReadOnlyArchiveList archiveListWithAmy = new ArchiveListBuilder().withPerson(AMY).build();
    private final ReadOnlyArchiveList archiveListWithBob = new ArchiveListBuilder().withPerson(BOB).build();
    private final ReadOnlyArchiveList archiveListWithCarl = new ArchiveListBuilder().withPerson(CARL).build();
    private final ReadOnlyArchiveList emptyArchiveList = new ArchiveListBuilder().build();

    @Test
    public void commit_singleArchiveList_noStatesRemovedCurrentStateSaved() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(emptyArchiveList);

        versionedArchiveList.commit();
        assertArchiveListStatus(versionedArchiveList,
                Collections.singletonList(emptyArchiveList),
                emptyArchiveList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleArchiveListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);

        versionedArchiveList.commit();
        assertArchiveListStatus(versionedArchiveList,
                Arrays.asList(emptyArchiveList, archiveListWithAmy, archiveListWithBob),
                archiveListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleArchiveListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 2);

        versionedArchiveList.commit();
        assertArchiveListStatus(versionedArchiveList,
                Collections.singletonList(emptyArchiveList),
                emptyArchiveList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleArchiveListPointerAtEndOfStateList_returnsTrue() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);

        assertTrue(versionedArchiveList.canUndo());
    }

    @Test
    public void canUndo_multipleArchiveListPointerAtStartOfStateList_returnsTrue() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 1);

        assertTrue(versionedArchiveList.canUndo());
    }

    @Test
    public void canUndo_singleArchiveList_returnsFalse() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(emptyArchiveList);
        assertFalse(versionedArchiveList.canUndo());
    }

    @Test
    public void canUndo_multipleArchiveListPointerAtStartOfStateList_returnsFalse() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 2);
        assertFalse(versionedArchiveList.canUndo());
    }

    @Test
    public void canRedo_multipleArchiveListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 1);
        assertTrue(versionedArchiveList.canRedo());
    }

    @Test
    public void canRedo_multipleArchiveListPointerAtStartOfStateList_returnsTrue() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 2);
        assertTrue(versionedArchiveList.canRedo());
    }

    @Test
    public void canRedo_singleArchiveList_returnsFalse() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(emptyArchiveList);
        assertFalse(versionedArchiveList.canRedo());
    }

    @Test
    public void canRedo_multipleArchiveListPointerAtEndOfStateList_returnsFalse() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        assertFalse(versionedArchiveList.canRedo());
    }

    @Test
    public void undo_multipleArchiveListPointerAtEndOfStateList_success() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        versionedArchiveList.undo();
        assertArchiveListStatus(versionedArchiveList,
                Collections.singletonList(emptyArchiveList),
                archiveListWithAmy,
                Collections.singletonList(archiveListWithBob));
    }

    @Test
    public void undo_multipleArchiveListPointerNotAtStartOfStateList_success() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 1);

        versionedArchiveList.undo();
        assertArchiveListStatus(versionedArchiveList,
                Collections.emptyList(),
                emptyArchiveList,
                Arrays.asList(archiveListWithAmy, archiveListWithBob));
    }

    @Test
    public void undo_singleArchiveList_throwsNoUndoableStateException() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(emptyArchiveList);
        assertThrows(VersionedArchiveList.NoUndoableStateException.class, versionedArchiveList::undo);
    }

    @Test
    public void undo_multipleArchiveListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 2);
        assertThrows(VersionedArchiveList.NoUndoableStateException.class, versionedArchiveList::undo);
    }

    @Test
    public void redo_multipleArchiveListPointerNotAtEndOfStateList_success() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 1);

        versionedArchiveList.redo();
        assertArchiveListStatus(versionedArchiveList,
                Arrays.asList(emptyArchiveList, archiveListWithAmy),
                archiveListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleArchiveListPointerAtStartOfStateList_success() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 2);

        versionedArchiveList.redo();
        assertArchiveListStatus(versionedArchiveList,
                Collections.singletonList(emptyArchiveList),
                archiveListWithAmy,
                Collections.singletonList(archiveListWithBob));
    }

    @Test
    public void redo_singleArchiveList_throwsNoRedoableStateException() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(emptyArchiveList);
        assertThrows(VersionedArchiveList.NoRedoableStateException.class, versionedArchiveList::redo);
    }

    @Test
    public void redo_multipleArchiveListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(
                emptyArchiveList, archiveListWithAmy, archiveListWithBob);
        assertThrows(VersionedArchiveList.NoRedoableStateException.class, versionedArchiveList::redo);
    }

    @Test
    public void equals() {
        VersionedArchiveList versionedArchiveList = prepareArchiveList(archiveListWithAmy, archiveListWithBob);

        // same values -> returns true
        VersionedArchiveList copy = prepareArchiveList(archiveListWithAmy, archiveListWithBob);
        assertTrue(versionedArchiveList.equals(copy));

        // same object -> returns true
        assertTrue(versionedArchiveList.equals(versionedArchiveList));

        // null -> returns false
        assertFalse(versionedArchiveList.equals(null));

        // different types -> returns false
        assertFalse(versionedArchiveList.equals(1));

        // different state list -> returns false
        VersionedArchiveList differentArchiveList = prepareArchiveList(archiveListWithBob, archiveListWithCarl);
        assertFalse(versionedArchiveList.equals(differentArchiveList));

        // different current pointer index -> returns false
        VersionedArchiveList differentCurrentStatePointer = prepareArchiveList(
                archiveListWithAmy, archiveListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArchiveList, 1);
        assertFalse(versionedArchiveList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedArchiveList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedArchiveList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedArchiveList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertArchiveListStatus(VersionedArchiveList versionedArchiveList,
                                             List<ReadOnlyArchiveList> expectedStatesBeforePointer,
                                             ReadOnlyArchiveList expectedCurrentState,
                                             List<ReadOnlyArchiveList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ArchiveList(versionedArchiveList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedArchiveList.canUndo()) {
            versionedArchiveList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyArchiveList expectedArchiveList : expectedStatesBeforePointer) {
            assertEquals(expectedArchiveList, new ArchiveList(versionedArchiveList));
            versionedArchiveList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyArchiveList expectedArchiveList : expectedStatesAfterPointer) {
            versionedArchiveList.redo();
            assertEquals(expectedArchiveList, new ArchiveList(versionedArchiveList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedArchiveList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedArchiveList.undo());
    }

    /**
     * Creates and returns a {@code VersionedArchiveList} with the {@code ArchiveListStates} added into it, and the
     * {@code VersionedArchiveList#currentStatePointer} at the end of list.
     */
    private VersionedArchiveList prepareArchiveList(ReadOnlyArchiveList... ArchiveListStates) {
        assertFalse(ArchiveListStates.length == 0);

        VersionedArchiveList versionedArchiveList = new VersionedArchiveList(ArchiveListStates[0]);
        for (int i = 1; i < ArchiveListStates.length; i++) {
            versionedArchiveList.resetData(ArchiveListStates[i]);
            versionedArchiveList.commit();
        }

        return versionedArchiveList;
    }

    /**
     * Shifts the {@code versionedArchiveList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedArchiveList versionedArchiveList, int count) {
        for (int i = 0; i < count; i++) {
            versionedArchiveList.undo();
        }
    }
}
