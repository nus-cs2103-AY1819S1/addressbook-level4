package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.eventcommands.DisplayPollCommand;
import seedu.address.logic.parser.eventparsers.DisplayPollCommandParser;

public class DisplayPollCommandParserTest {
    private DisplayPollCommandParser parser = new DisplayPollCommandParser();

    @Test
    public void parse_validArgs_returnsJoinEventCommand() {
        assertParseSuccess(parser, "1", new DisplayPollCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayPollCommand.MESSAGE_USAGE));
    }
}
