package seedu.jxmusic.logic.commands;

// imports
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibraryAfterTrackAdd;

import org.junit.Before;
import org.junit.Test;

import seedu.jxmusic.logic.CommandHistory;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.testutil.TypicalPlaylistList;

public class TrackDeleteCommandTest {
    private Model model;
    private Model expectedModel;
    private Model expectedUnchangedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Track trackToDelete;
    private Playlist targetPlaylist;

    @Before
    public void setUp() {
        trackToDelete = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = TypicalPlaylistList.TEST_ANIME;
        // setup library with the track to delete
        model = new ModelManager(getTypicalLibraryAfterTrackAdd(trackToDelete), new UserPrefs());
        expectedUnchangedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        // setup expected model with library that has track deleted
        expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
    }

    @Test
    public void execute_deleteTrackFromPlaylist() {
        assertCommandSuccess(new TrackDeleteCommand(trackToDelete, targetPlaylist), model, commandHistory,
                String.format(
                        TrackDeleteCommand.MESSAGE_SUCCESS, trackToDelete, targetPlaylist.getName()), expectedModel);
    }

    @Test
    public void execute_removeNonExistentTrackFromPlaylist() {
        trackToDelete = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = TypicalPlaylistList.ANIME;
        assertCommandSuccess(new TrackDeleteCommand(trackToDelete, targetPlaylist), model, commandHistory,
                String.format(TrackDeleteCommand.MESSAGE_TRACK_DOES_NOT_EXIST, trackToDelete), expectedUnchangedModel);
    }

    @Test
    public void execute_deleteTrackFromNonExistentPlaylist() {
        trackToDelete = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = new Playlist(new Name("playlistNameDoesNotExist"));
        assertCommandSuccess(new TrackDeleteCommand(trackToDelete, targetPlaylist), model, commandHistory,
                String.format(TrackDeleteCommand.MESSAGE_PLAYLIST_DOES_NOT_EXIST,
                        targetPlaylist.getName()), expectedUnchangedModel);
    }
}

