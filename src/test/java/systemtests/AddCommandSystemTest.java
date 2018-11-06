package systemtests;

import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.testutil.RecipeUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    /*@Test
    public void add() {
        *//*Model model = getModel();

        *//**//* ------------------------ Perform add operations on the shown unfiltered list
        ----------------------------- *//**//*

        *//**//* Case: add a recipe without tags to a non-empty address book, command with leading spaces and trailing
        spaces
         * -> added
         *//**//*
        Recipe toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + DIFFICULTY_DESC_AMY + " "
                + COOKTIME_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        *//**//* Case: undo adding Amy to the list -> Amy deleted *//**//*
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        *//**//* Case: redo adding Amy to the list -> Amy added again *//**//*
        command = RedoCommand.COMMAND_WORD;
        model.add(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        *//**//* Case: add a recipe with all fields same as another recipe in the address book except name -> added
        *//**//*
        toAdd = new RecipeBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + DIFFICULTY_DESC_AMY + COOKTIME_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        *//**//* Case: add a recipe with all fields same as another recipe in the address book except phone and email
         * -> added
         *//**//*
        toAdd = new RecipeBuilder(AMY).withDifficulty(VALID_DIFFICULTY_BOB).withCooktime(VALID_COOKTIME_BOB).build();
        command = RecipeUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        *//**//* Case: add to empty address book -> added *//**//*
        deleteAllRecipes();
        assertCommandSuccess(APPLE);

        *//**//* Case: add a recipe with tags, command with parameters in random order -> added *//**//*
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + DIFFICULTY_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + COOKTIME_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        *//**//* Case: add a recipe, missing tags -> added *//**//*
        assertCommandSuccess(HOON);

        *//**//* -------------------------- Perform add operation on the shown filtered list
        ------------------------------ *//**//*

        *//**//* Case: filters the recipe list before adding -> added *//**//*
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        *//**//* ------------------------ Perform add operation while a recipe card is selected
        --------------------------- *//**//*

        *//**//* Case: selects first card in the recipe list, add a recipe -> added, card selection remains unchanged
         *//**//*
        selectRecipe(Index.fromOneBased(1));
        assertCommandSuccess(CHINESE);

        *//**//* ----------------------------------- Perform invalid add operations
        --------------------------------------- *//**//*

        *//**//* Case: add a duplicate recipe -> rejected *//**//*
        command = RecipeUtil.getAddCommand(HOON);
        //assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: add a duplicate recipe except with different phone -> rejected *//**//*
        toAdd = new RecipeBuilder(HOON).withDifficulty(VALID_DIFFICULTY_BOB).build();
        command = RecipeUtil.getAddCommand(toAdd);
        //assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: add a duplicate recipe except with different email -> rejected *//**//*
        toAdd = new RecipeBuilder(HOON).withCooktime(VALID_COOKTIME_BOB).build();
        command = RecipeUtil.getAddCommand(toAdd);
        //assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: add a duplicate recipe except with different address -> rejected *//**//*
        toAdd = new RecipeBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = RecipeUtil.getAddCommand(toAdd);
        //assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: add a duplicate recipe except with different tags -> rejected *//**//*
        command = RecipeUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        //assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECIPE);

        *//**//* Case: missing name -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + DIFFICULTY_DESC_AMY + COOKTIME_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//**//* Case: missing phone -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + COOKTIME_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//**//* Case: missing email -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DIFFICULTY_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//**//* Case: missing address -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DIFFICULTY_DESC_AMY + COOKTIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        *//**//* Case: invalid keyword -> rejected *//**//*
        command = "adds " + RecipeUtil.getRecipeDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        *//**//* Case: invalid name -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + DIFFICULTY_DESC_AMY +
        COOKTIME_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        *//**//* Case: invalid phone -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_DIFFICULTY_DESC +
        COOKTIME_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        *//**//* Case: invalid email -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DIFFICULTY_DESC_AMY +
        INVALID_COOKTIME_DESC + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        *//**//* Case: invalid address -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DIFFICULTY_DESC_AMY +
        COOKTIME_DESC_AMY + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        *//**//* Case: invalid tag -> rejected *//**//*
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DIFFICULTY_DESC_AMY +
        COOKTIME_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);*//*
    }*/

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the recipeModel and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code RecipeListPanel} equal to the corresponding components in
     * the current recipeModel added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Recipe toAdd) {
        assertCommandSuccess(RecipeUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Recipe)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Recipe)
     */
    private void assertCommandSuccess(String command, Recipe toAdd) {
        Model expectedModel = getModel();
        expectedModel.add(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_ADD_SUCCESS, "recipe", toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Recipe)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code RecipeListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Recipe)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
