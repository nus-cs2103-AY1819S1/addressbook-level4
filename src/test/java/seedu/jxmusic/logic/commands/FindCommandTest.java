//package seedu.jxmusic.logic.commands;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.CLASSICS;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalAddressBook;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.junit.Test;
//
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.ModelManager;
//import seedu.jxmusic.model.UserPrefs;
//import seedu.jxmusic.model.NameContainsKeywordsPredicate;
//
///**
// * Contains integration tests (interaction with the Model) for {@code PlaylistSearchCommand}.
// */
//public class PlaylistSearchCommandTest {
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Test
//    public void equals() {
//        NameContainsKeywordsPredicate firstPredicate =
//                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
//        NameContainsKeywordsPredicate secondPredicate =
//                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
//
//        PlaylistSearchCommand findFirstCommand = new PlaylistSearchCommand(firstPredicate);
//        PlaylistSearchCommand findSecondCommand = new PlaylistSearchCommand(secondPredicate);
//
//        // same object -> returns true
//        assertTrue(findFirstCommand.equals(findFirstCommand));
//
//        // same values -> returns true
//        PlaylistSearchCommand findFirstCommandCopy = new PlaylistSearchCommand(firstPredicate);
//        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(findFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(findFirstCommand.equals(null));
//
//        // different playlist -> returns false
//        assertFalse(findFirstCommand.equals(findSecondCommand));
//    }
//
//    @Test
//    public void execute_zeroKeywords_noPlaylistFound() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
//        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
//        PlaylistSearchCommand command = new PlaylistSearchCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
//        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
//    }
//
//    @Test
//    public void execute_multipleKeywords_multiplePersonsFound() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
//        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
//        PlaylistSearchCommand command = new PlaylistSearchCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
//        assertEquals(Arrays.asList(SFX, SFX, CLASSICS), model.getFilteredPersonList());
//    }
//
//    /**
//     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
//     */
//    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
//        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
//    }
//}
