package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, SelectCommand.SELECT_TYPE_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON, SelectCommand.SELECT_TYPE_PERSON);
        assertExecutionSuccess(lastPersonIndex, SelectCommand.SELECT_TYPE_PERSON);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SELECT_TYPE_PERSON,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON, SelectCommand.SELECT_TYPE_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SELECT_TYPE_PERSON,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON, SelectCommand.SELECT_TYPE_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON, SelectCommand.SELECT_TYPE_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON, SelectCommand.SELECT_TYPE_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    @Test
    public void execute_validIndexUnfilteredGroupList_success() {
        Index lastGroupIndex = Index.fromOneBased(model.getFilteredGroupList().size());

        assertExecutionSuccess(INDEX_FIRST_GROUP, SelectCommand.SELECT_TYPE_GROUP);
        assertExecutionSuccess(INDEX_THIRD_GROUP, SelectCommand.SELECT_TYPE_GROUP);
        assertExecutionSuccess(lastGroupIndex, SelectCommand.SELECT_TYPE_GROUP);
    }

    @Test
    public void execute_invalidIndexUnfilteredGroupList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SELECT_TYPE_GROUP,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredGroupList_success() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        assertExecutionSuccess(INDEX_FIRST_GROUP, SelectCommand.SELECT_TYPE_GROUP);
    }

    @Test
    public void execute_invalidIndexFilteredGroupList_failure() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        Index outOfBoundsIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SELECT_TYPE_GROUP,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equalsGroup() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_GROUP, SelectCommand.SELECT_TYPE_GROUP);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_GROUP, SelectCommand.SELECT_TYPE_GROUP);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_GROUP, SelectCommand.SELECT_TYPE_GROUP);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, int selectType) {
        SelectCommand selectCommand = new SelectCommand(index, selectType);
        String expectedMessage = (selectType == SelectCommand.SELECT_TYPE_PERSON)
                ? String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased())
                : String.format(SelectCommand.MESSAGE_SELECT_GROUP_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        if (selectType == SelectCommand.SELECT_TYPE_PERSON) {
            JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) baseEvent;
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        } else {
            JumpToGroupListRequestEvent lastEvent = (JumpToGroupListRequestEvent) baseEvent;
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        }
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, int selectType, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index, selectType);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
