package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectOtherCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectRestaurantCommand object
 */
public class SelectOtherCommandParser implements Parser<SelectOtherCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectRestaurantCommand
     * and returns an SelectRestaurantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectOtherCommand parse(String args) throws ParseException {
        try {
            Index index = ParserRestaurantUtil.parseIndex(args);
            return new SelectOtherCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectOtherCommand.MESSAGE_USAGE), pe);
        }
    }
}
