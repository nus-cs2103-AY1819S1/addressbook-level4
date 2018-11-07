package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.PromoteAllCommand;
import seedu.address.logic.commands.PromoteCommand;
import seedu.address.logic.commands.PromoteIndividualCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ParserCommand object.
 */
public class PromoteCommandParser implements Parser<PromoteCommand> {


    private static final String PROMOTE_INPUT_VALIDATION_REGEX = ("[[\\d]+[\\s]?]+");

    private static final Logger logger = LogsCenter.getLogger(PromoteCommandParser.class);

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
            logger.info("=============================[ PROMOTE ALL COMMAND ]===========================");
            return new PromoteAllCommand();
        } else {
            logger.info("=============================[ PROMOTE INDIVIDUAL COMMAND ]===========================");
            logger.info("============================= [input: " + trimmedArgs + " ]=============================");
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
