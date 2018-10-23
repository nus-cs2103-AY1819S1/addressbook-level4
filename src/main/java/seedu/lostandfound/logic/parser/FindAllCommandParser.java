package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.lostandfound.MainApp;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.logic.commands.FindAllCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.article.ContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindAllCommand object
 */
public class FindAllCommandParser implements Parser<FindAllCommand> {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindAllCommand
     * and returns an FindAllCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAllCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAllCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        logger.info((Arrays.toString(nameKeywords)));
        return new FindAllCommand(new ContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
