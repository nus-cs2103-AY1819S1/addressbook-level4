package ssp.scheduleplanner.logic.commands;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ReadOnlySchedulePlanner;
import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Interval;
import ssp.scheduleplanner.model.task.Repeat;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.testutil.TaskBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

public class AddRepeatCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddRepeatCommand(null, new Repeat("3"), new Interval("3"));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new AddRepeatCommand(validTask, new Repeat("2"), new Interval("1"))
                .execute(modelStub, commandHistory);

        Task anotherValidTask = (new TaskBuilder()).withDate("121155").build();


        assertEquals(String.format(AddRepeatCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask, anotherValidTask), modelStub.tasksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void archiveTask(Task completedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void autoDeleteArchived() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTag(Tag tag, String category) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCategory(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Category getCategory(String category) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlySchedulePlanner newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySchedulePlanner getSchedulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasArchivedTask(Task archivedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTagInCategory(Tag tag, Category category) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCategory(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredArchivedTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Category> getCategoryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredArchivedTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoSchedulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoSchedulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoSchedulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoSchedulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitSchedulePlanner() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        final Category modules = new Category("Modules");
        final Category others = new Category("Others");
        final ArrayList<Tag> tags = new ArrayList<>();
        final ArrayList<Category> categories =
                new ArrayList<Category>() {
                    {
                        add(modules);
                        add(others);
                    }
                };

        @Override
        public void autoDeleteArchived(){
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }


        @Override
        public void addTag(Tag tag, String category) {
            tags.add(tag);
        }

        @Override
        public ObservableList<Category> getCategoryList() {
            return FXCollections.observableArrayList(categories);
        }
        @Override
        public void commitSchedulePlanner() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlySchedulePlanner getSchedulePlanner() {
            return new SchedulePlanner();
        }
    }
}
