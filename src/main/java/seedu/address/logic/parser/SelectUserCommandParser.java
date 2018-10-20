package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.SelectUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectUserCommand object
 */
public class SelectUserCommandParser implements Parser<SelectUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectUserCommand
     * and returns an SelectUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectUserCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectUserCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectUserCommand.MESSAGE_USAGE), pe);
        }
    }
}
