package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.List;
import java.util.stream.Stream;

import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Parses input arguments and creates a new PlaylistNewCommand object
 */
public class PlaylistNewCommandParser implements Parser<PlaylistNewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlaylistNewCommand
     * and returns an PlaylistNewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlaylistNewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYLIST);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYLIST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlaylistNewCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYLIST).get());
        List<Track> trackList = ParserUtil.parseTracks(argMultimap.getAllValues(PREFIX_TRACK));

        Playlist playlist = new Playlist(name, trackList);

        return new PlaylistNewCommand(playlist);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
