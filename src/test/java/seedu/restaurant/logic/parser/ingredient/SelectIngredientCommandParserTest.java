package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.restaurant.logic.commands.ingredient.SelectIngredientCommand;

//@@author rebstan97
public class SelectIngredientCommandParserTest {

    private SelectIngredientCommandParser parser = new SelectIngredientCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectIngredientCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectIngredientCommand.MESSAGE_USAGE));
    }
}
