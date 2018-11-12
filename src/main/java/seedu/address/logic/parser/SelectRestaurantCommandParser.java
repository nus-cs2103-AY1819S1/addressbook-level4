package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectRestaurantCommand object
 */
public class SelectRestaurantCommandParser implements Parser<SelectRestaurantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectRestaurantCommand
     * and returns an SelectRestaurantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectRestaurantCommand parse(String args) throws ParseException {
        try {
            Index index = ParserRestaurantUtil.parseIndex(args);
            return new SelectRestaurantCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectRestaurantCommand.MESSAGE_USAGE), pe);
        }
    }
}
