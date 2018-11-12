package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.testutil.CalendarEventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    }

    @Test
    public void execute_newCalendarEvent_success() {
        CalendarEvent validCalendarEvent = new CalendarEventBuilder().build();

        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.addCalendarEvent(validCalendarEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(new AddEventCommand(validCalendarEvent), model, commandHistory,
            String.format(AddEventCommand.MESSAGE_SUCCESS, validCalendarEvent), expectedModel);
    }

    @Test
    public void execute_duplicateCalendarEvent_throwsCommandException() {
        CalendarEvent calendarEventInList = model.getScheduler().getCalendarEventList().get(0);
        assertCommandFailure(new AddEventCommand(calendarEventInList), model, commandHistory,
            AddEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);
    }

}
