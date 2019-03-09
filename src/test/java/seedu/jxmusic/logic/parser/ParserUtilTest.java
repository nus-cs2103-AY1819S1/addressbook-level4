package seedu.jxmusic.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.jxmusic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.jxmusic.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_PLAYLIST_NAME = "Rock&Roll";
    private static final String INVALID_TRACK_NAME = "Don't Stop Believin'";
    private static final String INVALID_TRACK_FILE_NOT_EXIST = "no track file";
    private static final String INVALID_TRACK_FILE_NOT_SUPPORTED = "unsupported";

    private static final String VALID_PLAYLIST_NAME = "Favourites";
    private static final String VALID_TRACK_1 = "Haikei Goodbye Sayonara";
    private static final String VALID_TRACK_2 = "Ihojin no Yaiba";
    private static final String VALID_TIME_1 = "1";
    private static final String VALID_TIME_2 = "1 2";
    private static final String VALID_TIME_3 = "1 2 3";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PLAYLIST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PLAYLIST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, Name.MESSAGE_NAME_CONSTRAINTS, () ->
                ParserUtil.parseName(INVALID_PLAYLIST_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_PLAYLIST_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_PLAYLIST_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_PLAYLIST_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_PLAYLIST_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTrack_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTrack(null);
    }

    @Test
    public void parseTrack_invalidName_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(Name.MESSAGE_NAME_CONSTRAINTS);
        ParserUtil.parseTrack(INVALID_TRACK_NAME);
    }

    @Test
    public void parseTrack_fileNotExist_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(Track.MESSAGE_FILE_NOT_EXIST);
        ParserUtil.parseTrack(INVALID_TRACK_FILE_NOT_EXIST);
    }

    @Test
    public void parseTrack_fileNotSupported_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(Track.MESSAGE_FILE_NOT_SUPPORTED);
        ParserUtil.parseTrack(INVALID_TRACK_FILE_NOT_SUPPORTED);
    }

    @Test
    public void parseTrack_validValueWithoutWhitespace_returnsTrack() throws Exception {
        Track expectedTrack = new Track(new Name(VALID_TRACK_1));
        assertEquals(expectedTrack, ParserUtil.parseTrack(VALID_TRACK_1));
    }

    @Test
    public void parseTrack_validValueWithWhitespace_returnsTrimmedTrack() throws Exception {
        String trackWithWhitespace = WHITESPACE + VALID_TRACK_1 + WHITESPACE;
        Track expectedTrack = new Track(new Name(VALID_TRACK_1));
        assertEquals(expectedTrack, ParserUtil.parseTrack(trackWithWhitespace));
    }

    @Test
    public void parseTracks_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTracks(null);
    }

    @Test
    public void parseTracks_collectionWithInvalidTracks_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTracks(Arrays.asList(VALID_TRACK_1, INVALID_TRACK_NAME));
    }

    @Test
    public void parseTracks_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTracks(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTracks_collectionWithValidTracks_returnsTrackList() throws Exception {
        List<Track> actualTrackList = ParserUtil.parseTracks(Arrays.asList(VALID_TRACK_1, VALID_TRACK_2));
        List<Track> expectedTrackList = new ArrayList<>(Arrays.asList(
                new Track(new Name(VALID_TRACK_1)), new Track(new Name(VALID_TRACK_2))));

        assertEquals(expectedTrackList, actualTrackList);
    }

    @Test
    public void parseTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTime(null);
    }

    @Test
    public void parseTime_invalidInputFormat_throwsParseException() throws Exception {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_TIME_FORMAT, () ->
                ParserUtil.parseTime("a"));
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_TIME_FORMAT, () ->
                ParserUtil.parseTime("1.2"));
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_TIME_FORMAT, () ->
                ParserUtil.parseTime("-1"));
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_TIME_FORMAT, () ->
                ParserUtil.parseTime("1 2 3 4"));
    }

    @Test
    public void parseTime_validInput_success() throws Exception {
        double expectedTime1 = 1000;
        double expectedTime2 = 62000;
        double expectedTime3 = 3723000;
        assertEquals(expectedTime1, ParserUtil.parseTime(VALID_TIME_1), 0);
        assertEquals(expectedTime2, ParserUtil.parseTime(VALID_TIME_2), 0);
        assertEquals(expectedTime3, ParserUtil.parseTime(VALID_TIME_3), 0);
    }
}

