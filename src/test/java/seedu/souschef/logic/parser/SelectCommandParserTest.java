package seedu.souschef.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.parser.commandparser.SelectCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {
    private Model<Recipe> recipeModel;
    private SelectCommandParser parser = new SelectCommandParser();

    /*@Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(model, INDEX_FIRST_RECIPE));
    }*/

    @Test
    public void parse_invalidArgs_throwsParseException() {
        try {
            parser.parseIndex(recipeModel, "a");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SelectCommand.MESSAGE_USAGE), pe.getMessage());
        }
    }
}
