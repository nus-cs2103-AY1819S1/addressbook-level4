//package seedu.jxmusic.logic.commands;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.showPlaylistAtIndex;
//import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;
//import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_SECOND_PLAYLIST;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;
//
//import org.junit.Test;
//
//import seedu.jxmusic.commons.core.Messages;
//import seedu.jxmusic.commons.core.index.Index;
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.ModelManager;
//import seedu.jxmusic.model.Playlist;
//import seedu.jxmusic.model.UserPrefs;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
// * {@code DeleteCommand}.
// */
//public class DeleteCommandTest {
//
//    private Model model = new ModelManager(getTypicalLibrary(), new UserPrefs());
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Playlist playlistToDelete = model.getFilteredPlaylistList().get(INDEX_FIRST_PLAYLIST.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLAYLIST);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PLAYLIST_SUCCESS, playlistToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
//        expectedModel.deletePlaylist(playlistToDelete);
//
//        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaylistList().size() + 1);
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showPlaylisAtIndex(model, INDEX_FIRST_PLAYLIST);
//
//        Playlist playlistToDelete = model.getFilteredPlaylistList().get(INDEX_FIRST_PLAYLIST.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLAYLIST);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PLAYLIST_SUCCESS, playlistToDelete);
//
//        Model expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
//        expectedModel.deletePlaylist(playlistToDelete);
//        showNoPlaylist(expectedModel);
//
//        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() {
//        showPlaylistAtIndex(model, INDEX_FIRST_PLAYLIST);
//
//        Index outOfBoundIndex = INDEX_SECOND_PLAYLIST;
//        // ensures that outOfBoundIndex is still in bounds of jxmusic book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibrary().getPlaylistList().size());
//
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
//    }
//
//    /**
//     * 1. Deletes a {@code Playlist} from a filtered list.
//     * 2. The unfiltered list should be shown now. Verify that the index of the previously deleted playlist in the
//     * unfiltered list is different from the index at the filtered list.
//     */
//
//    @Test
//    public void equals() {
//        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PLAYLIST);
//        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PLAYLIST);
//
//        // same object -> returns true
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
//
//        // same values -> returns true
//        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PLAYLIST);
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(deleteFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(deleteFirstCommand.equals(null));
//
//        // different playlist -> returns false
//        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
//    }
//
//    /**
//     * Updates {@code model}'s filtered list to show no one.
//     */
//    private void showNoPlaylist(Model model) {
//        model.updateFilteredPlaylistList(p -> false);
//
//        assertTrue(model.getFilteredPlaylistList().isEmpty());
//    }
//}
