package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.restaurant.logic.commands.ingredient.DeleteIngredientByIndexCommand;
import seedu.restaurant.logic.commands.ingredient.DeleteIngredientByNameCommand;
import seedu.restaurant.logic.commands.ingredient.DeleteIngredientCommand;
import seedu.restaurant.model.ingredient.IngredientName;

//@@author rebstan97
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteIngredientCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteIngredientCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteIngredientCommandParserTest {

    private DeleteIngredientCommandParser parser = new DeleteIngredientCommandParser();

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteIngredientByIndexCommand(INDEX_FIRST));
    }

    @Test
    public void parse_validSingleName_returnsDeleteCommand() {
        assertParseSuccess(parser, "Chicken",
                new DeleteIngredientByNameCommand(new IngredientName("Chicken")));
    }

    @Test
    public void parse_validLongName_returnsDeleteCommand() {
        assertParseSuccess(parser, "Chicken Thigh",
                new DeleteIngredientByNameCommand(new IngredientName("Chicken Thigh")));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteIngredientCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteIngredientCommand.MESSAGE_USAGE));
    }

}
