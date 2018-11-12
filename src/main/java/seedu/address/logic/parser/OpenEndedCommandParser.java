package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.OpenEndedCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class OpenEndedCommandParser implements Parser<OpenEndedCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code OpenEndedTestCommand}
     * and returns an OpenEndedTest object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenEndedCommand parse(String args) throws ParseException {
        args = args.trim().replaceAll("\\s+", " ");
        String[] values = args.split(" ");
        char in = values[0].charAt(0);
        if (!(in == 'Y' || in == 'y' || in == 'N' || in == 'n')) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenEndedCommand.MESSAGE_USAGE));
        }
        return new OpenEndedCommand(in);
    }
}
