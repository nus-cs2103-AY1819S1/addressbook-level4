package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.NotificationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NotificationCommand object
 */

public class NotificationCommandParser implements Parser<NotificationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotificationCommand
     * and returns a Notification object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotificationCommand parse(String args) throws ParseException {
        requireNonNull(args);

        args = args.trim();

        if (!args.equals("enable") && !args.equals("disable")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotificationCommand.MESSAGE_USAGE));
        } else if (args.equals("enable")) {
            return new NotificationCommand(true);
        } else {
            return new NotificationCommand(false);
        }
    }
}
