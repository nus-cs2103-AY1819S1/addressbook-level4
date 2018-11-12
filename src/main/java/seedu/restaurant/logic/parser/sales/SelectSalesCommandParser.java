package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.sales.SelectSalesCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author HyperionNKJ
/**
 * Parses input arguments and creates a new SelectSalesCommand object
 */
public class SelectSalesCommandParser implements Parser<SelectSalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectSalesCommand
     * and returns an SelectSalesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectSalesCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectSalesCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectSalesCommand.MESSAGE_USAGE), pe);
        }
    }
}
