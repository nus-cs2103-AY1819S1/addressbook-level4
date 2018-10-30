package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns an HistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public HistoryCommand parse(String args) throws ParseException {
        String[] options = args.trim().split(" ");

        if (!matchesExpectedFormat(options)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HistoryCommand.MESSAGE_USAGE));
        }

        return new HistoryCommand(options);
    }

    /**
     * Returns true if matches "history" or "history more"
     */
    private static boolean matchesExpectedFormat(String[] options) {
        return options.length == 1 && options[0].isEmpty()
                || options.length == 1 && options[0].equals(HistoryCommand.MORE_INFO_FLAG);
    }

}
