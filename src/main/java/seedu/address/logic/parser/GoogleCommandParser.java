package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GoogleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GoogleCommand object
 */
public class GoogleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the GoogleCommand
     * and returns an GoogleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoogleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GoogleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoogleCommand.MESSAGE_USAGE), pe);
        }
    }
}
