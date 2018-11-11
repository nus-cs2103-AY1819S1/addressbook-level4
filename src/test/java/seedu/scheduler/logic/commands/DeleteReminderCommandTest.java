package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;
import static seedu.scheduler.testutil.TypicalEvents.getStudyWithJaneAllList;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FOURTH_EVENT;

import java.util.List;

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
import seedu.scheduler.testutil.EventBuilder;

public class DeleteReminderCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());

    @Test
    public void execute_deleteExistingReminder_success() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        ReminderDurationList durationsToDelete = getReminderDurationList(0, 2);
        EventBuilder eventInList = new EventBuilder(firstEvent);
        Event editedEvent = eventInList.withReminderDurationList(new ReminderDurationList()).build();

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_EVENT, durationsToDelete);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(deleteReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistingReminder_success() { // no change to the model
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        ReminderDurationList durationsToDelete = getReminderDurationList(1);
        EventBuilder eventInList = new EventBuilder(firstEvent);
        Event editedEvent = eventInList.build();

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_EVENT, durationsToDelete);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(deleteReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistAndExistReminder_success() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        ReminderDurationList durationsToDelete = getReminderDurationList(0, 1); // 1 is not in the list
        ReminderDurationList actualDurationsToDelete = getReminderDurationList(0);

        EventBuilder eventInList = new EventBuilder(firstEvent);
        ReminderDurationList deleteReminderDurationList = firstEvent.getReminderDurationList().getCopy();
        deleteReminderDurationList.removeAll(actualDurationsToDelete);
        Event editedEvent = eventInList.withReminderDurationList(deleteReminderDurationList).build();

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_EVENT, durationsToDelete);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(deleteReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteReminderToAllEvents_success() {
        Index index = INDEX_FOURTH_EVENT;
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToDelete = getReminderDurationList(2, 3); //2 is not in the list
        ReminderDurationList actualDurationsToDelete = getReminderDurationList(3);


        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                firstEvent.getEventName());
        List<Event> eventsToEdit = getStudyWithJaneAllList();
        deleteRemindersToEvents(eventsToEdit, expectedModel, actualDurationsToDelete);
        expectedModel.commitScheduler();

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(index, durationsToDelete, FLAG_ALL);

        assertCommandSuccess(deleteReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteReminderToUpcomingEvents_success() {
        Index index = INDEX_FOURTH_EVENT;
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToDelete = getReminderDurationList(2, 3); //1 is not in the list
        ReminderDurationList actualDurationsToDelete = getReminderDurationList(3);


        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                firstEvent.getEventName());
        List<Event> eventsToEdit = getStudyWithJaneAllList().subList(1,4);
        deleteRemindersToEvents(eventsToEdit, expectedModel, actualDurationsToDelete);
        expectedModel.commitScheduler();

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(index,
                durationsToDelete, FLAG_UPCOMING);

        assertCommandSuccess(deleteReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        ReminderDurationList durationsToDelete = getReminderDurationList(2);
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(outOfBoundIndex,
                durationsToDelete, FLAG_UPCOMING);

        assertCommandFailure(deleteReminderCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    public void deleteRemindersToEvents( List<Event> events, Model expectedModel, ReminderDurationList durationsToDelete) {
        for (Event event : events) {
            EventBuilder eventInList = new EventBuilder(event);
            ReminderDurationList addedReminderDurationList = event.getReminderDurationList().getCopy();
            addedReminderDurationList.removeAll(durationsToDelete);
            Event editedEvent = eventInList.withReminderDurationList(addedReminderDurationList).build();
            expectedModel.updateEvent(event, editedEvent);
        }
    }
}
