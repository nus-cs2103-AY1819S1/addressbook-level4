package seedu.jxmusic.logic.commands;

import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalTrackList.BELL;
import static seedu.jxmusic.testutil.TypicalTrackList.getTypicalLibrary;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.player.PlayableTrack;
import seedu.jxmusic.player.Player;
import seedu.jxmusic.player.PlayerManager;

public class SeekCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Player player;

    /**
     * Without calling {@code Platform.startup}, all tests fail with
     * {@code IllegalStateException: Toolkit not initialized"}
     * But calling {@code Platform.startup} more than once, it throws
     * {@code IllegalStateException: Toolkit already initialized"}
     *
     * This test class is skipped when run on travis and appveyor, as it does not work with either of them.
     * For each test method, both travis and appveyor throws:
     * MediaException: UNKNOWN : com.sun.media.jfxmedia.MediaException: Could not create player!
     * The exception is traced to be thrown from the MediaPlayer initialization in PlayableTrack.
     *
     * Suspect it is due to incompatibility with JavaFX media, see more (bottom of page):
     * https://www.oracle.com/technetwork/java/javase/jdk9certconfig-3761018.html
     *
     * So test class is skipped when MediaException is detected.
     */
    @BeforeClass
    public static void initToolkit() {
        try {
            Platform.startup(() -> {});
            new PlayableTrack(BELL);
            // dont know why thrown MediaException is not caught below so tests continue and fail
            // but caught on appveyor so tests skipped
            new MediaPlayer(new Media(BELL.getFile().toURI().toString()));
        } catch (IllegalStateException ex) {
            // do nothing
            // for some reason travis throws "IllegalStateException: Toolkit already initialized"
        } catch (MediaException ex) {
            Assume.assumeNoException(ex);
        }
    }

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        player = PlayerManager.getInstance();
        try {
            new PlayableTrack(BELL);
            new MediaPlayer(new Media(BELL.getFile().toURI().toString()));
        } catch (MediaException ex) {
            Assume.assumeNoException(ex);
        }
    }

    @Test
    public void execute_seekWithNoPlayingTrack_throwsCommandException() {
        SeekCommand command = new SeekCommand(new Duration(1));
        assertCommandFailure(command, model, commandHistory, SeekCommand.MESSAGE_NOT_PLAYING);
    }

    @Test
    public void execute_seekFromPausedTrack_success() {
        player.play(BELL);
        player.pause();
        SeekCommand command = new SeekCommand(new Duration(2));
        assertCommandSuccess(command, model, commandHistory, SeekCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_seekFromPlayingTrack_success() {
        player.play(BELL);
        SeekCommand command = new SeekCommand(new Duration(2));
        assertCommandSuccess(command, model, commandHistory, SeekCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_seekFromStoppedTrack_throwsCommandException() {
        player.play(BELL);
        player.stop();
        SeekCommand command = new SeekCommand(new Duration(2));
        assertCommandFailure(command, model, commandHistory, SeekCommand.MESSAGE_NOT_PLAYING);
    }
}
