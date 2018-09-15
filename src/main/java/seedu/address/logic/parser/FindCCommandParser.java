package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.QuestionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCCommand object
 */
public class FindCCommandParser implements Parser<FindCCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
