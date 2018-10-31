package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewPermissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewPermissionCommand object
 */
public class ViewPermissionCommandParser implements Parser<ViewPermissionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPermissionCommand
     * and returns an ViewPermissionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewPermissionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewPermissionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPermissionCommand.MESSAGE_USAGE), pe);
        }
    }
}
