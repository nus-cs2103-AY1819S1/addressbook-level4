package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindManagerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ManagerContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindManagerCommand object
 */
public class FindManagerCommandParser implements Parser<FindManagerCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindManagerCommand
     * and returns an FindManagerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindManagerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindManagerCommand.MESSAGE_USAGE));
        }

        String[] managerKeywords = trimmedArgs.split("\\s+");

        return new FindManagerCommand(new ManagerContainsKeywordsPredicate(Arrays.asList(managerKeywords)));
    }
}
