package systemtests;

import static seedu.souschef.commons.core.Messages.MESSAGE_LISTED_OVERVIEW;

import seedu.souschef.model.Model;

public class FindCommandSystemTest extends AddressBookSystemTest {

    /*@Test
    public void find() {
        *//* Case: find multiple recipes in address book, command with leading spaces and trailing spaces
         * -> 2 recipes found
         *//**//*
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BANDITO, DANISH); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: repeat previous find command where recipe list is displaying the recipes we are finding
         * -> 2 recipes found
         *//**//*
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find recipe where recipe list is not displaying the recipe we are finding -> 1 recipe found */
    /**//*
        command = FindCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, CHINESE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find multiple recipes in address book, 2 keywords -> 2 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BANDITO, DANISH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find multiple recipes in address book, 2 keywords in reversed order -> 2 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find multiple recipes in address book, 2 keywords with 1 repeat -> 2 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find multiple recipes in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 recipes found
         *//**//*
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: undo previous find command -> rejected *//**//*
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        *//**//* Case: redo previous find command -> rejected *//**//*
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        *//**//* Case: find same recipes in address book after deleting 1 of them -> 1 recipe found *//**//*
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAppContent().getObservableRecipeList().contains(BANDITO));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANISH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find recipe in address book, keyword is same as name but of different case -> 1 recipe found
        *//**//*
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find recipe in address book, keyword is substring of name -> 0 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find recipe in address book, name is substring of keyword -> 0 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find recipe not in address book -> 0 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find phone number of recipe in address book -> 0 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " " + DANISH.getCooktime().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find address of recipe in address book -> 0 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " " + DANISH.getDifficulty().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find email of recipe in address book -> 0 recipes found *//**//*
        command = FindCommand.COMMAND_WORD + " " + DANISH.getCooktime().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find tags of recipe in address book -> 0 recipes found *//**//*
        List<Tag> tags = new ArrayList<>(DANISH.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: find while a recipe is selected -> selected card deselected *//**//*
        showAllRecipes();
        selectRecipe(Index.fromOneBased(1));
        assertFalse(getRecipeListPanel().getHandleToSelectedCard().getName().equals(DANISH.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANISH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        *//**//* Case: find recipe in empty address book -> 0 recipes found *//**//*
        deleteAllRecipes();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANISH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        *//**//* Case: mixed case command word -> rejected *//**//*
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);*//*
    }*/

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_RECIPES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the recipeModel related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_LISTED_OVERVIEW, expectedModel.getFilteredList().size(), "recipe");

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the recipeModel related components equal
     * to the current recipeModel.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
