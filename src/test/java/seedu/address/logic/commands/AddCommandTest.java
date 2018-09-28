package seedu.address.logic.commands;

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

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AppContent;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAppContent;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.RecipeBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Recipe validRecipe = new RecipeBuilder().build();

        CommandResult commandResult = new AddCommand(validRecipe).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRecipe), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecipe), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Recipe validRecipe = new RecipeBuilder().build();
        AddCommand addCommand = new AddCommand(validRecipe);
        ModelStub modelStub = new ModelStubWithPerson(validRecipe);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_RECIPE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Recipe alice = new RecipeBuilder().withName("Alice").build();
        Recipe bob = new RecipeBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different recipe -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addRecipe(Recipe recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAppContent newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAppContent getAppContent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecipe(Recipe target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecipe(Recipe target, Recipe editedRecipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAppContent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAppContent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAppContent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAppContent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAppContent() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single recipe.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Recipe recipe;

        ModelStubWithPerson(Recipe recipe) {
            requireNonNull(recipe);
            this.recipe = recipe;
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return this.recipe.isSameRecipe(recipe);
        }
    }

    /**
     * A Model stub that always accept the recipe being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Recipe> personsAdded = new ArrayList<>();

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return personsAdded.stream().anyMatch(recipe::isSameRecipe);
        }

        @Override
        public void addRecipe(Recipe recipe) {
            requireNonNull(recipe);
            personsAdded.add(recipe);
        }

        @Override
        public void commitAppContent() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAppContent getAppContent() {
            return new AppContent();
        }
    }

}
