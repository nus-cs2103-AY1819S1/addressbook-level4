package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_ENTER_WORD;

import java.util.Arrays;

import seedu.learnvocabulary.logic.commands.ShowCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_ENTER_WORD, ShowCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new ShowCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
