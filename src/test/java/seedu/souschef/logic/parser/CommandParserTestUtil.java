package seedu.souschef.logic.parser;

import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {
    private static Model<Recipe> model;

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    /*
    public static void assertParseSuccess(CommandParser commandParser, String userInput,
                                          Command<UniqueType> expectedCommand) {
        try {
            Command<Recipe> command = commandParser.parseRecipe(model, userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }*/

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    /*
    public static void assertParseFailure(CommandParser commandParser, String userInput, String expectedMessage) {
        try {
            commandParser.parseRecipe(model, userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }*/
}
