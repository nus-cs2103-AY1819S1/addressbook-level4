package seedu.jxmusic.logic.commands;

// imports
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.logic.commands.CommandTestUtil.showPlaylistAtIndex;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;
import static seedu.jxmusic.testutil.TypicalPlaylists.getTypicalLibrary;

import org.junit.Before;
import org.junit.Test;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.Name;

import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.testutil.TypicalPlaylists;

public class TrackAddCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Track trackToAdd;
    private TypicalPlaylists targetPlaylist;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        trackToAdd = new Track(new Name("Marbles.mp3"));
        targetPlaylist = new TypicalPlaylists(new Name("Marbles playlist"));
    }

    @Test
    public void execute_addTrackToPlaylist() {
        assertCommandSuccess(new TrackAddCommand(trackToAdd, targetPlaylist), model, commandHistory,
                TrackAddCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPlaylistAtIndex(model, INDEX_FIRST_PLAYLIST);
        assertCommandSuccess(new TrackAddCommand(), model, commandHistory,
                TrackAddCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

