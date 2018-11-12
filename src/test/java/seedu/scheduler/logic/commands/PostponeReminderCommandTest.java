package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;
import static seedu.scheduler.testutil.TypicalEvents.getStudyWithJaneAllList;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIFTH_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FOURTH_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SEVENTH_EVENT;

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

public class PostponeReminderCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());

    @Test
    public void execute_postponeForEmptyList_success() { //do nothing in this case
        // This event has empty reminder list
        Index index = INDEX_SEVENTH_EVENT;
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());

        ReminderDurationList durationToPostpone = getReminderDurationList(1);

        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index, durationToPostpone);

        String expectedMessage = String.format(PostponeReminderCommand.MESSAGE_POSTPONE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.commitScheduler();
        assertCommandSuccess(postponeReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_postponeEmptyReminder_failure() {
        Index index = INDEX_FIRST_EVENT;
        ReminderDurationList durationToPostpone = getReminderDurationList();
        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index,
                durationToPostpone, FLAG_UPCOMING);

        assertCommandFailure(postponeReminderCommand, model, commandHistory,
                PostponeReminderCommand.MESSAGE_EMPTY_REMINDER_ENTERED);
    }

    @Test
    public void execute_postponeForSingleEventNoExceed_success() {
        Index index = INDEX_SECOND_EVENT; //30M
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToPostpone = getReminderDurationList(0); //15M --> 30-15 = 15M
        EventBuilder eventInList = new EventBuilder(firstEvent);
        Event editedEvent = eventInList.withReminderDurationList(getReminderDurationList(0)).build();
        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index, durationsToPostpone);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();
        String expectedMessage = String.format(PostponeReminderCommand.MESSAGE_POSTPONE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        assertCommandSuccess(postponeReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_postponeForSingleEventAllExceed_success() {
        Index index = INDEX_SECOND_EVENT; //15M, 30M, 1H
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToPostpone = getReminderDurationList(2); //1H30M --> All become 0
        EventBuilder eventInList = new EventBuilder(firstEvent);
        Event editedEvent = eventInList.withReminderDurationList(getReminderDurationList(4)).build(); //0
        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index, durationsToPostpone);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();
        String expectedMessage = String.format(PostponeReminderCommand.MESSAGE_POSTPONE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        assertCommandSuccess(postponeReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_postponeForSingleEventSomeExceed_success() {
        Index index = INDEX_FIRST_EVENT; //15M, 1H30M
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToPostpone = getReminderDurationList(3); //1H -> 0S, 30M
        EventBuilder eventInList = new EventBuilder(firstEvent);
        Event editedEvent = eventInList.withReminderDurationList(getReminderDurationList(1, 4)).build();
        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index, durationsToPostpone);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateEvent(firstEvent, editedEvent);
        expectedModel.commitScheduler();
        String expectedMessage = String.format(PostponeReminderCommand.MESSAGE_POSTPONE_REMINDER_SUCCESS,
                firstEvent.getEventName());

        assertCommandSuccess(postponeReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_postponeReminderAllEvents_success() {
        Index index = INDEX_FOURTH_EVENT; //15M, 30M, 1H
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToPostpone = getReminderDurationList(1); //30M
        ReminderDurationList durationsAfterPostpone = getReminderDurationList(1, 4); //0S, 30M


        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        String expectedMessage = String.format(PostponeReminderCommand.MESSAGE_POSTPONE_REMINDER_SUCCESS,
                firstEvent.getEventName());
        List<Event> eventsToEdit = getStudyWithJaneAllList();
        postponeRemindersToEvents(eventsToEdit, expectedModel, durationsAfterPostpone);
        expectedModel.commitScheduler();

        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index,
                durationsToPostpone, FLAG_ALL);

        assertCommandSuccess(postponeReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_postponeReminderUpcomingEvents_success() {
        Index index = INDEX_FIFTH_EVENT; //15M, 30M, 1H
        Event firstEvent = model.getFilteredEventList().get(index.getZeroBased());
        ReminderDurationList durationsToPostpone = getReminderDurationList(0); //15M
        ReminderDurationList durationsAfterPostpone = getReminderDurationList(0, 4, 5); //0S, 15M, 45M


        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        String expectedMessage = String.format(PostponeReminderCommand.MESSAGE_POSTPONE_REMINDER_SUCCESS,
                firstEvent.getEventName());
        List<Event> eventsToEdit = getStudyWithJaneAllList().subList(2, 4);
        postponeRemindersToEvents(eventsToEdit, expectedModel, durationsAfterPostpone);
        expectedModel.commitScheduler();

        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(index,
                durationsToPostpone, FLAG_UPCOMING);

        assertCommandSuccess(postponeReminderCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        ReminderDurationList durationToPostpone = getReminderDurationList(2);
        PostponeReminderCommand postponeReminderCommand = new PostponeReminderCommand(outOfBoundIndex,
                durationToPostpone, FLAG_UPCOMING);

        assertCommandFailure(postponeReminderCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Replace all reminder list of all events in the list and update the model accordingly
     * @param events
     * @param expectedModel
     * @param durationsAfterPostpone
     */
    private void postponeRemindersToEvents(List<Event> events, Model expectedModel,
                                           ReminderDurationList durationsAfterPostpone) {
        for (Event event : events) {
            EventBuilder eventInList = new EventBuilder(event);
            Event editedEvent = eventInList.withReminderDurationList(durationsAfterPostpone).build();
            expectedModel.updateEvent(event, editedEvent);
        }
    }

}
