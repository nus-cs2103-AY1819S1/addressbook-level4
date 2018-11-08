package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ToggleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ToggleCommand object
 */
public class ToggleCommandParser implements  Parser<ToggleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ToggleCommand
     * and returns a ToggleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ToggleCommand parse(String args) throws ParseException {

        if (!isNull(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleCommand.MESSAGE_USAGE));
        }   else {
            return new ToggleCommand();
        }
    }
}
