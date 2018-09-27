package seedu.address.logic.parser;

import seedu.address.logic.commands.EnqueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EnqueueCommandParser implements Parser<EnqueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnqueueCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if(trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnqueueCommand.MESSAGE_USAGE));
        }

        return new EnqueueCommand(trimmedArgs);
    }
}
