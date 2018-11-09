package systemtests;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jxmusic.testutil.TypicalPlaylistList.KEYWORD_MATCHING_MUSIC;

import org.junit.Test;

import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;

public class ClearCommandSystemTest extends LibrarySystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty jxmusic book, command with leading spaces and trailing special characters with / and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_PHRASE + " !/@#   ");
        assertSelectedCardUnchanged();

        /* Case: filters the playlist list before clearing -> entire jxmusic book cleared */
        executeCommand(PlaylistNewCommand.COMMAND_PHRASE + " p/placeholder");
        executeCommand(PlaylistNewCommand.COMMAND_PHRASE + " p/anime music");
        showPlaylistsWithName(KEYWORD_MATCHING_MUSIC);
        assertCommandSuccess(ClearCommand.COMMAND_PHRASE);
        assertSelectedCardUnchanged();

        /* Case: clear empty jxmusic book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_PHRASE);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     *
     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     *
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
