package seedu.jxmusic.logic.commands;

// imports
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTestPlaylistLibrary;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;

import java.util.ArrayList;
import java.util.Arrays;
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
    private CommandHistory commandHistory = new CommandHistory();
    private List<Track> tracksToAdd = new ArrayList<Track>();
    private Track trackToAdd;
    private Playlist targetPlaylist;

    @Before
    public void setUp() {
        trackToAdd = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        initTracksToAdd();
        targetPlaylist = TypicalPlaylistList.TEST;
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
    }

    private void initTracksToAdd() {
        tracksToAdd.add(new Track(new Name(VALID_TRACK_NAME_MARBLES)));
        tracksToAdd.add(new Track(new Name(VALID_TRACK_NAME_IHOJIN)));
    }

    /**
     * @param tracksToAdd each tested track that is required to be added into model
     * @return
     */
    private Playlist addTracksToExpectedModel(List<Track> tracksToAdd) {
        Playlist playlistToAdd = targetPlaylist.copy();
        for (Track track : tracksToAdd) {
            playlistToAdd.addTrack(track);
        }
        return playlistToAdd;
    }

    /**
     * see above
     */
    private Playlist addTracksToExpectedModel(Track... tracks) {
        return addTracksToExpectedModel(Arrays.asList(tracks));
    }

    // No need to test if trackToAdd exists: Non-existent track handled by Track class

    @Test
    public void execute_addTrackToPlaylist() {
        Playlist oneTrack = addTracksToExpectedModel(trackToAdd);
        expectedModel = new ModelManager(getTestPlaylistLibrary(oneTrack), new UserPrefs());
        String successMessage = String.format(TrackAddCommand.MESSAGE_SUCCESS, trackToAdd, targetPlaylist.getName());
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, trackToAdd);
        assertCommandSuccess(command, model, commandHistory, successMessage, expectedModel);
    }

    @Test
    public void execute_addTracksToPlaylist() {
        Playlist multipleTracks = addTracksToExpectedModel(tracksToAdd);
        expectedModel = new ModelManager(getTestPlaylistLibrary(multipleTracks), new UserPrefs());
        String successMessage = String.format(TrackAddCommand.MESSAGE_SUCCESS, tracksToAdd, targetPlaylist.getName());
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, tracksToAdd);
        assertCommandSuccess(command, model, commandHistory, successMessage, expectedModel);
    }

    @Test
    public void execute_addTrackToNonExistentPlaylist() {
        expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
        targetPlaylist = new Playlist(new Name("playlistNameDoesNotExist"));
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, tracksToAdd);
        assertCommandSuccess(command, model, commandHistory,
                String.format(TrackAddCommand.MESSAGE_PLAYLIST_DOES_NOT_EXIST,
                        targetPlaylist.getName()), expectedModel);
    }
}

