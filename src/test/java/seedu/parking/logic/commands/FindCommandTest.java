package seedu.parking.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.commons.core.Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.testutil.TypicalCarparks.CHARLIE;
import static seedu.parking.testutil.TypicalCarparks.ECHO;
import static seedu.parking.testutil.TypicalCarparks.FOXTROT;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        CarparkContainsKeywordsPredicate firstPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("first"));
        CarparkContainsKeywordsPredicate secondPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different carpark -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCarparkFound() {
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 0);
        CarparkContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCarparkList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCarparkList());
    }

    @Test
    public void execute_multipleKeywords_multipleCarparksFound() {
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 3);
        CarparkContainsKeywordsPredicate predicate = preparePredicate("U25 PP5 SE39");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCarparkList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHARLIE, ECHO, FOXTROT), model.getFilteredCarparkList());
    }

    /**
     * Parses {@code userInput} into a {@code CarparkContainsKeywordsPredicate}.
     */
    private CarparkContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CarparkContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
