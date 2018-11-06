package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RandomMeetingLocationGeneratedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.EmbedGoogleMaps;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 *  Generates a location for a particular meeting.
 */
public class GenerateLocationCommand extends Command {

    public static final String COMMAND_WORD = "generateLocation";
    public static final String COMMAND_WORD_ALIAS = "gl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": Generates a location for a specific meeting."
            + "Parameters: "
            + PREFIX_NAME + "MEETING_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Project Meeting";

    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "This event does not exist!";

    public static final String MESSAGE_SUCCESS = "Meeting location generated!";

    private final EventName meetingLocationEventName;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public GenerateLocationCommand(EventName eventName) {
        requireNonNull(eventName);
        meetingLocationEventName = eventName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Event> eventList = model.getFilteredEventList();
        boolean isEventFound = false;

        for (Event event : eventList) {
            if (event.getEventName().equals(meetingLocationEventName)) {
                isEventFound = true;
                break;
            }
        }

        if (isEventFound == false) {
            throw new CommandException(MESSAGE_EVENT_DOES_NOT_EXIST);
        }

        String meetingPlaceId = EmbedGoogleMaps.getYihPlaceId();
        EventsCenter.getInstance().post(new RandomMeetingLocationGeneratedEvent(meetingPlaceId));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateLocationCommand // instanceof handles nulls
                && meetingLocationEventName.equals(((GenerateLocationCommand) other).meetingLocationEventName));
    }
}
