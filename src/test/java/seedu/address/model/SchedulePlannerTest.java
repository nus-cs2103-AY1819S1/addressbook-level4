package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.getTypicalSchedulePlanner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class SchedulePlannerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SchedulePlanner schedulePlanner = new SchedulePlanner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), schedulePlanner.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        schedulePlanner.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlySchedulePlanner_replacesData() {
        SchedulePlanner newData = getTypicalSchedulePlanner();
        schedulePlanner.resetData(newData);
        assertEquals(newData, schedulePlanner);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedAlice = new TaskBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Task> newTasks = Arrays.asList(ALICE, editedAlice);
        SchedulePlannerStub newData = new SchedulePlannerStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        schedulePlanner.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        schedulePlanner.hasTask(null);
    }

    @Test
    public void hasTask_taskNotInSchedulePlanner_returnsFalse() {
        assertFalse(schedulePlanner.hasTask(ALICE));
    }

    @Test
    public void hasTask_taskInSchedulePlanner_returnsTrue() {
        schedulePlanner.addTask(ALICE);
        assertTrue(schedulePlanner.hasTask(ALICE));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInSchedulePlanner_returnsTrue() {
        schedulePlanner.addTask(ALICE);
        Task editedAlice = new TaskBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(schedulePlanner.hasTask(editedAlice));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        schedulePlanner.getTaskList().remove(0);
    }

    /**
     * A stub ReadOnlySchedulePlanner whose tasks list can violate interface constraints.
     */
    private static class SchedulePlannerStub implements ReadOnlySchedulePlanner {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        SchedulePlannerStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
