package seedu.address.logic.parser;

import seedu.address.logic.commands.NotificationCommand;
import seedu.address.logic.commands.NotificationCommand.NotificationCommandDescriptor;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTIFICATION_ON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTIFICATION_OFF;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTIFICATION_WARNING_ON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTIFICATION_WARNING_OFF;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTIFICATION_TIP_ON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTIFICATION_TIP_OFF;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTIFICATION_WARNING_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTIFICATION_TIP_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTIFICATION_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOGGLE_TYPE;


import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author Snookerballs

public class NotificationCommandParserTest {
    private NotificationCommandParser parser = new NotificationCommandParser();

    @Test
    public void parse_invalidSyntaxt_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NotificationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalid_noSuffix_throwsParseException() {
        System.out.println(INVALID_NOTIFICATION_TYPE);
        assertParseFailure(parser, NotificationCommand.COMMAND_WORD + " "
                + INVALID_NOTIFICATION_TYPE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NotificationCommand.MESSAGE_USAGE) );
        assertParseFailure(parser, NotificationCommand.COMMAND_WORD + " "
                + INVALID_TOGGLE_TYPE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NotificationCommand.MESSAGE_USAGE) );

    }

    @Test
    public void parse_invalid_noTogglePrefix_throwsParseException() {
        assertParseFailure(parser, NotificationCommand.COMMAND_WORD + " " +
                INVALID_NOTIFICATION_TIP_ONLY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NotificationCommand.MESSAGE_USAGE) );
        assertParseFailure(parser, NotificationCommand.COMMAND_WORD + " " +
                INVALID_NOTIFICATION_WARNING_ONLY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NotificationCommand.MESSAGE_USAGE) );

    }

    @Test
    public void parse_valid_noNotificationTypePrefix() {
        NotificationCommandDescriptor descriptor = new NotificationCommandDescriptor();
        descriptor.setToggle(NotificationCommand.OPTION_ON);
        assertParseSuccess(parser, NotificationCommand.COMMAND_WORD + " " + VALID_NOTIFICATION_ON,
                new NotificationCommand(descriptor));
        descriptor.setToggle(NotificationCommand.OPTION_OFF);
        assertParseSuccess(parser, NotificationCommand.COMMAND_WORD + " " + VALID_NOTIFICATION_OFF,
                new NotificationCommand(descriptor));
    }

    @Test
    public void parse_valid_toggleWarning() {
        NotificationCommandDescriptor descriptor = new NotificationCommandDescriptor();
        descriptor.setNotificationType(NotificationCommand.OPTION_WARNING);
        descriptor.setToggle(NotificationCommand.OPTION_ON);
        assertParseSuccess(parser, NotificationCommand.COMMAND_WORD + " " + VALID_NOTIFICATION_WARNING_ON,
                new NotificationCommand(descriptor));
        descriptor.setToggle(NotificationCommand.OPTION_OFF);
        assertParseSuccess(parser, NotificationCommand.COMMAND_WORD + " " + VALID_NOTIFICATION_WARNING_OFF,
                new NotificationCommand(descriptor));
    }

    @Test
    public void parse_valid_toggleTip() {
        NotificationCommandDescriptor descriptor = new NotificationCommandDescriptor();
        descriptor.setNotificationType(NotificationCommand.OPTION_TIP);
        descriptor.setToggle(NotificationCommand.OPTION_ON);
        assertParseSuccess(parser, NotificationCommand.COMMAND_WORD + " " + VALID_NOTIFICATION_TIP_ON,
                new NotificationCommand(descriptor));
        descriptor.setToggle(NotificationCommand.OPTION_OFF);
        assertParseSuccess(parser, NotificationCommand.COMMAND_WORD + " " + VALID_NOTIFICATION_TIP_OFF,
                new NotificationCommand(descriptor));
    }


}
