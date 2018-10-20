package seedu.lostandfound.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

import seedu.lostandfound.MainApp;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.logic.commands.FindResolvedCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.article.ResolvedAndContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindAllCommand object
 */
public class FindResolvedCommandParser implements Parser<FindResolvedCommand> {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindAllCommand
     * and returns an FindAllCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindResolvedCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new FindResolvedCommand(new ResolvedAndContainsKeywordsPredicate(Collections.emptyList()));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        logger.info((Arrays.toString(nameKeywords)));
        return new FindResolvedCommand(new ResolvedAndContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
