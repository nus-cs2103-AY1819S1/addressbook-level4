package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showMeetingAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalMeetingBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.util.GroupContainsPersonPredicate;
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

        assertExecutionSuccess(INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON, SelectCommand.SelectCommandType.PERSON);
        assertExecutionSuccess(lastPersonIndex, SelectCommand.SelectCommandType.PERSON);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SelectCommandType.PERSON,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SelectCommandType.PERSON,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand =
            new SelectCommand(INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON);
        SelectCommand selectSecondCommand =
            new SelectCommand(INDEX_SECOND_PERSON, SelectCommand.SelectCommandType.PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy =
            new SelectCommand(INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));

        selectFirstCommand = new SelectCommand(INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP);
        selectSecondCommand =
            new SelectCommand(INDEX_SECOND_GROUP, SelectCommand.SelectCommandType.GROUP);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        selectFirstCommandCopy =
            new SelectCommand(INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));

        selectFirstCommand = new SelectCommand(INDEX_FIRST_MEETING, SelectCommand.SelectCommandType.MEETING);
        selectSecondCommand =
            new SelectCommand(INDEX_SECOND_MEETING, SelectCommand.SelectCommandType.MEETING);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        selectFirstCommandCopy =
            new SelectCommand(INDEX_FIRST_MEETING, SelectCommand.SelectCommandType.MEETING);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different meeting -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    @Test
    public void execute_validIndexUnfilteredGroupList_success() {
        Index lastGroupIndex = Index.fromOneBased(model.getFilteredGroupList().size());

        Group group = expectedModel.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        assertExecutionSuccess(INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP);

        group = expectedModel.getFilteredGroupList().get(INDEX_THIRD_GROUP.getZeroBased());
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        assertExecutionSuccess(INDEX_THIRD_GROUP, SelectCommand.SelectCommandType.GROUP);

        group = expectedModel.getFilteredGroupList().get(lastGroupIndex.getZeroBased());
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        assertExecutionSuccess(lastGroupIndex, SelectCommand.SelectCommandType.GROUP);
    }

    @Test
    public void execute_invalidIndexUnfilteredGroupList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SelectCommandType.GROUP,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredGroupList_success() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        assertExecutionSuccess(INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP);
    }

    @Test
    public void execute_invalidIndexFilteredGroupList_failure() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        Index outOfBoundsIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SelectCommandType.GROUP,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredMeetingList_success() {
        Index lastMeetingIndex = Index.fromOneBased(model.getFilteredMeetingList().size());

        assertExecutionSuccess(INDEX_FIRST_MEETING, SelectCommand.SelectCommandType.MEETING);
        assertExecutionSuccess(INDEX_THIRD_MEETING, SelectCommand.SelectCommandType.MEETING);
        assertExecutionSuccess(lastMeetingIndex, SelectCommand.SelectCommandType.MEETING);
    }

    @Test
    public void execute_invalidIndexUnfilteredMeetingList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMeetingList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SelectCommandType.MEETING,
            Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredMeetingList_success() {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);
        showMeetingAtIndex(expectedModel, INDEX_FIRST_MEETING);

        assertExecutionSuccess(INDEX_FIRST_MEETING, SelectCommand.SelectCommandType.MEETING);
    }

    @Test
    public void execute_invalidIndexFilteredMeetingList_failure() {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);
        showMeetingAtIndex(expectedModel, INDEX_FIRST_MEETING);

        Index outOfBoundsIndex = INDEX_THIRD_MEETING;

        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getMeetingList().size());

        assertExecutionFailure(outOfBoundsIndex, SelectCommand.SelectCommandType.MEETING,
            Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, SelectCommand.SelectCommandType selectType) {
        SelectCommand selectCommand = new SelectCommand(index, selectType);
        String expectedMessage = String.format(getExpectedMessage(selectType), index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        if (selectType == SelectCommand.SelectCommandType.PERSON) {
            JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) baseEvent;
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        } else if (selectType == SelectCommand.SelectCommandType.GROUP) {
            JumpToGroupListRequestEvent lastEvent = (JumpToGroupListRequestEvent) baseEvent;
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        } else {
            JumpToMeetingListRequestEvent lastEvent = (JumpToMeetingListRequestEvent) baseEvent;
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        }
    }

    private String getExpectedMessage(SelectCommand.SelectCommandType selectType) {
        switch (selectType) {
        case GROUP:
            return SelectCommand.MESSAGE_SELECT_GROUP_SUCCESS;
        case MEETING:
            return SelectCommand.MESSAGE_SELECT_MEETING_SUCCESS;
        case PERSON:
            return SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
        default:
            return SelectCommand.MESSAGE_USAGE;
        }
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, SelectCommand.SelectCommandType selectType,
                                        String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index, selectType);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
