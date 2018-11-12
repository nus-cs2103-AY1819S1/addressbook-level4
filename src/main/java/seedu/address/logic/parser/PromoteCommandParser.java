package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PromoteAllCommand;
import seedu.address.logic.commands.PromoteCommand;
import seedu.address.logic.commands.PromoteIndividualCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ParserCommand object.
 */
public class PromoteCommandParser implements Parser<PromoteCommand> {


    private static final String PROMOTE_INPUT_VALIDATION_REGEX = ("[[\\d]+[\\s]?]+");

    /**
     * Parses the given {@code String} of arguments in the context of the PromoteCommand
     * and returns an PromoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PromoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (!isValidInput(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PromoteCommand.MESSAGE_USAGE));
        }

        if (isPromoteTypeAll(trimmedArgs)) {
            return new PromoteAllCommand();
        } else {
            return new PromoteIndividualCommand(trimmedArgs);
        }
    }

    private boolean isPromoteTypeAll(String type) {
        return type.equals("all");
    }

    private boolean isValidInput (String args) {
        return !args.isEmpty() && (args.matches(PROMOTE_INPUT_VALIDATION_REGEX) || isPromoteTypeAll(args));
    }
}
