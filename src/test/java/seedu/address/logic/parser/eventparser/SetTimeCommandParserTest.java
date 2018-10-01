//@@author theJrLinguist
package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalTime;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.eventcommands.SetTimeCommand;
import seedu.address.logic.parser.eventparsers.SetTimeCommandParser;

public class SetTimeCommandParserTest {
    private SetTimeCommandParser parser = new SetTimeCommandParser();

    @Test
    public void parse_validArgs_returnsSetDateCommand() {
        LocalTime startTime = LocalTime.of(12, 00);
        LocalTime endTime = LocalTime.of(13, 30);
        assertParseSuccess(parser, " t1/12:00 t2/13:30", new SetTimeCommand(startTime, endTime));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " t/02-03-2018", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, " t1/1200 t2/1300", String.format(Messages.MESSAGE_WRONG_TIME_FORMAT));
    }
}
