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

import seedu.jxmusic.commons.core.index.Index;
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
    private List<Index> indexesToAdd = new ArrayList<>();

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
     * @return playlistToAdd: a copy of targetPlaylist with added tracks
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
    public void executeAddTrackIndexToPlaylist() {
        int trackIndex = 3;
        trackToAdd = model.getFilteredTrackList().get(trackIndex);
        Playlist oneTrack = addTracksToExpectedModel(model.getFilteredTrackList().get(trackIndex));
        indexesToAdd.add(Index.fromZeroBased(trackIndex));
        System.out.println(model.getFilteredTrackList());
        expectedModel = new ModelManager(getTestPlaylistLibrary(oneTrack), new UserPrefs());
        String successMessage = String.format(TrackAddCommand.MESSAGE_SUCCESS, trackToAdd, targetPlaylist.getName());
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, TrackAddCommand.InputType.INDEX, indexesToAdd);
        assertCommandSuccess(command, model, commandHistory, successMessage, expectedModel);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void executeAddTrackNameToPlaylist() {
        Playlist oneTrack = addTracksToExpectedModel(trackToAdd);
        expectedModel = new ModelManager(getTestPlaylistLibrary(oneTrack), new UserPrefs());
        String successMessage = String.format(TrackAddCommand.MESSAGE_SUCCESS, trackToAdd, targetPlaylist.getName());
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, TrackAddCommand.InputType.TRACK, trackToAdd);
        assertCommandSuccess(command, model, commandHistory, successMessage, expectedModel);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void executeAddTrackNamesToPlaylist() {
        Playlist multipleTracks = addTracksToExpectedModel(tracksToAdd);
        expectedModel = new ModelManager(getTestPlaylistLibrary(multipleTracks), new UserPrefs());
        String successMessage = String.format(TrackAddCommand.MESSAGE_SUCCESS, tracksToAdd, targetPlaylist.getName());
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, TrackAddCommand.InputType.TRACK, tracksToAdd);
        assertCommandSuccess(command, model, commandHistory, successMessage, expectedModel);
    }

    @Test
    public void executeAddTrackToNonExistentPlaylist() {
        expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
        targetPlaylist = new Playlist(new Name("playlistNameDoesNotExist"));
        TrackAddCommand command = new TrackAddCommand(targetPlaylist, TrackAddCommand.InputType.TRACK, tracksToAdd);
        assertCommandSuccess(command, model, commandHistory,
                String.format(TrackAddCommand.MESSAGE_PLAYLIST_DOES_NOT_EXIST,
                        targetPlaylist.getName()), expectedModel);
    }
}

