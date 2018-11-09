package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.sales.DeleteSalesCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author HyperionNKJ
/**
 * Parses input arguments and creates a new DeleteSalesCommand object
 */
public class DeleteSalesCommandParser implements Parser<DeleteSalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSalesCommand and returns an
     * DeleteSalesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSalesCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteSalesCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSalesCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
