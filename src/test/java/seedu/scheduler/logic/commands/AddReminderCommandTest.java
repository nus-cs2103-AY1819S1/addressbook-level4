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

import org.junit.Test;
import java.util.List;
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

    @Test
    public void execute_addRepeatedReminder_unfilteredList_success() { // no change to the model
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        ReminderDurationList durationsToAdd = getReminderDurationList(0);

        AddReminderCommand addReminderCommand = new AddReminderCommand(INDEX_FIRST_EVENT, durationsToAdd);
        String expectedMessage = String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.commitScheduler();

        assertCommandSuccess(addReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addRepeatedAndNewReminder_unfilteredList_success() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        ReminderDurationList durationsToAdd = getReminderDurationList(1, 2); // 2 is already inside the list
        ReminderDurationList actualDurationsToAdd = getReminderDurationList(1);

        EventBuilder eventInList = new EventBuilder(firstEvent);
        ReminderDurationList addedReminderDurationList = firstEvent.getReminderDurationList().getCopy();
        addedReminderDurationList.addAll(actualDurationsToAdd);
        Event editedEvent = eventInList.withReminderDurationList(addedReminderDurationList).build();

        AddReminderCommand addReminderCommand = new AddReminderCommand(INDEX_FIRST_EVENT, durationsToAdd);

        String expectedMessage = String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(addReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addReminderToAllEvents_success() {
        Index index = INDEX_FOURTH_EVENT;
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToAdd = getReminderDurationList(2);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        String expectedMessage = String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS,
                firstEvent.getEventName());
        List<Event> eventsToEdit = getStudyWithJaneAllList();
        addRemindersToEvents(eventsToEdit, expectedModel, durationsToAdd);
        expectedModel.commitScheduler();

        AddReminderCommand addReminderCommand = new AddReminderCommand(index, durationsToAdd, FLAG_ALL);

        assertCommandSuccess(addReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addReminderToUpcomingEvents_success() {
        Index index = INDEX_FOURTH_EVENT;
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToAdd = getReminderDurationList(2);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        String expectedMessage = String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS,
                firstEvent.getEventName());
        List<Event> eventsToEdit = getStudyWithJaneAllList().subList(1,4);
        addRemindersToEvents(eventsToEdit, expectedModel, durationsToAdd);
        expectedModel.commitScheduler();

        AddReminderCommand addReminderCommand = new AddReminderCommand(index, durationsToAdd, FLAG_UPCOMING);

        assertCommandSuccess(addReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        ReminderDurationList durationsToAdd = getReminderDurationList(2);
        AddReminderCommand addReminderCommand = new AddReminderCommand(outOfBoundIndex, durationsToAdd, FLAG_UPCOMING);

        assertCommandFailure(addReminderCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    public void addRemindersToEvents( List<Event> events, Model expectedModel, ReminderDurationList durationsToAdd) {
        for (Event event : events) {
            EventBuilder eventInList = new EventBuilder(event);
            ReminderDurationList addedReminderDurationList = event.getReminderDurationList().getCopy();
            addedReminderDurationList.addAll(durationsToAdd);
            Event editedEvent = eventInList.withReminderDurationList(addedReminderDurationList).build();
            expectedModel.updateEvent(event, editedEvent);
        }
    }
}
