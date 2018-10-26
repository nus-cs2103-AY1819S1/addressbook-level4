package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.jxmusic.logic.commands.PlaylistSearchCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new PlaylistSearchCommand object
 */
public class PlaylistSearchCommandParser implements Parser<PlaylistSearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlaylistSearchCommand
     * and returns a PlaylistSearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlaylistSearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlaylistSearchCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new PlaylistSearchCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
