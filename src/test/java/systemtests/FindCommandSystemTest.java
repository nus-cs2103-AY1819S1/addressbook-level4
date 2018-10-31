//package systemtests;
//
//import static org.junit.Assert.assertFalse;
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.ANIME;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.KEYWORD_MATCHING_SONG;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import seedu.jxmusic.commons.core.index.Index;
//import seedu.jxmusic.logic.commands.DeleteCommand;
//import seedu.jxmusic.logic.commands.FindCommand;
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.tag.Tag;
//
//public class FindCommandSystemTest extends LibrarySystemTest {
//
//    @Test
//    public void find() {
//        /* Case: find multiple persons in jxmusic book, command with leading spaces and trailing spaces
//         * -> 2 persons found
//         */
//        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SONG + "   ";
//        Model expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, ANIME, SFX); // first names of Benson and Daniel are "Meier"
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: repeat previous find command where playlist list is displaying the persons we are finding
//         * -> 2 persons found
//         */
//        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SONG;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find playlist where playlist list is not displaying the playlist we are finding -> 1 playlist found
//  */
//        command = FindCommand.COMMAND_WORD + " Carl";
//        ModelHelper.setFilteredList(expectedModel, SFX);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in jxmusic book, 2 keywords -> 2 persons found */
//        command = FindCommand.COMMAND_WORD + " Benson Daniel";
//        ModelHelper.setFilteredList(expectedModel, ANIME, SFX);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in jxmusic book, 2 keywords in reversed order -> 2 persons found */
//        command = FindCommand.COMMAND_WORD + " Daniel Benson";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in jxmusic book, 2 keywords with 1 repeat -> 2 persons found */
//        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in jxmusic book, 2 matching keywords and 1 non-matching keyword
//         * -> 2 persons found
//         */
//        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: undo previous find command -> rejected */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: redo previous find command -> rejected */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: find same persons in jxmusic book after deleting 1 of them -> 1 playlist found */
//        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
//        assertFalse(getModel().getAddressBook().getPlaylistList().contains(ANIME));
//        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SONG;
//        expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, SFX);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find playlist in jxmusic book, keyword is same as name but of different case -> 1 playlist found */
//        command = FindCommand.COMMAND_WORD + " MeIeR";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find playlist in jxmusic book, keyword is substring of name -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " Mei";
//        ModelHelper.setFilteredList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find playlist in jxmusic book, name is substring of keyword -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " Meiers";
//        ModelHelper.setFilteredList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find playlist not in jxmusic book -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " Mark";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find phone number of playlist in jxmusic book -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " " + SFX.getPhone().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find jxmusic of playlist in jxmusic book -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " " + SFX.getAddress().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find email of playlist in jxmusic book -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " " + SFX.getEmail().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find tags of playlist in jxmusic book -> 0 persons found */
//        List<Tag> tags = new ArrayList<>(SFX.getTags());
//        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find while a playlist is selected -> selected card deselected */
//        showAllPersons();
//        selectPerson(Index.fromOneBased(1));
//        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(SFX.getName().nameString));
//        command = FindCommand.COMMAND_WORD + " Daniel";
//        ModelHelper.setFilteredList(expectedModel, SFX);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardDeselected();
//
//        /* Case: find playlist in empty jxmusic book -> 0 persons found */
//        deleteAllPersons();
//        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SONG;
//        expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, SFX);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: mixed case command word -> rejected */
//        command = "FiNd Meier";
//        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    /**
//     * Executes {@code command} and verifies that the command box displays an empty string, the result display
//     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
//     * and the model related components equal to {@code expectedModel}.
//     * These verifications are done by
//     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
//     * selected card updated accordingly, depending on {@code cardStatus}.
//     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel) {
//        String expectedResultMessage = String.format(
//                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchanged();
//    }
//
//    /**
//     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
//     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
//     * These verifications are done by
//     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
//     * error style.
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
