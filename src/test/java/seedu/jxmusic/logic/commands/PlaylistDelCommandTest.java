package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.logic.commands.CommandTestUtil.showPlaylistAtIndex;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_SECOND_PLAYLIST;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;

import org.junit.Test;

import seedu.jxmusic.commons.core.Messages;
import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code PlaylistDelCommand}.
 */
public class PlaylistDelCommandTest {

    private Model model = new ModelManager(getTypicalLibrary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Playlist playlistToDelete = model.getFilteredPlaylistList().get(INDEX_FIRST_PLAYLIST.getZeroBased());
        PlaylistDelCommand playlistDelCommand = new PlaylistDelCommand(INDEX_FIRST_PLAYLIST);

        String expectedMessage = String.format(PlaylistDelCommand.MESSAGE_DELETE_PLAYLIST_SUCCESS, playlistToDelete);

        ModelManager expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        expectedModel.deletePlaylist(playlistToDelete);

        assertCommandSuccess(playlistDelCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaylistList().size() + 1);
        PlaylistDelCommand playlistDelCommand = new PlaylistDelCommand(outOfBoundIndex);

        assertCommandFailure(playlistDelCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlaylistAtIndex(model, INDEX_FIRST_PLAYLIST);

        Playlist playlistToDelete = model.getFilteredPlaylistList().get(INDEX_FIRST_PLAYLIST.getZeroBased());
        PlaylistDelCommand playlistDelCommand = new PlaylistDelCommand(INDEX_FIRST_PLAYLIST);

        String expectedMessage = String.format(PlaylistDelCommand.MESSAGE_DELETE_PLAYLIST_SUCCESS, playlistToDelete);

        Model expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        expectedModel.deletePlaylist(playlistToDelete);
        showNoPlaylist(expectedModel);

        assertCommandSuccess(playlistDelCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlaylistAtIndex(model, INDEX_FIRST_PLAYLIST);

        Index outOfBoundIndex = INDEX_SECOND_PLAYLIST;
        // ensures that outOfBoundIndex is still in bounds of jxmusic book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibrary().getPlaylistList().size());

        PlaylistDelCommand deleteCommand = new PlaylistDelCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
    }

    /**
     * 1. Deletes a {@code Playlist} from a filtered list.
     * 2. The unfiltered list should be shown now. Verify that the index of the previously deleted playlist in the
     * unfiltered list is different from the index at the filtered list.
     */

    @Test
    public void equals() {
        PlaylistDelCommand deleteFirstCommand = new PlaylistDelCommand(INDEX_FIRST_PLAYLIST);
        PlaylistDelCommand deleteSecondCommand = new PlaylistDelCommand(INDEX_SECOND_PLAYLIST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        PlaylistDelCommand deleteFirstCommandCopy = new PlaylistDelCommand(INDEX_FIRST_PLAYLIST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different playlist -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPlaylist(Model model) {
        model.updateFilteredPlaylistList(p -> false);

        assertTrue(model.getFilteredPlaylistList().isEmpty());
    }
}
