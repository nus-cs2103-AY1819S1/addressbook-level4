package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToEntryListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SelectEntryCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private Model expectedModel = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastEntryIndex = Index.fromOneBased(model.getFilteredEntryList().size());

        assertExecutionSuccess(INDEX_FIRST_ENTRY);
        assertExecutionSuccess(INDEX_THIRD_ENTRY);
        assertExecutionSuccess(lastEntryIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredEntryList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        SelectEntryCommand selectFirstCommand = new SelectEntryCommand(INDEX_FIRST_ENTRY);
        SelectEntryCommand selectSecondCommand = new SelectEntryCommand(INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectEntryCommand selectFirstCommandCopy = new SelectEntryCommand(INDEX_FIRST_ENTRY);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different entry -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectEntryCommand} with the given {@code index},
     * and checks that {@code JumpToEntryListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectEntryCommand selectEntryCommand = new SelectEntryCommand(index);
        String expectedMessage = String.format(SelectEntryCommand.MESSAGE_SELECT_ENTRY_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectEntryCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToEntryListRequestEvent lastEvent = (JumpToEntryListRequestEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectEntryCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectEntryCommand selectEntryCommand = new SelectEntryCommand(index);
        assertCommandFailure(selectEntryCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
