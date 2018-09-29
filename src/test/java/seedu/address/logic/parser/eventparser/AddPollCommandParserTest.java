//@@author theJrLinguist
package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.eventcommands.AddPollCommand;
import seedu.address.logic.parser.eventparsers.AddPollCommandParser;

public class AddPollCommandParserTest {
    private AddPollCommandParser parser = new AddPollCommandParser();

    @Test
    public void parse_validArgs_returnsAddPollCommand() {
        assertParseSuccess(parser, " n/Date poll", new AddPollCommand("Date poll"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Date poll", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPollCommand.MESSAGE_USAGE));
    }
}
