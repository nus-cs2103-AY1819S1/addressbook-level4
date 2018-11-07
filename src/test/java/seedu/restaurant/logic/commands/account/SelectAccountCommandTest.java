package seedu.restaurant.logic.commands.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.CommandTestUtil.showAccountAtIndex;
import static seedu.restaurant.logic.commands.CommandTestUtil.showIngredientAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.JumpToAccountListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.ui.testutil.EventsCollectorRule;

//@@author AZhiKai

/**
 * Contains integration tests (interaction with the Model) for {@code SelectAccountCommand}.
 */
public class SelectAccountCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validAccountUnfilteredList_success() {
        Index lastAccountIndex = Index.fromOneBased(model.getFilteredAccountList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_THIRD);
        assertExecutionSuccess(lastAccountIndex);
    }

    @Test
    public void execute_invalidAccountUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredAccountList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validAccountFilteredList_success() {
        showIngredientAtIndex(model, INDEX_FIRST);
        showIngredientAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST);
    }

    @Test
    public void execute_invalidIngredientFilteredList_failure() {
        showAccountAtIndex(model, INDEX_FIRST);
        showAccountAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getRestaurantBook().getAccountList().size());

        assertExecutionFailure(outOfBoundsIndex, MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectAccountCommand selectFirstCommand = new SelectAccountCommand(INDEX_FIRST);
        SelectAccountCommand selectSecondCommand = new SelectAccountCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectAccountCommand selectFirstCommandCopy = new SelectAccountCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different account -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectAccountCommand} with the given {@code index}, and checks that {@code
     * JumpToAccountListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectAccountCommand selectCommand = new SelectAccountCommand(index);
        String expectedMessage = String.format(SelectAccountCommand.MESSAGE_SELECT_ACCOUNT_SUCCESS,
                index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToAccountListRequestEvent lastEvent =
                (JumpToAccountListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectAccountCommand} with the given {@code index}, and checks that a {@code CommandException }
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectAccountCommand selectCommand = new SelectAccountCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
