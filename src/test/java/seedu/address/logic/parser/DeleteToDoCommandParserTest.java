package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtilToDo.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtilToDo.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.Test;

import seedu.address.logic.commands.DeleteToDoCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteToDoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteToDoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteToDoCommandParserTest {

    private DeleteToDoCommandParser parser = new DeleteToDoCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteToDoCommand(INDEX_FIRST_ELEMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteToDoCommand.MESSAGE_USAGE));
    }
}
