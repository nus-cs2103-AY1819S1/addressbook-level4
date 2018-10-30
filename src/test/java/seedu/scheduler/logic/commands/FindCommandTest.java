package seedu.scheduler.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_FOUR;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_THREE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_TWO;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JILL_DAY_FOUR;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JILL_DAY_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JILL_DAY_THREE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JILL_DAY_TWO;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.event.EventNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("first"));
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 8);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Study");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(STUDY_WITH_JANE_DAY_ONE, STUDY_WITH_JANE_DAY_TWO, STUDY_WITH_JANE_DAY_THREE,
                STUDY_WITH_JANE_DAY_FOUR, STUDY_WITH_JILL_DAY_ONE, STUDY_WITH_JILL_DAY_TWO,
                STUDY_WITH_JILL_DAY_THREE, STUDY_WITH_JILL_DAY_FOUR),
                model.getFilteredEventList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private EventNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EventNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
