package seedu.address.logic.parser;

import seedu.address.logic.commands.EarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EarningsCommand object
 */
public class EarningsCommandParser implements Parser<EarningsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EarningsCommand
     * and returns an EarningsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EarningsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        return new EarningsCommand(trimmedArgs);
    }
}
