package seedu.address.logic.parser.order;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.order.DoneCommand;

public class DoneCommandParserTest {

    private DoneCommandParser parser = new DoneCommandParser();

    @Test
    public void parse_validArgs_returnsDoneCommand() {
        assertParseSuccess(parser, "1", new DoneCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

}
