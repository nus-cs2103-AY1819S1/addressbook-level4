package seedu.souschef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.parser.commandparser.DeleteCommandParser;
import seedu.souschef.logic.parser.commandparser.FindCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {
    private static Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(),
            new UserPrefs()).getRecipeModel();

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertDeleteParseSuccess(DeleteCommandParser commandParser, String userInput,
                                                Command<UniqueType> expectedCommand) {
        try {
            Command<Recipe> command = new DeleteCommandParser().parseRecipe(model, userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertFindParseSuccess(FindCommandParser commandParser, String userInput,
                                              Command<UniqueType> expectedCommand) {
        try {
            Command<Recipe> command = new FindCommandParser().parseRecipe(model, userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertDeleteParseFailure(DeleteCommandParser commandParser, String userInput,
                                                String expectedMessage) {
        try {
            commandParser.parseRecipe(model, userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertFindParseFailure(FindCommandParser commandParser, String userInput,
                                                String expectedMessage) {
        try {
            commandParser.parseRecipe(model, userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
