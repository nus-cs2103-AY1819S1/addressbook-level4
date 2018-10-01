//@@author theJrLinguist
package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.eventcommands.SetDateCommand;
import seedu.address.logic.parser.eventparsers.SetDateCommandParser;

public class SetDateCommandParserTest {
    private SetDateCommandParser parser = new SetDateCommandParser();

    @Test
    public void parse_validArgs_returnsSetDateCommand() {
        LocalDate date = LocalDate.of(2018, 3, 2);
        assertParseSuccess(parser, " d/02-03-2018", new SetDateCommand(date));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " 02-03-2018", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, " d/2018-02-03", String.format(Messages.MESSAGE_WRONG_DATE_FORMAT));
    }
}
