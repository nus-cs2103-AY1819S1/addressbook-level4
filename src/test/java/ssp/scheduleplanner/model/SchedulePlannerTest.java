package ssp.scheduleplanner.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static ssp.scheduleplanner.testutil.TypicalTasks.ALICE;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.model.task.exceptions.DuplicateTaskException;
import ssp.scheduleplanner.testutil.TaskBuilder;

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
        // Two tasks with the same fields


        Task editedAlice = new TaskBuilder(ALICE).build();
        Category others = new Category("Others");
        others.addTag(new Tag("friends"));
        List<Category> categories = Arrays.asList(new Category("Modules"), others);
        List<Task> newTasks = Arrays.asList(ALICE, editedAlice);
        List<Task> archivedTasks = Arrays.asList();
        SchedulePlannerStub newData = new SchedulePlannerStub(categories, newTasks, archivedTasks);

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

    // OLD TEST
    /*
    public void hasTask_taskWithSameIdentityFieldsInSchedulePlanner_returnsTrue() {
        schedulePlanner.addTask(ALICE);
        Task editedAlice = new TaskBuilder(ALICE).withVenue(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(schedulePlanner.hasTask(editedAlice));
    */
    @Test
    public void hasTask_taskWithNotAllSameFieldsInSchedulePlanner_returnsFalse() {
        schedulePlanner.addTask(ALICE);
        Task editedAlice = new TaskBuilder(ALICE).withVenue(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertFalse(schedulePlanner.hasTask(editedAlice));
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
        private final ObservableList<Category> categories = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Task> archivedTasks = FXCollections.observableArrayList();

        SchedulePlannerStub(Collection<Category> categories, Collection<Task> tasks, Collection<Task> archivedTasks) {
            this.categories.setAll(categories);
            this.tasks.setAll(tasks);
            this.archivedTasks.setAll(archivedTasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Task> getArchivedTaskList() {
            return archivedTasks;
        }

        @Override
        public ObservableList<Category> getCategoryList() {
            return categories;
        }

    }

}
