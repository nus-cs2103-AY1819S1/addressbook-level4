package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.address.testutil.TypicalRecipes.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.address.model.AppContent;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.EditRecipeDescriptorBuilder;
import seedu.address.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Recipe editedRecipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new AppContent(model.getAppContent()), new UserPrefs());
        expectedModel.updateRecipe(model.getFilteredRecipeList().get(0), editedRecipe);
        expectedModel.commitAppContent();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredRecipeList().size());
        Recipe lastRecipe = model.getFilteredRecipeList().get(indexLastPerson.getZeroBased());

        RecipeBuilder personInList = new RecipeBuilder(lastRecipe);
        Recipe editedRecipe = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new AppContent(model.getAppContent()), new UserPrefs());
        expectedModel.updateRecipe(lastRecipe, editedRecipe);
        expectedModel.commitAppContent();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE, new EditRecipeDescriptor());
        Recipe editedRecipe = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new AppContent(model.getAppContent()), new UserPrefs());
        expectedModel.commitAppContent();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);

        Recipe recipeInFilteredList = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe editedRecipe = new RecipeBuilder(recipeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new AppContent(model.getAppContent()), new UserPrefs());
        expectedModel.updateRecipe(model.getFilteredRecipeList().get(0), editedRecipe);
        expectedModel.commitAppContent();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Recipe firstRecipe = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(firstRecipe).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RECIPE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);

        // edit recipe in filtered list into a duplicate in address book
        Recipe recipeInList = model.getAppContent().getRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder(recipeInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);
        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppContent().getRecipeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Recipe editedRecipe = new RecipeBuilder().build();
        Recipe recipeToEdit = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE, descriptor);
        Model expectedModel = new ModelManager(new AppContent(model.getAppContent()), new UserPrefs());
        expectedModel.updateRecipe(recipeToEdit, editedRecipe);
        expectedModel.commitAppContent();

        // edit -> first recipe edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered recipe list to show all persons
        expectedModel.undoAppContent();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first recipe edited again
        expectedModel.redoAppContent();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Recipe} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited recipe in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the recipe object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Recipe editedRecipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE, descriptor);
        Model expectedModel = new ModelManager(new AppContent(model.getAppContent()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_RECIPE);
        Recipe recipeToEdit = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        expectedModel.updateRecipe(recipeToEdit, editedRecipe);
        expectedModel.commitAppContent();

        // edit -> edits second recipe in unfiltered recipe list / first recipe in filtered recipe list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered recipe list to show all persons
        expectedModel.undoAppContent();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased()), recipeToEdit);
        // redo -> edits same second recipe in unfiltered recipe list
        expectedModel.redoAppContent();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_RECIPE, DESC_AMY);

        // same values -> returns true
        EditRecipeDescriptor copyDescriptor = new EditCommand.EditRecipeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_RECIPE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RECIPE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RECIPE, DESC_BOB)));
    }

}
