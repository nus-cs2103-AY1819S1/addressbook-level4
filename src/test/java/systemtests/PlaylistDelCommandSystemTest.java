package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jxmusic.logic.commands.PlaylistDelCommand.MESSAGE_DELETE_PLAYLIST_SUCCESS;
import static seedu.jxmusic.testutil.TestUtil.getLastIndex;
import static seedu.jxmusic.testutil.TestUtil.getMidIndex;
import static seedu.jxmusic.testutil.TestUtil.getPlaylist;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;
import static seedu.jxmusic.testutil.TypicalPlaylistList.KEYWORD_MATCHING_MUSIC;

import org.junit.Test;

import seedu.jxmusic.commons.core.Messages;
import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.commands.PlaylistDelCommand;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

public class PlaylistDelCommandSystemTest extends LibrarySystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PlaylistDelCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown --------------------
 */

        /* Case: delete the first playlist in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + PlaylistDelCommand.COMMAND_PHRASE + "      "
                + INDEX_FIRST_PLAYLIST.getOneBased() + "     ";
        Playlist deletedPlaylist = removePlaylist(expectedModel, INDEX_FIRST_PLAYLIST);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PLAYLIST_SUCCESS, deletedPlaylist);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last playlist in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPlaylistIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPlaylistIndex);

        /* Case: delete the middle playlist in the list -> deleted */
        Index middlePlaylistIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePlaylistIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ----------------------
 */

        /* Case: filtered playlist list, delete index within bounds of jxmusic book and playlist list -> deleted */
        showPlaylistsWithName(KEYWORD_MATCHING_MUSIC);
        Index index = INDEX_FIRST_PLAYLIST;
        assertTrue(index.getZeroBased() < getModel().getFilteredPlaylistList().size());
        assertCommandSuccess(index);

        /* Case: filtered playlist list, delete index within bounds of jxmusic book but out of bounds of playlist list
         * -> rejected
         */
        showPlaylistsWithName(KEYWORD_MATCHING_MUSIC);
        int invalidIndex = getModel().getLibrary().getPlaylistList().size();
        command = PlaylistDelCommand.COMMAND_PHRASE + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid delete operation ------------------------------------
 */

        /* Case: invalid index (0) -> rejected */
        command = PlaylistDelCommand.COMMAND_PHRASE + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = PlaylistDelCommand.COMMAND_PHRASE + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getLibrary().getPlaylistList().size() + 1);
        command = PlaylistDelCommand.COMMAND_PHRASE + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(PlaylistDelCommand.COMMAND_PHRASE + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(PlaylistDelCommand.COMMAND_PHRASE + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Playlist} at the specified {@code index} in {@code model}'s jxmusic book.
     * @return the removed playlist
     */
    private Playlist removePlaylist(Model model, Index index) {
        Playlist targetPlaylist = getPlaylist(model, index);
        model.deletePlaylist(targetPlaylist);
        return targetPlaylist;
    }

    /**
     * Deletes the playlist at {@code toDelete} by creating a default {@code PlaylistDelCommand}
 using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see PlaylistDelCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Playlist deletedPlaylist = removePlaylist(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PLAYLIST_SUCCESS, deletedPlaylist);

        assertCommandSuccess(
                PlaylistDelCommand.COMMAND_PHRASE + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser
 url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex
 }.
     * @see PlaylistDelCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see LibrarySystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
