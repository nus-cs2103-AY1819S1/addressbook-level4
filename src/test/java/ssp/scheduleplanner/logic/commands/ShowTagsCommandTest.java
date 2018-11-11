package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import ssp.scheduleplanner.commons.events.ui.ShowTagsRequestEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ReadOnlySchedulePlanner;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.testutil.CategoryBuilder;
import ssp.scheduleplanner.ui.testutil.EventsCollectorRule;

public class ShowTagsCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCategory_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ShowTagsCommand(null);
    }

    @Test
    public void execute_categoryDoesNotExist_failure() throws Exception {
        ModelStub modelStub = new DefaultModelStub();

        Category validCategory = new CategoryBuilder().build();

        thrown.expectMessage(ShowTagsCommand.MESSAGE_CATEGORY_NONEXISTENT);
        CommandResult commandResult =
                new ShowTagsCommand(validCategory.getName()).execute(modelStub, commandHistory);

        assertEquals(ShowTagsCommand.MESSAGE_CATEGORY_NONEXISTENT, commandResult.feedbackToUser);
    }

    @Test
    public void execute_categoryExists_success() throws Exception {
        ModelStub modelStub = new DefaultModelStub();

        CommandResult result = new ShowTagsCommand("Modules").execute(modelStub, commandHistory);
        assertEquals(result.feedbackToUser, ShowTagsCommand.MESSAGE_SUCCESS);

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTagsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void equals() {

        ShowTagsCommand showModules = new ShowTagsCommand("Modules");
        ShowTagsCommand showOthers = new ShowTagsCommand("Others");

        // same object -> returns true
        assertTrue(showModules.equals(showModules));

        // different types -> returns false
        assertFalse(showModules.equals(1));

        // null -> returns false
        assertFalse(showModules.equals(null));

        // different category -> returns false
        assertFalse(showModules.equals(showOthers));
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
        public void editCategory(String original, String newName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearCategory(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCategory(String name) {
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
     * A Model stub that contains two default categories.
     */
    private class DefaultModelStub extends ModelStub {
        private final Category modules;
        private final Category others;

        DefaultModelStub() {
            this.modules = new Category("Modules");
            this.others = new Category("Others");
        }

        @Override
        public boolean hasCategory(String category) {
            requireNonNull(category);
            return (category.equals("Modules") || category.equals("Others"));
        }
    }
}