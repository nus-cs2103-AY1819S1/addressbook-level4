package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.FacultyLocationDisplayChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

class ShowLocationCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfiltered_success() {
        int personListLength = model.getFilteredPersonList().size();

        for (int i = 0; i < personListLength; i++) {
            Index validPersonIndex = Index.fromZeroBased(i);

            assertFacultyLocationDisplaySuccess(validPersonIndex);
        }
    }

    @Test
    public void execute_invalidIndexUnfiltered_success() {
        Index invalidPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertFacultyLocationDisplayFailure(invalidPersonIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        ShowLocationCommand showLocationFirstCommand = new ShowLocationCommand(INDEX_FIRST_PERSON);
        ShowLocationCommand showLocationSecondCommand = new ShowLocationCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(showLocationFirstCommand.equals(showLocationFirstCommand));

        // same index values -> returns true
        ShowLocationCommand showLocationFirstCommandCopy = new ShowLocationCommand(INDEX_FIRST_PERSON);
        assertTrue(showLocationFirstCommand.equals(showLocationFirstCommandCopy));

        // different types -> returns false
        assertFalse(showLocationFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showLocationFirstCommand.equals(null));

        // different index (therefore different person) -> returns false
        assertFalse(showLocationFirstCommand.equals(showLocationSecondCommand));
    }

    /**
     * Executes a {@code ShowLocationCommand} with the given invalid {@code index}, and checks that
     * a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertFacultyLocationDisplayFailure(Index index, String expectedMessage) {
        ShowLocationCommand showLocationCommand = new ShowLocationCommand(index);
        assertCommandFailure(showLocationCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

    /**
     * Executes a {@code ShowLocationCommand} with the given {@code index}, and checks that
     * {@code FacultyLocationDisplayChangedEvent} is raised with the correct person.
     */
    private void assertFacultyLocationDisplaySuccess(Index index) {
        ShowLocationCommand showLocationCommand = new ShowLocationCommand(index);
        String expectedMessage = String.format(ShowLocationCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        assertCommandSuccess(showLocationCommand, model, commandHistory, expectedMessage, expectedModel);

        Person person = model.getFilteredPersonList().get(index.getZeroBased());

        FacultyLocationDisplayChangedEvent latestEvent = (FacultyLocationDisplayChangedEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(person, latestEvent.getSelectedPerson());
    }

}