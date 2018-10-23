package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskCollections;

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
import seedu.address.testutil.TaskBuilder;

public class TaskCollectionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskCollection taskCollection = new TaskCollection();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskCollection.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskCollection.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskCollections_replacesData() {
        TaskCollection newData = getTypicalTaskCollections();
        taskCollection.resetData(newData);
        assertEquals(newData, taskCollection);
    }

    @Test
    public void resetData_withDuplicateTasks_doesNotThrow() {
        // Two tasks with the same identity fields
        Task editedAlice = new TaskBuilder(ALICE)
            .withPriority(VALID_PRIORITY_BOB)
            .withFrequency(VALID_FREQUENCY_BOB)
            .withTags(VALID_TAG_HUSBAND)
            .build();
        List<Task> newTasks = Arrays.asList(ALICE, editedAlice);
        TaskCollectionStub newData = new TaskCollectionStub(newTasks);

        // make sure no exception gets thrown
        taskCollection.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskCollection.hasTask(null);
    }

    @Test
    public void hasPerson_personNotInTaskCollections_returnsFalse() {
        assertFalse(taskCollection.hasTask(ALICE));
    }

    @Test
    public void hasPerson_personInTaskCollections_returnsTrue() {
        taskCollection.addTask(ALICE);
        assertTrue(taskCollection.hasTask(ALICE));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        taskCollection.getTaskList().remove(0);
    }

    /**
     * A stub ReadOnlyTaskCollection whose tasks list can violate interface constraints.
     */
    private static class TaskCollectionStub implements ReadOnlyTaskCollection {

        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskCollectionStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
