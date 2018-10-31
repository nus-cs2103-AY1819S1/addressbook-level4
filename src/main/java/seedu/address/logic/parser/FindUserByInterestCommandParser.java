package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.personcommands.FindUserByInterestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindUserByInterestCommand object
 */
public class FindUserByInterestCommandParser implements Parser<FindUserByInterestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindUserByInterestCommand
     * and returns an FindUserByInterestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindUserByInterestCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindUserByInterestCommand.MESSAGE_USAGE));
        }

        return new FindUserByInterestCommand(new InterestContainsKeywordsPredicate(trimmedArgs));
    }

}
