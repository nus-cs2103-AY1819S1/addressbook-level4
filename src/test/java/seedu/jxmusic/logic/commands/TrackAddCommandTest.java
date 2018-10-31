package seedu.jxmusic.logic.commands;

// imports
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylists.getTypicalLibrary;
import static seedu.jxmusic.testutil.TypicalPlaylists.getTypicalLibraryAfterTrackAdd;

import org.junit.Before;
import org.junit.Test;

import seedu.jxmusic.logic.CommandHistory;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.testutil.TypicalPlaylists;

public class TrackAddCommandTest {
    private Model model;
    private Model expectedModel;
    private Model expectedUnchangedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Track trackToAdd;
    private Playlist targetPlaylist;

    @Before
    public void setUp() {
        trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = TypicalPlaylists.ANIME;
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedUnchangedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalLibraryAfterTrackAdd(trackToAdd), new UserPrefs());
    }

    @Test
    public void execute_addTrackToPlaylist() {
        assertCommandSuccess(new TrackAddCommand(trackToAdd, targetPlaylist), model, commandHistory,
                TrackAddCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_addDuplicateTrackToPlaylist() {
        trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = TypicalPlaylists.SFX;
        assertCommandSuccess(new TrackAddCommand(trackToAdd, targetPlaylist), model, commandHistory,
                TrackAddCommand.MESSAGE_DUPLICATE_TRACK, expectedUnchangedModel);
    }

    @Test
    public void execute_addTrackToNonExistentPlaylist() {
        trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = new Playlist(new Name("playlistNameDoesNotExist"));
        assertCommandSuccess(new TrackAddCommand(trackToAdd, targetPlaylist), model, commandHistory,
                TrackAddCommand.MESSAGE_PLAYLIST_DOES_NOT_EXIST, expectedUnchangedModel);
    }
}

