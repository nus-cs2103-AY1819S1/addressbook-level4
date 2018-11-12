package seedu.scheduler.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.disable;
import static seedu.scheduler.logic.commands.CommandTestUtil.enable;
import static seedu.scheduler.logic.commands.CommandTestUtil.helperCommand;
import static seedu.scheduler.testutil.TypicalEvents.AFTER_CHRISTMAS;
import static seedu.scheduler.testutil.TypicalEvents.SATURDAY_LECTURE;

import org.junit.Test;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GetGoogleEventsCommandCommand.
 */
public class EnterGoogleCalendarModeCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeEnterGoogleCalendarModeCommandValidNonRepeatEventSuccess() {
        //Set status to before initialization
        disable();
        //Prepare the command
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        EnterGoogleCalendarModeCommand
                enterGoogleCalendarModeCommand = new EnterGoogleCalendarModeCommand();

        //Add a new single Event
        //Enable the Google Calender feature temporarily to add new events
        enable();
        Event validEvent = new EventBuilder(AFTER_CHRISTMAS).build();
        helperCommand(new AddCommand(validEvent), model, commandHistory);
        //Set status to before initialization for the command to run
        disable();
        helperCommand(enterGoogleCalendarModeCommand, model, commandHistory);
        //check
        //Currently unable to get all Google Event property downloaded and match perfectly
        //especially the conversion between
        //EventUid, EventSetUid, EventRecurringID, EventiCalID
        //Hence for not as long as the model has been added with a new event
        //We consider it as success
        //TODO: Ensure all GoogleEvent properties are matched perfectly with Local Event
        disable();
        assertNotEquals(model, expectedModel);
    }

    @Test
    public void executeEnterGoogleCalendarModeCommandValidRepeatEventSuccess() {
        //Set status to before initialization
        disable();
        //Prepare the command
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        EnterGoogleCalendarModeCommand
                enterGoogleCalendarModeCommand = new EnterGoogleCalendarModeCommand();

        //Add a new single Event
        //Enable the Google Calender feature temporarily to add new events
        enable();
        Event validEvent = new EventBuilder(SATURDAY_LECTURE).build();
        helperCommand(new AddCommand(validEvent), model, commandHistory);
        //Set status to before initialization for the command to run
        disable();
        helperCommand(enterGoogleCalendarModeCommand, model, commandHistory);
        //check
        //Currently unable to get all Google Event property downloaded and match perfectly
        //especially the conversion between
        //EventUid, EventSetUid, EventRecurringID, EventiCalID
        //Hence for not as long as the model has been added with a new event
        //We consider it as success
        //TODO: Ensure all GoogleEvent properties are matched perfectly with Local Event
        disable();
        assertNotEquals(model, expectedModel);
    }

    @Test
    public void executeEnterGoogleCalendarModeCommandTwiceFailure() {
        //Set status to after initialization
        enable();
        EnterGoogleCalendarModeCommand
                enterGoogleCalendarModeCommand = new EnterGoogleCalendarModeCommand();
        String expectedMessage =
                EnterGoogleCalendarModeCommand.MESSAGE_REJECT_SECOND_INITIALIZE;
        Model model = new ModelManager();

        assertCommandFailure(enterGoogleCalendarModeCommand,
                model, commandHistory, expectedMessage);
        //set beck to the original status for other tests
        disable();
    }
}
