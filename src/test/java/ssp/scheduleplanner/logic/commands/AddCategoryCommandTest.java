package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
import ssp.scheduleplanner.testutil.CategoryBuilder;

public class AddCategoryCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCategory_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCategoryCommand(null);
    }

    @Test
    public void execute_categoryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCategoryAdded modelStub = new ModelStubAcceptingCategoryAdded();
        Category validCategory = new CategoryBuilder().build();

        CommandResult commandResult =
                new AddCategoryCommand(validCategory.getName()).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCategoryCommand.MESSAGE_SUCCESS,
                validCategory.getName()), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCategory), modelStub.categoriesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCategory_throwsCommandException() throws Exception {
        Category validModules = new CategoryBuilder().withName("Modules").build();
        Category validOthers = new CategoryBuilder().withName("Others").build();
        AddCategoryCommand addModulesCommand = new AddCategoryCommand(validModules.getName());
        AddCategoryCommand addOthersCommand = new AddCategoryCommand(validOthers.getName());
        ModelStub modelStub = new ModelStubWithCategory();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCategoryCommand.MESSAGE_DUPLICATE_CATEGORY);
        addModulesCommand.execute(modelStub, commandHistory);
        addOthersCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Category games = new CategoryBuilder().withName("Games").build();
        Category queen = new CategoryBuilder().withName("Queen").build();
        AddCategoryCommand addGamesCommand = new AddCategoryCommand("Games");
        AddCategoryCommand addQueenCommand = new AddCategoryCommand("Queen");

        // same object -> returns true
        assertTrue(addGamesCommand.equals(addGamesCommand));

        // different types -> returns false
        assertFalse(addGamesCommand.equals(1));

        // null -> returns false
        assertFalse(addGamesCommand.equals(null));

        // different category -> returns false
        assertFalse(addGamesCommand.equals(addQueenCommand));
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
    private class ModelStubWithCategory extends ModelStub {
        private final Category modules;
        private final Category others;

        ModelStubWithCategory() {
            this.modules = new Category("Modules");
            this.others = new Category("Others");
        }

        @Override
        public boolean hasCategory(String category) {
            requireNonNull(category);
            return (category.equals("Modules") || category.equals("Others"));
        }
    }

    /**
     * A Model stub that always accept the category being added.
     */
    private class ModelStubAcceptingCategoryAdded extends ModelStub {
        final ArrayList<Category> categoriesAdded = new ArrayList<>();
        final Category modules = new Category("Modules");
        final Category others = new Category("Others");
        final ArrayList<Tag> tags = new ArrayList<>();
        final ArrayList<Category> categories =
                new ArrayList<Category>() {{
                    add(modules);
                    add(others);
                }};

        @Override
        public void autoDeleteArchived(){
        }

        @Override
        public boolean hasCategory(String category) {
            requireNonNull(category);
            return (category.equals("Modules") || category.equals("Others"));
        }

        @Override
        public void addCategory(String name) {
            requireNonNull(name);
            categoriesAdded.add(new Category(name));
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
