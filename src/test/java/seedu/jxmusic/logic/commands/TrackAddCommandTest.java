package seedu.jxmusic.logic.commands;

// imports
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibraryAfterTrackAdd;

import java.util.ArrayList;
import java.util.List;

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

public class TrackAddCommandTest {
    private Model model;
    private Model expectedModel;
    private Model expectedUnchangedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private List<Track> tracksToAdd;
    private Playlist targetPlaylist;

    @Before
    public void setUp() {
        Track trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        tracksToAdd = new ArrayList<Track>();
        tracksToAdd.add(trackToAdd);
        targetPlaylist = TypicalPlaylistList.ANIME;
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedUnchangedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalLibraryAfterTrackAdd(trackToAdd), new UserPrefs());
    }

    // No need to test if trackToAdd exists: Non-existent track handled by Track class

    @Test
    public void execute_addTrackToPlaylist() {
        assertCommandSuccess(new TrackAddCommand(tracksToAdd, targetPlaylist), model, commandHistory,
                String.format(TrackAddCommand.MESSAGE_SUCCESS, tracksToAdd, targetPlaylist.getName()), expectedModel);
    }

    @Test
    public void execute_addDuplicateTrackToPlaylist() {
        Track trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        tracksToAdd.add(trackToAdd);
        targetPlaylist = TypicalPlaylistList.SFX;
        assertCommandSuccess(new TrackAddCommand(tracksToAdd, targetPlaylist), model, commandHistory,
                String.format(TrackAddCommand.MESSAGE_DUPLICATE_TRACK, tracksToAdd), expectedUnchangedModel);
    }

    @Test
    public void execute_addTrackToNonExistentPlaylist() {
        Track trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        tracksToAdd.add(trackToAdd);
        targetPlaylist = new Playlist(new Name("playlistNameDoesNotExist"));
        assertCommandSuccess(new TrackAddCommand(tracksToAdd, targetPlaylist), model, commandHistory,
                String.format(TrackAddCommand.MESSAGE_PLAYLIST_DOES_NOT_EXIST,
                        targetPlaylist.getName()), expectedUnchangedModel);
    }
}

