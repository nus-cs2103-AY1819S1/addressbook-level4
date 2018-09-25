package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Task;
import seedu.address.model.person.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskManager_replacesData() {
        TaskManager newData = getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedAlice = new TaskBuilder(ALICE).withDescription(VALID_ADDRESS_BOB).withLabels(VALID_TAG_HUSBAND)
                .build();
        List<Task> newTasks = Arrays.asList(ALICE, editedAlice);
        TaskManagerStub newData = new TaskManagerStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        taskManager.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskManager.hasTask(null);
    }

    @Test
    public void hasTask_taskNotInTaskManager_returnsFalse() {
        assertFalse(taskManager.hasTask(ALICE));
    }

    @Test
    public void hasTask_personInTaskManager_returnsTrue() {
        taskManager.addTask(ALICE);
        assertTrue(taskManager.hasTask(ALICE));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInTaskManager_returnsTrue() {
        taskManager.addTask(ALICE);
        Task editedAlice = new TaskBuilder(ALICE).withDescription(VALID_ADDRESS_BOB).withLabels(VALID_TAG_HUSBAND)
                .build();
        assertTrue(taskManager.hasTask(editedAlice));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        taskManager.getTaskList().remove(0);
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks list can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskManagerStub(Collection<Task> persons) {
            this.tasks.setAll(persons);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
