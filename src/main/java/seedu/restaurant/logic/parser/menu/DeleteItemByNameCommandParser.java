package seedu.restaurant.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.logic.commands.menu.DeleteItemByNameCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.menu.Name;

//@@author yican95
/**
 * Parses input arguments and creates a new DeleteItemByNameCommand object
 */
public class DeleteItemByNameCommandParser implements Parser<DeleteItemByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteItemByNameCommand
     * and returns an DeleteItemByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteItemByNameCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Name name;
        try {
            name = ItemParserUtil.parseName(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemByNameCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteItemByNameCommand(name);
    }

}
