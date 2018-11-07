package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
        assertParseSuccess(parser, "enable", new NotificationCommand(true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,"set", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotificationCommand.MESSAGE_USAGE));
    }

}
