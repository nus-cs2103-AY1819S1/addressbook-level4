package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertVolunteerCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
    private Model modelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private Model expectedModelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredVolunteerList_success() {
        Index lastVolunteerIndex = Index.fromOneBased(modelVolunteer.getFilteredVolunteerList().size());

        assertVolunteerExecutionSuccess(INDEX_FIRST_PERSON);
        assertVolunteerExecutionSuccess(INDEX_THIRD_PERSON);
        assertVolunteerExecutionSuccess(lastVolunteerIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredVolunteerList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(modelVolunteer.getFilteredVolunteerList().size() + 1);

        assertVolunteerExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredVolunteerList_success() {
        showVolunteerAtIndex(modelVolunteer, INDEX_FIRST_PERSON);
        showVolunteerAtIndex(expectedModelVolunteer, INDEX_FIRST_PERSON);

        assertVolunteerExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredVolunteerList_failure() {
        showVolunteerAtIndex(modelVolunteer, INDEX_FIRST_PERSON);
        showVolunteerAtIndex(expectedModelVolunteer, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < modelVolunteer.getAddressBook()
                .getVolunteerList().size());

        assertVolunteerExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);
        SelectVolunteerCommand selectFirstVolunteerCommand = new SelectVolunteerCommand(INDEX_FIRST_PERSON);
        SelectVolunteerCommand selectSecondVolunteerCommand = new SelectVolunteerCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));

        // same object -> returns true
        assertTrue(selectFirstVolunteerCommand.equals(selectFirstVolunteerCommand));

        // same values -> returns true
        SelectVolunteerCommand selectFirstVolunteerCommandCopy = new SelectVolunteerCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstVolunteerCommand.equals(selectFirstVolunteerCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstVolunteerCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstVolunteerCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstVolunteerCommand.equals(selectSecondVolunteerCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

    /**
     * Executes a {@code SelectVolunteerCommand} with the given {@code index}, and checks that
     * {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertVolunteerExecutionSuccess(Index index) {
        SelectVolunteerCommand selectVolunteerCommand = new SelectVolunteerCommand(index);
        String expectedMessage = String.format(SelectVolunteerCommand.MESSAGE_SELECT_VOLUNTEER_SUCCESS,
                index.getOneBased());

        assertCommandSuccess(selectVolunteerCommand, modelVolunteer, commandHistory, expectedMessage,
                expectedModelVolunteer);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectVolunteerCommand} with the given {@code index}, and checks that
     * a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertVolunteerExecutionFailure(Index index, String expectedMessage) {
        SelectVolunteerCommand selectVolunteerCommand = new SelectVolunteerCommand(index);
        assertVolunteerCommandFailure(selectVolunteerCommand, modelVolunteer, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
