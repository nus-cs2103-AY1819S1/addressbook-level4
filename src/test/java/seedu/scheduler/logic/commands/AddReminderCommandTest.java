package seedu.scheduler.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.testutil.EditEventDescriptorBuilder;
import seedu.scheduler.testutil.EventBuilder;

public class AddReminderCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());

    @Test
    public void execute_addNewReminder_unfilteredList_success() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        ReminderDurationList durationsToAdd = getReminderDurationList(3);
        EventBuilder eventInList = new EventBuilder(firstEvent);
        ReminderDurationList addedReminderDurationList = firstEvent.getReminderDurationList().getCopy();
        addedReminderDurationList.addAll(durationsToAdd);
        Event editedEvent = eventInList.withReminderDurationList(addedReminderDurationList).build();

        AddReminderCommand addReminderCommand = new AddReminderCommand(INDEX_FIRST_EVENT, durationsToAdd);

        String expectedMessage = String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(addReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
