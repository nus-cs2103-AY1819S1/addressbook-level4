package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTIFICATION_PARAMETER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISABLE_NOTIFICATION_PARAMETER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.NotificationCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the Notification code.
 */
public class NotificationCommandParserTest {

    private NotificationCommandParser parser = new NotificationCommandParser();

    @Test
    public void parse_validArgs_returnsNotificationCommand() {
        assertParseSuccess(parser, " " + VALID_DISABLE_NOTIFICATION_PARAMETER, new NotificationCommand(false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,PREAMBLE_WHITESPACE + INVALID_NOTIFICATION_PARAMETER, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotificationCommand.MESSAGE_USAGE));
    }

}
