package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.parking.logic.commands.NotifyCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NotifyCommand object
 */
public class NotifyCommandParser implements Parser<NotifyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotifyCommand
     * and returns an NotifyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotifyCommand parse(String args) throws ParseException {
        try {
            int time = ParserUtil.parseTime(args);
            return new NotifyCommand(time);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotifyCommand.MESSAGE_USAGE), pe);
        }
    }
}
