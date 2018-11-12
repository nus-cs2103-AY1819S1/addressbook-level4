package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.util.Objects.requireNonNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ReadOnlySchedulePlanner;
import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.testutil.TaskBuilder;

public class AddTagCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_tagAcceptedByModel_addSuccessful() throws Exception {
        AddTagCommandTest.ModelStubAcceptingTagAdded modelStub = new AddTagCommandTest.ModelStubAcceptingTagAdded();
        Tag tag = new Tag("CS2103");

        CommandResult commandResult = new AddTagCommand(tag, "Others").execute(modelStub, commandHistory);

        assertEquals(String.format(AddTagCommand.MESSAGE_SUCCESS, tag, "Others"), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(tag), modelStub.Others.getTags());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateTagInSameCategory_throwsCommandException() throws Exception {
        Tag validTag = new Tag("CS2103");
        AddTagCommand addCommand = new AddTagCommand(validTag, "Others");
        AddTagCommandTest.ModelStub modelStub = new AddTagCommandTest.ModelStubWithTag(validTag);
        assertTrue(modelStub.hasTagInCategory(validTag, modelStub.getCategory("Others")));
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTagCommand.MESSAGE_DUPLICATE_TAG);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Tag cs = new Tag("CS2103");
        Tag st = new Tag("ST2334");

        AddTagCommand addCsCommand = new AddTagCommand(cs, "Others");
        AddTagCommand addStCommand = new AddTagCommand(st, "Others");

        // same object -> returns true
        assertTrue(addCsCommand.equals(addCsCommand));

        // same values -> returns true
        Tag csCopy = new Tag("CS2103");
        assertTrue(cs.equals(csCopy));

        // different types -> returns false
        assertFalse(addCsCommand.equals(1));

        // null -> returns false
        assertFalse(addCsCommand.equals(null));

        // different tag -> returns false
        assertFalse(addCsCommand.equals(addStCommand));
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
    private class ModelStubWithTag extends AddTagCommandTest.ModelStub {
        final ArrayList<Tag> OthersCategory = new ArrayList<>();
        final Category Others = new Category("Others");

        ModelStubWithTag(Tag tag) {
            requireNonNull(tag);
            this.Others.addTag(tag);
        }

        @Override
        public boolean hasCategory(String category) {
            return category.equals(Others.getName());
        }

        @Override
        public Category getCategory (String name) {
            return Others;
        }

        @Override
        public boolean hasTagInCategory(Tag tag, Category category) {
            return category.getUniqueTagList().contains(tag);
        }

        @Override
        public void addTag(Tag tag, String name) {
            requireNonNull(name);
            requireNonNull(tag);
            OthersCategory.add(tag);
        }

        @Override
        public void commitSchedulePlanner() {

        }
    }

    /**
     * A Model stub that always accept the category being added.
     */
    private class ModelStubAcceptingTagAdded extends AddTagCommandTest.ModelStub {
        final ArrayList<Tag> OthersCategory = new ArrayList<>();
        final Category Others = new Category("Others");

        @Override
        public boolean hasCategory(String category) {
            return true;
        }

        @Override
        public void addTag(Tag tag, String name) {
            requireNonNull(name);
            requireNonNull(tag);
            Others.addTag(tag);
        }

        @Override
        public Category getCategory(String name) {
            return Others;
        }

        @Override
        public boolean hasTagInCategory(Tag tag, Category category) {
            return OthersCategory.contains(tag);
        }

        @Override
        public void commitSchedulePlanner() {
            // called by {@code AddTagCommand#execute()}
        }

        @Override
        public ReadOnlySchedulePlanner getSchedulePlanner() {
            return new SchedulePlanner();
        }
    }
}
