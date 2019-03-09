package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_TRACKS_LISTED_OVERVIEW;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalTrackList.HAIKEI;
import static seedu.jxmusic.testutil.TypicalTrackList.IHOJIN;
import static seedu.jxmusic.testutil.TypicalTrackList.getTypicalLibrary;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.TrackNameContainsKeywordsPredicate;
import seedu.jxmusic.model.UserPrefs;

public class TrackSearchCommandTest {
    private Model model = new ModelManager(getTypicalLibrary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TrackNameContainsKeywordsPredicate firstPredicate =
                new TrackNameContainsKeywordsPredicate(Collections.singletonList("first"));
        TrackNameContainsKeywordsPredicate secondPredicate =
                new TrackNameContainsKeywordsPredicate(Collections.singletonList("second"));

        TrackSearchCommand searchFirstCommand = new TrackSearchCommand(firstPredicate);
        TrackSearchCommand searchSecondCommand = new TrackSearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        TrackSearchCommand searchFirstCommandCopy = new TrackSearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different track -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTrackFound() {
        String expectedMessage = String.format(MESSAGE_TRACKS_LISTED_OVERVIEW, 0);
        TrackNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        TrackSearchCommand command = new TrackSearchCommand(predicate);
        expectedModel.updateFilteredTrackList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTrackList());
    }

    @Test
    public void execute_multipleKeywords_multipleTracksFound() {
        String expectedMessage = String.format(MESSAGE_TRACKS_LISTED_OVERVIEW, 2);
        TrackNameContainsKeywordsPredicate predicate = preparePredicate("ai");
        TrackSearchCommand command = new TrackSearchCommand(predicate);
        expectedModel.updateFilteredTrackList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(HAIKEI, IHOJIN), model.getFilteredTrackList());
    }

    /**
     * Parses {@code userInput} into a {@code TrackNameContainsKeywordsPredicate}.
     */
    private TrackNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TrackNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
