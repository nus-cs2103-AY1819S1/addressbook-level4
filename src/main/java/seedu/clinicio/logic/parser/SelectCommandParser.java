package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.commands.SelectCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
