package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * parses the input arguments and returns a SelectEntryCommand instance.
 */
public class SelectEntryCommandParser implements Parser<SelectEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectEntryCommand
     * and returns an SelectEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectEntryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectEntryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectEntryCommand.MESSAGE_USAGE), pe);
        }
    }
}
