package systemtests;

import static seedu.souschef.logic.commands.SelectCommand.MESSAGE_SELECT_RECIPE_SUCCESS;

import seedu.souschef.commons.core.index.Index;
import seedu.souschef.model.Model;

public class SelectCommandSystemTest extends AddressBookSystemTest {
    /*@Test
    public void select() {
        *//* ------------------------ Perform select operations on the shown unfiltered list --------------------------
         * *//**//*

        *//**//* Case: select the first card in the recipe list, command with leading spaces and trailing spaces
         * -> selected
         *//**//*
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_RECIPE);

        *//**//* Case: select the last card in the recipe list -> selected *//**//*
        Index recipeCount = getLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + recipeCount.getOneBased();
        assertCommandSuccess(command, recipeCount);

        *//**//* Case: undo previous selection -> rejected *//**//*
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        *//**//* Case: redo selecting last card in the list -> rejected *//**//*
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        *//**//* Case: select the middle card in the recipe list -> selected *//**//*
        Index middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        *//**//* Case: select the current selected card -> selected *//**//*
        assertCommandSuccess(command, middleIndex);

        *//**//* ------------------------ Perform select operations on the shown filtered list
        ---------------------------- *//**//*

        *//**//* Case: filtered recipe list, select index within bounds of address book but out of bounds of recipe list
         * -> rejected
         *//**//*
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAppContent().getObservableRecipeList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        *//**//* Case: filtered recipe list, select index within bounds of address book and recipe list -> selected
        *//**//*
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        *//**//* ----------------------------------- Perform invalid select operations
        ------------------------------------ *//**//*

        *//**//* Case: invalid index (0) -> rejected *//**//*
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        *//**//* Case: invalid index (-1) -> rejected *//**//*
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        *//**//* Case: invalid index (size + 1) -> rejected *//**//*
        invalidIndex = getModel().getFilteredList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        *//**//* Case: invalid arguments (alphabets) -> rejected *//**//*
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        *//**//* Case: invalid arguments (extra argument) -> rejected *//**//*
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        *//**//* Case: mixed case command word -> rejected *//**//*
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        *//**//* Case: select from empty address book -> rejected *//**//*
        deleteAllRecipes();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased(),
                MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);*//*
    }*/

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected recipe.<br>
     * 4. {@code Storage} and {@code RecipeListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_RECIPE_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getRecipeListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RecipeListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
