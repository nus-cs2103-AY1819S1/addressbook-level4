package seedu.learnvocabulary.logic.parser;

import java.util.Arrays;

import seedu.learnvocabulary.logic.commands.ShowGroupCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//@@author Harryqu123

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ShowGroupCommandParser implements Parser<ShowGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowGroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        return new ShowGroupCommand(new TagContainsKeywordsPredicate(Arrays.asList(trimmedArgs)));
    }

}
//@@author