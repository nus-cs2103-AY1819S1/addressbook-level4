package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RandomMeetingLocationGeneratedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.EmbedGoogleMaps;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

/**
 *  Generates a location for a particular meeting.
 */
public class GenerateLocationCommand extends Command {

    public static final String COMMAND_WORD = "generateLocation";
    public static final String COMMAND_WORD_ALIAS = "gl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": Generates a location for a specific meeting. "
            + "Parameters: "
            + PREFIX_NAME + "MEETING_NAME "
            + PREFIX_DATE + "MEETING_DATE "
            + PREFIX_START_TIME + "START_TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Project Meeting "
            + PREFIX_DATE + "2018-01-04 "
            + PREFIX_START_TIME + "1330";

    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "This event does not exist!";

    public static final String MESSAGE_SUCCESS = "Meeting location generated for ";

    private final EventName meetingLocationEventName;
    private final EventDate meetingLocationEventDate;
    private final EventTime meetingLocationEventTime;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public GenerateLocationCommand(EventName eventName, EventDate eventDate, EventTime eventTime) {
        requireNonNull(eventName);
        meetingLocationEventName = eventName;
        meetingLocationEventDate = eventDate;
        meetingLocationEventTime = eventTime;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Event> eventList = model.getFilteredEventList();
        boolean isEventFound = false;

        isEventFound = isEventFound(eventList, isEventFound);

        if (isEventFound == false) {
            throw new CommandException(MESSAGE_EVENT_DOES_NOT_EXIST);
        }

        String meetingPlaceId = EmbedGoogleMaps.getMeetingPlaceId();
        EventsCenter.getInstance().post(new RandomMeetingLocationGeneratedEvent(meetingPlaceId));
        return new CommandResult(MESSAGE_SUCCESS + meetingLocationEventName +"! Use editEventLocation to"
                + " change the location of your event if you are happy with this :)");
    }

    private boolean isEventFound(ObservableList<Event> eventList, boolean isEventFound) {
        for (Event event : eventList) {
            if (event.getEventName().equals(meetingLocationEventName)) {
                isEventFound = true;
                break;
            } else {
                isEventFound = false;
            }
        }

        for (Event event : eventList) {
            if (event.getEventDate().equals(meetingLocationEventDate)) {
                isEventFound = true;
                break;
            } else {
                isEventFound = false;
            }
        }

        for (Event event : eventList) {
            if (event.getEventStartTime().equals(meetingLocationEventTime)) {
                isEventFound = true;
                break;
            } else {
                isEventFound = false;
            }
        }
        return isEventFound;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateLocationCommand // instanceof handles nulls
                && meetingLocationEventName.equals(((GenerateLocationCommand) other).meetingLocationEventName));
    }
}
