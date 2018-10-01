package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.eventcommands.JoinEventCommand;
import seedu.address.logic.parser.eventparsers.JoinEventCommandParser;

public class JoinEventCommandParserTest {
    private JoinEventCommandParser parser = new JoinEventCommandParser();

    @Test
    public void parse_validArgs_returnsJoinEventCommand() {
        assertParseSuccess(parser, "1", new JoinEventCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JoinEventCommand.MESSAGE_USAGE));
    }
}
