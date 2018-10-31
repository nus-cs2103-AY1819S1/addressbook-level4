package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.learnvocabulary.logic.commands.TriviaAnsCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.word.Meaning;

/**
 * Parses input and creates a new TriviaAnsCommand object
 */

public class TriviaAnsCommandParser implements Parser<TriviaAnsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TriviaAnsCommand
     * and returns an TriviaAnsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public TriviaAnsCommand parse(String args) throws ParseException {
        try {
            Meaning meaning = ParserUtil.parseMeaning(args);
            return new TriviaAnsCommand(meaning.toString());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TriviaAnsCommand.MESSAGE_USAGE, pe));
        }
    }
}
