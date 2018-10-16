package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.carpark.CarparkContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private String[] ignoreKeywords = new String[]{"blk"};
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

        String[] keywords = trimmedArgs.split("\\s+");
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i].toUpperCase();
        }

        List nameKeywords = new ArrayList<>(Arrays.asList(keywords));
        for (String ignore:ignoreKeywords) {
            nameKeywords.removeAll(Collections.singleton(ignore.toUpperCase()));
        }

        return new FindCommand(new CarparkContainsKeywordsPredicate(nameKeywords));
    }

}
