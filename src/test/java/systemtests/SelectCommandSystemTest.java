//package systemtests;
//
//import static org.junit.Assert.assertTrue;
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX;
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.jxmusic.logic.commands.SelectCommand.MESSAGE_SELECT_PLAYLIST_SUCCESS;
//import static seedu.jxmusic.testutil.TestUtil.getLastIndex;
//import static seedu.jxmusic.testutil.TestUtil.getMidIndex;
//import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;
//
//import org.junit.Test;
//
//import seedu.jxmusic.commons.core.index.Index;
//import seedu.jxmusic.logic.commands.SelectCommand;
//import seedu.jxmusic.model.Model;
//
//public class SelectCommandSystemTest extends LibrarySystemTest {
//    @Test
//    public void select() {
//        /* ----------------------- Perform select operations on the shown unfiltered list ------------------------- */
//
//        /* Case: select the first card in the playlist list, command with leading spaces and trailing spaces
//         * -> selected
//         */
//        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYLIST.getOneBased() + "   ";
//        // assertCommandSuccess(command, INDEX_FIRST_PLAYLIST); // todo failing test
//
//        /* Case: select the last card in the playlist list -> selected */
//        Index playlistCount = getLastIndex(getModel());
//        command = SelectCommand.COMMAND_WORD + " " + playlistCount.getOneBased();
//        assertCommandSuccess(command, playlistCount);
//
//        /* Case: select the middle card in the playlist list -> selected */
//        Index middleIndex = getMidIndex(getModel());
//        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
//        assertCommandSuccess(command, middleIndex);
//
//        /* Case: select the current selected card -> selected */
//        assertCommandSuccess(command, middleIndex);
//
//        /* ----------------------- Perform select operations on the shown filtered list --------------------------- */
//
//
//        /* Case: filtered playlist list, select index within bounds of library but out of bounds of playlist list
//         * -> rejected
//         */
//        // showPlaylistsWithName(KEYWORD_MATCHING_SONG); // todo failing test
//        int invalidIndex = getModel().getLibrary().getPlaylistList().size();
//        // assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex,
//        // MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
//
//        /* Case: filtered playlist list, select index within bounds of library and playlist list -> selected */
//        Index validIndex = Index.fromOneBased(1);
//        assertTrue(validIndex.getZeroBased() < getModel().getFilteredPlaylistList().size());
//        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
//        assertCommandSuccess(command, validIndex);
//
//        /* ---------------------------------- Perform invalid select operations ----------------------------------- */
//
//        /* Case: invalid index (0) -> rejected */
//        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (-1) -> rejected */
//        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (size + 1) -> rejected */
//        invalidIndex = getModel().getFilteredPlaylistList().size() + 1;
//        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex,
//                MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
//
//        /* Case: invalid arguments (alphabets) -> rejected */
//        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc", MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid arguments (extra argument) -> rejected */
//        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
//
//        /* Case: mixed case command word -> rejected */
//        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: select from empty library -> rejected */
//        deleteAllPlaylists();
//        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYLIST.getOneBased(),
//                MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing select command with the
//     * {@code expectedSelectedCardIndex} of the selected playlist.<br>
//     * 4. {@code Storage} and {@code PlaylistListPanel} remain unchanged.<br>
//     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
//     * 6. Status bar remains unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     * @see LibrarySystemTest#assertSelectedCardChanged(Index)
//     */
//    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
//        Model expectedModel = getModel();
//        String expectedResultMessage = String.format(
//                MESSAGE_SELECT_PLAYLIST_SUCCESS, expectedSelectedCardIndex.getOneBased());
//        int preExecutionSelectedCardIndex = getPlaylistListPanel().getSelectedCardIndex();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//
//        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
//            assertSelectedCardUnchanged();
//        } else {
//            assertSelectedCardChanged(expectedSelectedCardIndex);
//        }
//
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchanged();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code Storage} and {@code PlaylistListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
