package systemtests;

import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.commons.core.index.Index;
import seedu.souschef.logic.commands.EditCommand;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

public class EditCommandSystemTest extends AddressBookSystemTest {

    /*@Test
    public void edit() {
        *//*Model model = getModel();

        *//**//* ----------------- Performing edit operation while an unfiltered list is being shown
        ---------------------- *//**//*

        *//**//* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each
         field
         * -> edited
         *//**//*
        Index index = INDEX_FIRST_RECIPE;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + DIFFICULTY_DESC_BOB + " " + COOKTIME_DESC_BOB + "  " + ADDRESS_DESC_BOB +
                " " + TAG_DESC_HUSBAND + " ";
        Recipe editedRecipe = new RecipeBuilder(BEE).withTags(VALID_TAG_STAPLE).build();
        assertCommandSuccess(command, index, editedRecipe);

        *//**//* Case: undo editing the last recipe in the list -> last recipe restored *//**//*
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        *//**//* Case: redo editing the last recipe in the list -> last recipe edited again *//**//*
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.update(
                getModel().getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased()), editedRecipe);
        assertCommandSuccess(command, model, expectedResultMessage);

        *//**//* Case: edit a recipe with new values same as existing values -> edited *//**//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_BOB + COOKTIME_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BEE);

        *//**//* Case: edit a recipe with new values same as another recipe's values but with different name ->
        edited *//**//*
        assertTrue(getModel().getAppContent().getObservableRecipeList().contains(BEE));
        index = INDEX_SECOND_RECIPE;
        assertNotEquals(getModel().getFilteredList().get(index.getZeroBased()), BEE);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY
        + DIFFICULTY_DESC_BOB + COOKTIME_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRecipe = new RecipeBuilder(BEE).withName(VALID_NAME_AMERICA).build();
        assertCommandSuccess(command, index, editedRecipe);

        *//**//* Case: edit a recipe with new values same as another recipe's values but
        with different phone and email
         * -> edited
         *//**//*
        index = INDEX_SECOND_RECIPE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_AMY + COOKTIME_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRecipe = new RecipeBuilder(BEE).withDifficulty(VALID_DIFFICULTY_5)
        .withCooktime(VALID_COOKTIME_MIN).build();
        assertCommandSuccess(command, index, editedRecipe);

        *//**//* Case: clear tags -> cleared *//**//*
        index = INDEX_FIRST_RECIPE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Recipe recipeToEdit = getModel().getFilteredList().get(index.getZeroBased());
        editedRecipe = new RecipeBuilder(recipeToEdit).withTags().build();
        assertCommandSuccess(command, index, editedRecipe);

        *//**//* ------------------ Performing edit operation while a filtered list is being shown
        ------------------------ *//**//*

        *//**//* Case: filtered recipe list, edit index within bounds of address book and recipe list -> edited *//**//*
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_RECIPE;
        assertTrue(index.getZeroBased() < getModel().getFilteredList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        recipeToEdit = getModel().getFilteredList().get(index.getZeroBased());
        editedRecipe = new RecipeBuilder(recipeToEdit).withName(VALID_NAME_BEE).build();
        assertCommandSuccess(command, index, editedRecipe);

        *//**//* Case: filtered recipe list, edit index within bounds of address book but out of bounds of recipe list
         * -> rejected
         *//**//*
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAppContent().getObservableRecipeList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        *//**//* --------------------- Performing edit operation while a recipe card is selected
        -------------------------- *//**//*

        *//**//* Case: selects first card in the recipe list, edit a recipe -> edited, card selection remains
        unchanged but
         * browser url changes
         *//**//*
        showAllRecipes();
        index = INDEX_FIRST_RECIPE;
        selectRecipe(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY
        + DIFFICULTY_DESC_AMY + COOKTIME_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new recipe's name
        assertCommandSuccess(command, index, AMERICA, index);

        *//**//* --------------------------------- Performing invalid edit operation
        -------------------------------------- *//**//*

        *//**//* Case: invalid index (0) -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        *//**//* Case: invalid index (-1) -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        *//**//* Case: invalid index (size + 1) -> rejected *//**//*
        invalidIndex = getModel().getFilteredList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        *//**//* Case: missing index -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        *//**//* Case: missing all fields -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        *//**//* Case: invalid name -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
        + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        *//**//* Case: invalid phone -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
        + INVALID_DIFFICULTY_DESC,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        *//**//* Case: invalid email -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
        + INVALID_COOKTIME_DESC,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        *//**//* Case: invalid address -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        *//**//* Case: invalid tag -> rejected *//**//*
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        *//**//* Case: edit a recipe with new values same as another recipe's values -> rejected *//**//*
        executeCommand(RecipeUtil.getAddCommand(BEE));
        assertTrue(getModel().getAppContent().getObservableRecipeList().contains(BEE));
        index = INDEX_FIRST_RECIPE;
        assertFalse(getModel().getFilteredList().get(index.getZeroBased()).equals(BEE));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_BOB + COOKTIME_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: edit a recipe with new values same as another recipe's values but with
        different tags -> rejected
        *//**//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_BOB +
        COOKTIME_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: edit a recipe with new values same as another recipe's values but with
        different address ->
        rejected *//**//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_BOB + COOKTIME_DESC_BOB
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: edit a recipe with new values same as another recipe's values but with different phone ->
        rejected
         *//**//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_AMY + COOKTIME_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: edit a recipe with new values same as another recipe's values but with different email ->
        rejected
         *//**//*
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
        + DIFFICULTY_DESC_BOB + COOKTIME_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);*//*
    }*/

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Recipe, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current recipeModel's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Recipe, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Recipe editedRecipe) {
        assertCommandSuccess(command, toEdit, editedRecipe, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
     * <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the recipeModel related components are updated to reflect the recipe
     * at index {@code toEdit} being updated to values specified {@code editedRecipe}.<br>
     * @param toEdit the index of the current recipeModel's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Recipe editedRecipe,
            Index expectedSelectedCardIndex) {
        Model<Recipe> expectedModel = getModel();
        expectedModel.update(expectedModel.getFilteredList().get(toEdit.getZeroBased()), editedRecipe);
        expectedModel.updateFilteredList(PREDICATE_SHOW_ALL);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_SUCCESS, "recipe", editedRecipe), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredList(PREDICATE_SHOW_ALL);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
