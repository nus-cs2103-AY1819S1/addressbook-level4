package systemtests;

import static seedu.souschef.logic.commands.DeleteCommand.MESSAGE_DELETE_RECIPE_SUCCESS;
import static seedu.souschef.testutil.TestUtil.getRecipe;

import org.junit.Test;

import seedu.souschef.commons.core.Messages;
import seedu.souschef.commons.core.index.Index;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

public class DeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown --------------------
         *  *//*

        *//* Case: delete the first recipe in the list, command with leading spaces and trailing spaces -> deleted *//*
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_RECIPE.getOneBased() + "       ";
        Recipe deletedRecipe = removeRecipe(expectedModel, INDEX_FIRST_RECIPE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RECIPE_SUCCESS, deletedRecipe);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        *//* Case: delete the last recipe in the list -> deleted *//*
        Model modelBeforeDeletingLast = getModel();
        Index lastRecipeIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastRecipeIndex);

        *//* Case: undo deleting the last recipe in the list -> last recipe restored *//*
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        *//* Case: redo deleting the last recipe in the list -> last recipe deleted again *//*
        command = RedoCommand.COMMAND_WORD;
        removeRecipe(modelBeforeDeletingLast, lastRecipeIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        *//* Case: delete the middle recipe in the list -> deleted *//*
        Index middleRecipeIndex = getMidIndex(getModel());
        assertCommandSuccess(middleRecipeIndex);

        *//* ------------------ Performing delete operation while a filtered list is being shown
        ---------------------- *//*

        *//* Case: filtered recipe list, delete index within bounds of address book and recipe list -> deleted *//*
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_RECIPE;
        assertTrue(index.getZeroBased() < getModel().getFilteredList().size());
        assertCommandSuccess(index);

        *//* Case: filtered recipe list, delete index within bounds of address book but out of bounds of recipe list
         * -> rejected
         *//*
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAppContent().getObservableRecipeList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        //assertCommandFailure(command, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        *//* --------------------- Performing delete operation while a recipe card is selected ------------------------ *//*

        *//* Case: delete the selected recipe -> recipe list panel selects the recipe before the deleted recipe *//*
        showAllRecipes();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectRecipe(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedRecipe = removeRecipe(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_RECIPE_SUCCESS, deletedRecipe);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        *//* --------------------------------- Performing invalid delete operation ------------------------------------ *//*

        *//* Case: invalid index (0) -> rejected *//*
        command = DeleteCommand.COMMAND_WORD + " 0";
        //assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        *//* Case: invalid index (-1) -> rejected *//*
        command = DeleteCommand.COMMAND_WORD + " -1";
        //assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        *//* Case: invalid index (size + 1) -> rejected *//*
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAppContent().getObservableRecipeList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        //assertCommandFailure(command, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        *//* Case: invalid arguments (alphabets) -> rejected *//*
        //assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        *//* Case: invalid arguments (extra argument) -> rejected *//*
        //assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        *//* Case: mixed case command word -> rejected *//*
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);*/
    }

    /**
     * Removes the {@code Recipe} at the specified {@code index} in {@code recipeModel}'s address book.
     * @return the removed recipe
     */
    private Recipe removeRecipe(Model model, Index index) {
        Recipe targetRecipe = getRecipe(model, index);
        model.delete(targetRecipe);
        return targetRecipe;
    }

    /**
     * Deletes the recipe at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Recipe deletedRecipe = removeRecipe(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RECIPE_SUCCESS, deletedRecipe);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel
                , expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
