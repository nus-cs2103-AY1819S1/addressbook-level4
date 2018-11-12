package seedu.thanepark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.thanepark.logic.commands.AllCommandWords;
import seedu.thanepark.logic.commands.SuggestCommand;
import seedu.thanepark.logic.matchers.PatternMatcher;
import seedu.thanepark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SuggestCommand object.
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    private PatternMatcher patternMatcher;

    public SuggestCommandParser (PatternMatcher patternMatcher) {
        this.patternMatcher = patternMatcher;
    }

    /**
     * Parses the given {@code String} of pattern in the context of the SuggestCommand
     * and returns a SuggestCommand object for execution.
     * @throws ParseException if no matching commands to pattern is found or pattern is non-alphabetical.
     */
    @Override
    public SuggestCommand parse(String pattern) throws ParseException {
        requireNonNull(pattern);
        requireNonNull(patternMatcher);
        final String[] suggestions;

        if (!pattern.matches("[a-zA-Z]+")) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        suggestions = patternMatcher.matchPattern(AllCommandWords.COMMAND_WORDS, pattern);
        if (suggestions.length <= 0) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        return new SuggestCommand(suggestions);
    }
}
