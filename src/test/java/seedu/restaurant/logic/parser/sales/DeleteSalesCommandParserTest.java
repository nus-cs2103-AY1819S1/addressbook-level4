package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.restaurant.logic.commands.sales.DeleteSalesCommand;

//@@author HyperionNKJ
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteSalesCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteSalesCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteSalesCommandParserTest {

    private DeleteSalesCommandParser parser = new DeleteSalesCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteSalesCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSalesCommand.MESSAGE_USAGE));
    }
}
