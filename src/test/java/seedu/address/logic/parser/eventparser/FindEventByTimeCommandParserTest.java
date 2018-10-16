//@@theJrLinguist
package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_WRONG_DATE_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_WRONG_TIME_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.DisplayPollCommand;
import seedu.address.logic.commands.eventcommands.FindEventByTimeCommand;
import seedu.address.logic.parser.eventparsers.FindEventByTimeCommandParser;

public class FindEventByTimeCommandParserTest {
    private FindEventByTimeCommandParser parser = new FindEventByTimeCommandParser();

    @Test
    public void parse_validArgs_returnsFindEventByTimeCommand() {
        LocalDate date = LocalDate.of(2018, 12, 12);
        LocalTime startTime = LocalTime.of(12, 30);
        LocalTime endTime = LocalTime.of(13, 30);
        assertParseSuccess(parser, " d/12-12-2018 t1/12:30 t2/13:30",
                new FindEventByTimeCommand(date, startTime, endTime));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventByTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, " d/12:12:2018 t1/12:30 t2/13:30",
                String.format(MESSAGE_WRONG_DATE_FORMAT, DisplayPollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTime_throwsParseException() {
        assertParseFailure(parser, " d/12-12-2018 t1/1230 t2/1330",
                String.format(MESSAGE_WRONG_TIME_FORMAT, DisplayPollCommand.MESSAGE_USAGE));
    }
}
