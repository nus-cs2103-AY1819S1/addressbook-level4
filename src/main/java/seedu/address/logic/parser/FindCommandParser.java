package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_WAITING_TIME;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ride.RideContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new RideContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                hasAddress(nameKeywords)));
    }

    /**
     * Checks the given {@code String[]} to find if it contains PREFIX_ADDRESS returns
     * a boolean.
     * @param keywords the string array of keywords to check
     * @return whether the array contains PREFIX_ADDRESS
     */
    private boolean hasAddress(String[] keywords) {
        for (String keyword : keywords) {
            if (keyword.contentEquals(PREFIX_ADDRESS.getPrefix())) {
                return true;
            }
        }
        return false;
    }

}
