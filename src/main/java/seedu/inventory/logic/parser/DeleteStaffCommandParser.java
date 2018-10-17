package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.DeleteStaffCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteStaffCommand object
 */
public class DeleteStaffCommandParser implements Parser<DeleteStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStaffCommand
     * and returns a DeleteStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStaffCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteStaffCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStaffCommand.MESSAGE_USAGE), pe);
        }
    }
}
