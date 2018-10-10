package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input filter and creates a new ListCommand Object.
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ListCommand(ListCommand.ListType.SHOW_ALL);
        }

        String trimmedArgs = args.trim();

        /* Check if args match '-c' or '-u' */
        switch (trimmedArgs) {

        case ListCommand.SHOW_COMPLETED_COMMAND:
            return new ListCommand(ListCommand.ListType.SHOW_COMPLETED);

        case ListCommand.SHOW_UNCOMPLETED_COMMAND:
            return new ListCommand(ListCommand.ListType.SHOW_UNCOMPLETED);

        case "": /* Trailing white space */
            return new ListCommand(ListCommand.ListType.SHOW_ALL);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
