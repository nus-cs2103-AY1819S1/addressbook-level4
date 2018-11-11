package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.commands.TrackAddCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Parses input arguments and creates a new TrackAddCommand object
 */
public class TrackAddCommandParser implements Parser<TrackAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TrackAddCommand
     * and returns an TrackAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TrackAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYLIST, PREFIX_TRACK, PREFIX_INDEX);
        List<Track> tracksToAdd;
        ArrayList<Index> indexesToAdd;
        boolean isUsingIndex = false;
        boolean isUsingTrackName = false;

        if (arePrefixesPresent(argMultimap, PREFIX_TRACK)) {
            isUsingTrackName = true;
        }

        if (arePrefixesPresent(argMultimap, PREFIX_INDEX)) {
            isUsingIndex = true;
        }

        if (isUsingTrackName && isUsingIndex) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackAddCommand.MESSAGE_TOO_MANY_PREFIX));
        }


        if (!(arePrefixesPresent(argMultimap, PREFIX_PLAYLIST, PREFIX_TRACK)
                && arePrefixesPresent(argMultimap, PREFIX_PLAYLIST, PREFIX_INDEX))
                || !argMultimap.getPreamble().isEmpty()) {
            if (isUsingTrackName && !arePrefixesPresent(argMultimap, PREFIX_TRACK)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackAddCommand.MESSAGE_USAGE_TRACK));
            }
            if (isUsingIndex && !arePrefixesPresent(argMultimap, PREFIX_INDEX)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackAddCommand.MESSAGE_USAGE_INDEX));
            }
        }

        Playlist targetPlaylist = ParserUtil.parsePlaylist(argMultimap.getValue(PREFIX_PLAYLIST).get());

        if (isUsingTrackName) {
            tracksToAdd = ParserUtil.parseTracks(argMultimap.getAllValues(PREFIX_TRACK));
            return new TrackAddCommand(targetPlaylist, TrackAddCommand.InputType.TRACK, tracksToAdd);
        }
        if (isUsingIndex) {
            List<String> indexes = Arrays.asList(argMultimap.getValue(PREFIX_INDEX).get().split("\\s+"));
            indexesToAdd = ParserUtil.parseIndexes(indexes);
            return new TrackAddCommand(targetPlaylist, TrackAddCommand.InputType.INDEX, indexesToAdd);
        }
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
