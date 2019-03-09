package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.commands.PlaylistDelCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PlaylistDelCommand object
 */
public class PlaylistDelCommandParser implements Parser<PlaylistDelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlaylistDelCommand
     * and returns an PlaylistDelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlaylistDelCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PlaylistDelCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlaylistDelCommand.MESSAGE_USAGE), pe);
        }
    }

}
