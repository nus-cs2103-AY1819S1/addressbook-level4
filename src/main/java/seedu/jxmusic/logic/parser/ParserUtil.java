package seedu.jxmusic.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.commons.util.StringUtil;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
//import seedu.jxmusic.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String trackName} into a {@code Track}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code track} is invalid.<br/>
     * Possible exception messages:<br/>
     * Name.MESSAGE_NAME_CONSTRAINTS<br/>
     * Track.MESSAGE_FILE_NOT_EXIST<br/>
     * Track.MESSAGE_FILE_NOT_SUPPORTED
     */
    public static Track parseTrack(String trackName) throws ParseException {
        requireNonNull(trackName);
        String trimmedTrackName = trackName.trim();
        // if (!Track.isValidTrackFromFileName(trimmedTrackName)) {
        //     throw new ParseException(Track.MESSAGE_NAME_CONSTRAINTS);
        // }
        try {
            return new Track(new Name(trimmedTrackName));
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }
    }

    /**
     * Parses {@code Collection<String> trackNames} into a {@code List<Track>}.
     */
    public static List<Track> parseTracks(Collection<String> trackNames) throws ParseException {
        requireNonNull(trackNames);
        final List<Track> trackList = new ArrayList<>();
        for (String trackName : trackNames) {
            trackList.add(parseTrack(trackName));
        }
        return trackList;
    }

    /**
     * Parses a {@code String playlistName} into a {@code Playlist}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code playlist} is invalid.<br/>
     * Possible exception messages:<br/>
     * Name.MESSAGE_NAME_CONSTRAINTS<br/>
     * Playlist.MESSAGE_FILE_NOT_EXIST<br/>
     * Playlist.MESSAGE_FILE_NOT_SUPPORTED
     */
    public static Playlist parsePlaylist(String playlistName) throws ParseException {
        requireNonNull(playlistName);
        String trimmedPlaylistName = playlistName.trim();
        try {
            return new Playlist(new Name(trimmedPlaylistName));
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }
    }

    /**
     * Parse a {@code String seekTime} into a {@code Duration}.
     * Leading and trailing whitespace will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     * Possible exception messages:
     * Wrong time format, TIME is in the format of [[h ]m ]s each of which represents
     * a unit of time that will be summed up to get the time point.
     */
    public static double parseTime(String timeString) throws ParseException {
        requireNonNull(timeString);
        String trimmedTimeString = timeString.trim();
        double timeInSeconds = 0;
        int countSoFar = 0;
        Scanner s = new Scanner(trimmedTimeString);

        try {
            while (s.hasNext() && countSoFar < 3) {
                String currentToken = s.next();
                int currentValue = Integer.parseUnsignedInt(currentToken);
                timeInSeconds *= 60;
                timeInSeconds += currentValue;
                countSoFar++;
            }
            if (s.hasNextInt()) {
                throw new ParseException("wrong time format, at most 3 unsigned integers are allowed in input.");
            }
            return timeInSeconds * 1000;
        } catch (Exception e) {
            throw new ParseException("wrong time format, at most 3 unsigned integers are allowed in input.");
        }

    }
}
