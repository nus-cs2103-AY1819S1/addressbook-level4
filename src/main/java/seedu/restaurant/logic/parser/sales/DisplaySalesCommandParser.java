package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.logic.commands.sales.DisplaySalesCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.sales.Date;

/**
 * Parses input arguments and creates a new DisplaySalesCommand object
 */
public class DisplaySalesCommandParser implements Parser<DisplaySalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplaySalesCommand and returns an
     * DisplaySalesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplaySalesCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplaySalesCommand.MESSAGE_USAGE));
        }

        Date date = SalesParserUtil.parseDate(args);
        return new DisplaySalesCommand(date);
    }
}
