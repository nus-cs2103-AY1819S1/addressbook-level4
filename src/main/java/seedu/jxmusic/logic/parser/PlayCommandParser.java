package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.stream.Stream;

import seedu.jxmusic.logic.commands.PlayCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Parses input arguments and creates a new PlayCommand object
 */
public class PlayCommandParser implements Parser<PlayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlayCommand
     * and returns an PlayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlayCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYLIST, PREFIX_TRACK);

        // playlist prefix takes precedence over track prefix
        if (arePrefixesPresent(argMultimap, PREFIX_PLAYLIST)) {
            String playlistNameString = argMultimap.getValue(PREFIX_PLAYLIST).get();
            Playlist playlist = null;
            if (!playlistNameString.isEmpty()) {
                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYLIST).get());
                playlist = new Playlist(name);
            }
            return new PlayCommand(playlist);
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TRACK)) {
            String trackNameString = argMultimap.getValue(PREFIX_TRACK).get();
            Track track = null;
            if (!trackNameString.isEmpty()) {
                track = ParserUtil.parseTrack(argMultimap.getValue(PREFIX_TRACK).get());
            }
            return new PlayCommand(track);
        }

        return new PlayCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
