package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.restaurant.logic.commands.menu.FindItemCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.menu.NameContainsKeywordsPredicate;

//@@author yican95
/**
 * Parses input arguments and creates a new FindItemCommand object
 */
public class FindItemCommandParser implements Parser<FindItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindItemCommand
     * and returns an FindItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindItemCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindItemCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindItemCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
