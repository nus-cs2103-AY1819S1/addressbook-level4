package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.C_TASK;
import static seedu.address.testutil.TypicalTasks.Y_TASK;
import static seedu.address.testutil.TypicalTasks.Z_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskManagerBuilder;

public class VersionedTaskManagerTest {

    private final ReadOnlyTaskManager taskManagerWithAmy = new TaskManagerBuilder().withTask(Y_TASK).build();
    private final ReadOnlyTaskManager taskManagerWithBob = new TaskManagerBuilder().withTask(Z_TASK).build();
    private final ReadOnlyTaskManager taskManagerWithCarl = new TaskManagerBuilder().withTask(C_TASK).build();
    private final ReadOnlyTaskManager emptyTaskManager = new TaskManagerBuilder().build();

    @Test
    public void commit_singleTaskManager_noStatesRemovedCurrentStateSaved() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        versionedTaskManager.commit();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.singletonList(emptyTaskManager),
            emptyTaskManager,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleTaskManagerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);

        versionedTaskManager.commit();
        assertTaskManagerListStatus(versionedTaskManager,
            Arrays.asList(emptyTaskManager, taskManagerWithAmy, taskManagerWithBob),
            taskManagerWithBob,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleTaskManagerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 2);

        versionedTaskManager.commit();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.singletonList(emptyTaskManager),
            emptyTaskManager,
            Collections.emptyList());
    }

    @Test
    public void rollback_singleTaskManagerNoStateChanged_noChange() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        versionedTaskManager.rollback();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.emptyList(),
            emptyTaskManager,
            Collections.emptyList());
    }

    @Test
    public void rollback_multipleTaskManagerPointerAtEndOfStateList_noChange() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);

        versionedTaskManager.rollback();
        assertTaskManagerListStatus(versionedTaskManager,
            Arrays.asList(emptyTaskManager, taskManagerWithAmy),
            taskManagerWithBob,
            Collections.emptyList());
    }

    @Test
    public void rollback_multipleTaskManagerPointerNotAtEndOfStateList_noChange() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 2);

        versionedTaskManager.rollback();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.emptyList(),
            emptyTaskManager,
            Arrays.asList(taskManagerWithAmy, taskManagerWithBob));
    }

    @Test
    public void canUndo_multipleTaskManagerPointerAtEndOfStateList_returnsTrue() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);

        assertTrue(versionedTaskManager.canUndo());
    }

    @Test
    public void canUndo_multipleTaskManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 1);

        assertTrue(versionedTaskManager.canUndo());
    }

    @Test
    public void canUndo_singleTaskManager_returnsFalse() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        assertFalse(versionedTaskManager.canUndo());
    }

    @Test
    public void canUndo_multipleTaskManagerPointerAtStartOfStateList_returnsFalse() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 2);

        assertFalse(versionedTaskManager.canUndo());
    }

    @Test
    public void canRedo_multipleTaskManagerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 1);

        assertTrue(versionedTaskManager.canRedo());
    }

    @Test
    public void canRedo_multipleTaskManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 2);

        assertTrue(versionedTaskManager.canRedo());
    }

    @Test
    public void canRedo_singleTaskManager_returnsFalse() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        assertFalse(versionedTaskManager.canRedo());
    }

    @Test
    public void canRedo_multipleTaskManagerPointerAtEndOfStateList_returnsFalse() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);

        assertFalse(versionedTaskManager.canRedo());
    }

    @Test
    public void undo_multipleTaskManagerPointerAtEndOfStateList_success() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);

        versionedTaskManager.undo();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.singletonList(emptyTaskManager),
            taskManagerWithAmy,
            Collections.singletonList(taskManagerWithBob));
    }

    @Test
    public void undo_multipleTaskManagerPointerNotAtStartOfStateList_success() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 1);

        versionedTaskManager.undo();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.emptyList(),
            emptyTaskManager,
            Arrays.asList(taskManagerWithAmy, taskManagerWithBob));
    }

    @Test
    public void undo_singleTaskManager_throwsNoUndoableStateException() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        assertThrows(VersionedTaskManager.NoUndoableStateException.class, versionedTaskManager::undo);
    }

    @Test
    public void undo_multipleTaskManagerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 2);

        assertThrows(VersionedTaskManager.NoUndoableStateException.class, versionedTaskManager::undo);
    }

    @Test
    public void redo_multipleTaskManagerPointerNotAtEndOfStateList_success() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 1);

        versionedTaskManager.redo();
        assertTaskManagerListStatus(versionedTaskManager,
            Arrays.asList(emptyTaskManager, taskManagerWithAmy),
            taskManagerWithBob,
            Collections.emptyList());
    }

    @Test
    public void redo_multipleTaskManagerPointerAtStartOfStateList_success() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 2);

        versionedTaskManager.redo();
        assertTaskManagerListStatus(versionedTaskManager,
            Collections.singletonList(emptyTaskManager),
            taskManagerWithAmy,
            Collections.singletonList(taskManagerWithBob));
    }

    @Test
    public void redo_singleTaskManager_throwsNoRedoableStateException() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        assertThrows(VersionedTaskManager.NoRedoableStateException.class, versionedTaskManager::redo);
    }

    @Test
    public void redo_multipleTaskManagerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(
            emptyTaskManager, taskManagerWithAmy, taskManagerWithBob);

        assertThrows(VersionedTaskManager.NoRedoableStateException.class, versionedTaskManager::redo);
    }

    @Test
    public void equals() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(taskManagerWithAmy, taskManagerWithBob);

        // same values -> returns true
        VersionedTaskManager copy = prepareTaskManagerList(taskManagerWithAmy, taskManagerWithBob);
        assertTrue(versionedTaskManager.equals(copy));

        // same object -> returns true
        assertTrue(versionedTaskManager.equals(versionedTaskManager));

        // null -> returns false
        assertFalse(versionedTaskManager.equals(null));

        // different types -> returns false
        assertFalse(versionedTaskManager.equals(1));

        // different state list -> returns false
        VersionedTaskManager differentTaskManagerList = prepareTaskManagerList(taskManagerWithBob, taskManagerWithCarl);
        assertFalse(versionedTaskManager.equals(differentTaskManagerList));

        // different current pointer index -> returns false
        VersionedTaskManager differentCurrentStatePointer = prepareTaskManagerList(
            taskManagerWithAmy, taskManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskManager, 1);
        assertFalse(versionedTaskManager.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTaskManager} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTaskManager#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTaskManager#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTaskManagerListStatus(VersionedTaskManager versionedTaskManager,
                                             List<ReadOnlyTaskManager> expectedStatesBeforePointer,
                                             ReadOnlyTaskManager expectedCurrentState,
                                             List<ReadOnlyTaskManager> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TaskManager(versionedTaskManager), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTaskManager.canUndo()) {
            versionedTaskManager.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTaskManager expectedTaskManager : expectedStatesBeforePointer) {
            assertEquals(expectedTaskManager, new TaskManager(versionedTaskManager));
            versionedTaskManager.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTaskManager expectedTaskManager : expectedStatesAfterPointer) {
            versionedTaskManager.redo();
            assertEquals(expectedTaskManager, new TaskManager(versionedTaskManager));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTaskManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTaskManager.undo());
    }

    /**
     * Creates and returns a {@code VersionedTaskManager} with the {@code taskManagerStates} added into it, and the
     * {@code VersionedTaskManager#currentStatePointer} at the end of list.
     */
    private VersionedTaskManager prepareTaskManagerList(ReadOnlyTaskManager... taskManagerStates) {
        assertFalse(taskManagerStates.length == 0);

        VersionedTaskManager versionedTaskManager = new VersionedTaskManager(taskManagerStates[0]);
        for (int i = 1; i < taskManagerStates.length; i++) {
            versionedTaskManager.resetData(taskManagerStates[i]);
            versionedTaskManager.commit();
        }

        return versionedTaskManager;
    }

    /**
     * Shifts the {@code versionedTaskManager#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTaskManager versionedTaskManager, int count) {
        for (int i = 0; i < count; i++) {
            versionedTaskManager.undo();
        }
    }
}
