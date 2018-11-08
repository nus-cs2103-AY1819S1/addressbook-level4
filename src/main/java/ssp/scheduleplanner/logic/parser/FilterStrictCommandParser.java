package ssp.scheduleplanner.logic.parser;

import java.util.Arrays;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.FilterStrictCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;
import ssp.scheduleplanner.model.task.TagsContainsAllKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterStrictCommand object
 */
public class FilterStrictCommandParser implements Parser<FilterStrictCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterStrictCommand
     * and returns a FilterStrictCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterStrictCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FilterStrictCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FilterStrictCommand(new TagsContainsAllKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
