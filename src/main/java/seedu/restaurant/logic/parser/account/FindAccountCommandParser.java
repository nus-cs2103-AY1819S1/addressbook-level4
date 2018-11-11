package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.restaurant.logic.commands.account.FindAccountCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.account.UsernameContainsKeywordsPredicate;

//@@author AZhiKai

/**
 * Parses input arguments and creates a new {@code FindAccountCommand} object
 */
public class FindAccountCommandParser implements Parser<FindAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code FindAccountCommand} and returns an
     * {@code FindAccountCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAccountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAccountCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAccountCommand(new UsernameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
