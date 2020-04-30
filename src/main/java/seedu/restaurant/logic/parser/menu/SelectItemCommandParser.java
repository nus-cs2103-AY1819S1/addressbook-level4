package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.SelectItemCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author yican95
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectItemCommandParser implements Parser<SelectItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectItemCommand
     * and returns an SelectItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectItemCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectItemCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectItemCommand.MESSAGE_USAGE), pe);
        }
    }
}
