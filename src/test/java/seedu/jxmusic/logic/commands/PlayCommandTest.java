package seedu.jxmusic.logic.commands;

import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.EMPTY;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;
import static seedu.jxmusic.testutil.TypicalTrackList.BELL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Platform;
import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.player.Player;
import seedu.jxmusic.player.PlayerManager;

public class PlayCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Player player;

    @BeforeClass
    public static void initToolkit() {
        // without this line, all tests fail with IllegalStateException: Toolkit not initialized
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ex) {
            // do nothing
        }
    }

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        player = PlayerManager.getInstance();
    }

    @Test
    public void execute_pausedTrack_success() {
        player.play(BELL);
        player.pause();
        PlayCommand command = new PlayCommand();
        assertCommandSuccess(command, model, commandHistory,
                PlayCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_playDefaultPlaylist_success() {
        Playlist playlist = null;
        PlayCommand command = new PlayCommand(playlist);
        assertCommandSuccess(command, model, commandHistory,
                PlayCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_playSpecificNonEmptyPlaylist_success() {
        PlayCommand command = new PlayCommand(SFX);
        assertCommandSuccess(command, model, commandHistory,
                PlayCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_playSpecificEmptyPlaylist_success() {
        PlayCommand command = new PlayCommand(EMPTY);
        assertCommandFailure(command, model, commandHistory,
                PlayCommand.MESSAGE_PLAYLIST_EMPTY);
    }

    @Test
    public void execute_playDefaultTrack_success() {
        Track track = null;
        PlayCommand command = new PlayCommand(track);
        assertCommandSuccess(command, model, commandHistory,
                PlayCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_playSpecificTrack_success() {
        PlayCommand command = new PlayCommand(BELL);
        assertCommandSuccess(command, model, commandHistory,
                PlayCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
