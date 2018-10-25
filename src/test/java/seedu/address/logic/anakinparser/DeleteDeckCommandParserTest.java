package seedu.address.logic.anakinparser;

import static seedu.address.commons.core.AddressbookMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.anakinparser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.anakinparser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.logic.anakincommands.DeleteDeckCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the AddressbookParserUtilTest.
 */
public class DeleteDeckCommandParserTest {

    private DeleteDeckCommandParser parser = new DeleteDeckCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteDeckCommand(INDEX_FIRST_DECK));
    }

    @Test
    public void parse_blankArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeckCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeckCommand.MESSAGE_USAGE));
    }
}
