package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.jxmusic.logic.commands.TrackSearchCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.TrackNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TrackSearchCommandParser implements Parser<TrackSearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TrackSearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackSearchCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new TrackSearchCommand(new TrackNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
