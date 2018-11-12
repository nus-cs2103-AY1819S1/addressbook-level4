package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanAddCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanDeleteCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanFindCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanListCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanSelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses deliveryman input.
 */
public class DeliverymanCommandParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    /**
     * Parses the given {@code String} of arguments in the context of the DeliverymanCommand
     * and returns an DeliverymanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public DeliverymanCommand parse (String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case DeliverymanAddCommand.COMMAND_WORD:
            return new DeliverymanAddCommandParser().parse(arguments);

        case DeliverymanSelectCommand.COMMAND_WORD:
            return new DeliverymanSelectCommandParser().parse(arguments);

        case DeliverymanDeleteCommand.COMMAND_WORD:
            return new DeliverymanDeleteCommandParser().parse(arguments);

        case DeliverymanListCommand.COMMAND_WORD:
            return new DeliverymanListCommand();

        case DeliverymanFindCommand.COMMAND_WORD:
            return new DeliverymanFindCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
