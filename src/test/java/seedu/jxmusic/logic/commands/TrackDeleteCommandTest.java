package seedu.jxmusic.logic.commands;

// imports
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTestPlaylistLibrary;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;

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

public class TrackDeleteCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Index index;
    private Track trackToDelete;
    private Playlist targetPlaylist;

    @Before
    public void setUp() {
        targetPlaylist = TypicalPlaylistList.TEST.copy();
        model = new ModelManager(getTestPlaylistLibrary(targetPlaylist), new UserPrefs());
        expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
    }

    @Test
    public void executeDeleteIndexFromPlaylist() {
        targetPlaylist.addTrack(new Track(new Name(VALID_TRACK_NAME_MARBLES)));
        Index lastTrackNum = Index.fromOneBased(targetPlaylist.getSize());
        trackToDelete = targetPlaylist.getTracks().get(lastTrackNum.getZeroBased());

        // setup library with the track to delete
        model = new ModelManager(getTestPlaylistLibrary(targetPlaylist), new UserPrefs());
        trackToDelete = targetPlaylist.getTracks().stream()
                .filter(Track -> Track.getFileNameWithoutExtension().equals(VALID_TRACK_NAME_MARBLES))
                .findFirst().get();
        index = targetPlaylist.getTrackIndex(trackToDelete);
        Command command = new TrackDeleteCommand(targetPlaylist, index);
        assertCommandSuccess(command, model, commandHistory,
                String.format(TrackDeleteCommand.MESSAGE_SUCCESS, trackToDelete,
                        targetPlaylist.getName()), expectedModel);
    }

    @Test
    public void executeDeleteNonExistentIndexFromPlaylist() {
        targetPlaylist = TypicalPlaylistList.ANIME;
        index = Index.fromZeroBased(targetPlaylist.getTracks().size() + 1);
        assertCommandSuccess(new TrackDeleteCommand(targetPlaylist, index), model, commandHistory,
                String.format(TrackDeleteCommand.MESSAGE_INDEX_DOES_NOT_EXIST,
                        index.getOneBased()), expectedModel);
    }

    @Test
    public void executeDeleteIndexFromNonExistentPlaylist() {
        trackToDelete = new Track(new Name(VALID_TRACK_NAME_MARBLES));
        targetPlaylist = new Playlist(new Name("playlistNameDoesNotExist"));
        index = Index.fromOneBased(1);
        assertCommandSuccess(new TrackDeleteCommand(targetPlaylist, index), model, commandHistory,
                String.format(TrackDeleteCommand.MESSAGE_PLAYLIST_DOES_NOT_EXIST,
                        targetPlaylist.getName()), expectedModel);
    }
}

