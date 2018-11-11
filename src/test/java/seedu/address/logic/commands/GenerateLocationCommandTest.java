package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RandomMeetingLocationGeneratedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventDate;
import seedu.address.ui.testutil.EventsCollectorRule;

class GenerateLocationCommandTest {


    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    public static final Index EXISTENT_INDEX = Index.fromOneBased(1);
    public static final Index NON_EXISTENT_INDEX = Index.fromOneBased(999);
    public static final EventDate TEST_EVENT_DATE = new EventDate(VALID_EVENT_DATE_DOCTORAPPT);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDateInvalidIndexLocationNotDisplayed_success() {
        assertGenerateLocationDisplayFailure(TEST_EVENT_DATE, NON_EXISTENT_INDEX,
                GenerateLocationCommand.MESSAGE_EVENT_DOES_NOT_EXIST);
    }

    @Test
    public void execute_validDateValidIndexLocationDisplayed_success() {
        assertGenerateLocationDisplaySuccess(TEST_EVENT_DATE, EXISTENT_INDEX);
    }

    @Test
    void equals() {
        GenerateLocationCommand generateLocationFirstCommand = new GenerateLocationCommand(TEST_EVENT_DATE,
                INDEX_FIRST_EVENT);
        GenerateLocationCommand generateLocationSecondCommand = new GenerateLocationCommand(TEST_EVENT_DATE,
                INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(generateLocationFirstCommand.equals(generateLocationFirstCommand));

        // same index values -> returns true
        GenerateLocationCommand generateLocationFirstCommandCopy = new GenerateLocationCommand(TEST_EVENT_DATE,
                INDEX_FIRST_EVENT);
        assertTrue(generateLocationFirstCommand.equals(generateLocationFirstCommandCopy));

        // different types -> returns false
        assertFalse(generateLocationFirstCommand.equals("hello"));

        // null -> returns false
        assertFalse(generateLocationFirstCommand.equals(null));

        // different index (therefore different person) -> returns false
        assertFalse(generateLocationFirstCommand.equals(generateLocationSecondCommand));
    }

    /**
     * Executes a {@code GenerateLocationCommand} with given incorrect{@code index} but correct {@code eventDate},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertGenerateLocationDisplayFailure(EventDate eventDate, Index index,
                                                      String expectedMessage) {
        GenerateLocationCommand generateLocationCommand = new GenerateLocationCommand(eventDate, index);
        assertCommandFailure(generateLocationCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

    /**
     * Executes a {@code GenerateLocationCommand} with the given correct {@code index} and {@code eventDate},
     * and checks that {@code RandomMeetingLocationGeneratedEvent} is raised.
     */
    private void assertGenerateLocationDisplaySuccess(EventDate eventDate, Index index) {
        GenerateLocationCommand generateLocationCommand = new GenerateLocationCommand(eventDate, index);
        String expectedMessage = generateLocationCommand.createFinalSuccessMessage(VALID_EVENT_NAME_DOCTORAPPT);

        assertCommandSuccess(generateLocationCommand, model, commandHistory, expectedMessage, expectedModel);

        RandomMeetingLocationGeneratedEvent latestEvent = (RandomMeetingLocationGeneratedEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals("RandomMeetingLocationGeneratedEvent", latestEvent.toString());
    }
}
