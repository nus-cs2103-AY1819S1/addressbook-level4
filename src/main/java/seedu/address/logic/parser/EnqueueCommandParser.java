package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EnqueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create an EnqueueCommand object.
 */
public class EnqueueCommandParser implements Parser<EnqueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EnqueueCommand
     * and returns an EnqueueCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EnqueueCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnqueueCommand.COMMAND_USAGE), pe);
        }

        return new EnqueueCommand(index);
    }
}
