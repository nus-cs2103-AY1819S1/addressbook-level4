package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.parking.logic.commands.CalculateCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * To be added
 */
public class CalculateCommandParser implements Parser<CalculateCommand> {

    /**
     * To be added
     */
    public CalculateCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalculateCommand.MESSAGE_USAGE));
        }

        String[] flags = trimmedArgs.split("\\s+");

        return new CalculateCommand(flags);
    }
}
