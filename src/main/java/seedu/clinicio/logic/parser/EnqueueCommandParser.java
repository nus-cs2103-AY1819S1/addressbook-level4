package seedu.clinicio.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.commands.EnqueueCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;


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
