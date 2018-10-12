package seedu.jxmusic.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.commons.util.StringUtil;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Name;
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
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Track parseTrack(String track) throws ParseException {
        requireNonNull(track);
        String trimmedTrack = track.trim();
//        if (!Track.isValidTrackName(trimmedTrack)) {
//            throw new ParseException(Track.MESSAGE_TRACK_CONSTRAINTS); // todo isValidTrackName.
//        }
        return new Track(new Name(track));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static List<Track> parseTrack(Collection<String> track) throws ParseException {
        requireNonNull(track);
        final List<Track> trackList = new ArrayList<>();
        for (String trackName : track) {
            trackList.add(parseTrack(trackName));
        }
        return trackList;
    }
}