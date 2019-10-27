package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ContextCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a {@code ContextCommand}
 */
public class ContextCommandParser implements Parser<ContextCommand> {

    /**
     * Parses an input {@code String} in the context of a {@code ContextCommand}
     * and returns a {@code ContextCommand} for execution.
     *
     * @param args an input String representing the argument for the ContextCommand
     * @return a ContextCommand object, holding the given input as its argument.
     * @throws ParseException if the input String does not meet requirements for a valid ContextCommand argument
     */
    public ContextCommand parse(String args) throws ParseException {

        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContextCommand.MESSAGE_USAGE));
        }

        return new ContextCommand(trimmedArgs);
    }

}
