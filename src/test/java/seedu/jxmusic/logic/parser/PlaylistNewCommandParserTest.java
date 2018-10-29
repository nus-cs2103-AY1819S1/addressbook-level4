package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_PLAYLIST_NAME_ARG;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_TRACK_FILE_NOT_EXIST_ARG;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_TRACK_FILE_NOT_SUPPORTED_ARG;
import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_TRACK_NAME_ARG;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PLAYLIST_NAME_ARG_ANIME;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PLAYLIST_NAME_ARG_SFX;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_NAME_ARG_BELL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_NAME_ARG_HAIKEI;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_NAME_ARG_IHOJIN;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_NAME_ARG_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_SFX;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_BELL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;

import org.junit.Test;

import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.testutil.PlaylistBuilder;

public class PlaylistNewCommandParserTest {
    private PlaylistNewCommandParser parser = new PlaylistNewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Playlist expectedPlaylist = new PlaylistBuilder(SFX).withTracks(VALID_TRACK_NAME_MARBLES).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PLAYLIST_NAME_ARG_SFX + TRACK_NAME_ARG_MARBLES,
                new PlaylistNewCommand(expectedPlaylist));

        // multiple playlist names - last name accepted
        assertParseSuccess(parser, PLAYLIST_NAME_ARG_ANIME + PLAYLIST_NAME_ARG_SFX + TRACK_NAME_ARG_MARBLES,
                new PlaylistNewCommand(expectedPlaylist));

        // multiple tracks - all accepted
        Playlist expectedPlaylistMultipleTracks = new PlaylistBuilder(SFX).withTracks(VALID_TRACK_NAME_MARBLES,
                VALID_TRACK_NAME_BELL)
                .build();
        assertParseSuccess(parser, PLAYLIST_NAME_ARG_SFX
                + TRACK_NAME_ARG_MARBLES + TRACK_NAME_ARG_BELL, new PlaylistNewCommand(expectedPlaylistMultipleTracks));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tracks
        Playlist expectedPlaylist = new PlaylistBuilder(SFX).withTracks().build();
        assertParseSuccess(parser, PLAYLIST_NAME_ARG_SFX,
                new PlaylistNewCommand(expectedPlaylist));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlaylistNewCommand.MESSAGE_USAGE);

        // missing playlist prefix
        assertParseFailure(parser, VALID_PLAYLIST_NAME_SFX,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid playlist name
        assertParseFailure(parser, INVALID_PLAYLIST_NAME_ARG
                + TRACK_NAME_ARG_HAIKEI + TRACK_NAME_ARG_IHOJIN, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid track name
        assertParseFailure(parser, PLAYLIST_NAME_ARG_ANIME
                + INVALID_TRACK_NAME_ARG + TRACK_NAME_ARG_BELL, Name.MESSAGE_NAME_CONSTRAINTS);

        // track file not exist
        assertParseFailure(parser, PLAYLIST_NAME_ARG_ANIME
                + INVALID_TRACK_FILE_NOT_EXIST_ARG + TRACK_NAME_ARG_BELL, Track.MESSAGE_FILE_NOT_EXIST);

        // track file not supported
        assertParseFailure(parser, PLAYLIST_NAME_ARG_ANIME
                + INVALID_TRACK_FILE_NOT_SUPPORTED_ARG + TRACK_NAME_ARG_BELL, Track.MESSAGE_FILE_NOT_SUPPORTED);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_PLAYLIST_NAME_ARG + INVALID_TRACK_FILE_NOT_EXIST_ARG,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PLAYLIST_NAME_ARG_ANIME + TRACK_NAME_ARG_BELL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlaylistNewCommand.MESSAGE_USAGE));
    }
}
