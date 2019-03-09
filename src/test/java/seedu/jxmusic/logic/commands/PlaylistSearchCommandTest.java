package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_PLAYLISTS_LISTED_OVERVIEW;
import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.testutil.TypicalPlaylistList.EMPTY;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;
import seedu.jxmusic.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code PlaylistSearchCommand}.
 */
public class PlaylistSearchCommandTest {
    private Model model = new ModelManager(getTypicalLibrary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        PlaylistSearchCommand searchFirstCommand = new PlaylistSearchCommand(firstPredicate);
        PlaylistSearchCommand searchSecondCommand = new PlaylistSearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        PlaylistSearchCommand searchFirstCommandCopy = new PlaylistSearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different playlist -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPlaylistFound() {
        String expectedMessage = String.format(MESSAGE_PLAYLISTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        PlaylistSearchCommand command = new PlaylistSearchCommand(predicate);
        expectedModel.updateFilteredPlaylistList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPlaylistList());
    }

    @Test
    public void execute_multipleKeywords_multiplePlaylistsFound() {
        String expectedMessage = String.format(MESSAGE_PLAYLISTS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("playlist");
        PlaylistSearchCommand command = new PlaylistSearchCommand(predicate);
        expectedModel.updateFilteredPlaylistList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(EMPTY, SFX), model.getFilteredPlaylistList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
