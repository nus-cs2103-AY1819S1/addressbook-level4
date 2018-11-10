package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;

import java.util.List;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.Flag;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;

/**
 * Deletes a event identified using it's displayed index from the scheduler.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS_ONE = "delet";
    public static final String COMMAND_ALIAS_TWO = "dele";
    public static final String COMMAND_ALIAS_THREE = "del";
    public static final String COMMAND_ALIAS_FOUR = "de";
    public static final String COMMAND_ALIAS_FIVE = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Compulsory Parameters: INDEX (must be a positive integer)\n"
            + "Optional Flags (Only one at a time):\n"
            + "-u: delete all upcoming events\n" + "-a: delete all similar repeating events"
            + "Example: " + COMMAND_WORD + " 1 -a";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_INTERNET_ERROR = "Only local changes,"
            + "no effects on your Google Calender.";

    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();
    private final Index targetIndex;
    private final Flag[] flags;

    public DeleteCommand(Index targetIndex, Flag... flags) {
        this.targetIndex = targetIndex;
        this.flags = flags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        boolean googleCalendarIsEnabled = connectToGoogleCalendar.isGoogleCalendarEnabled();
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        int instanceIndex = EventFormatUtil.calculateInstanceIndex(lastShownList, eventToDelete);
        int totalInstance = EventFormatUtil.calculateTotalInstanceNumber(lastShownList, eventToDelete);
        boolean operationOnGoogleCalIsSuccessful;
        if (flags.length == 0) {
            operationOnGoogleCalIsSuccessful =
                    connectToGoogleCalendar.deleteOnGoogleCal(googleCalendarIsEnabled, eventToDelete, instanceIndex);
            model.deleteEvent(eventToDelete);
        } else {
            if (flags[0].equals(FLAG_UPCOMING)) {
                operationOnGoogleCalIsSuccessful =
                        connectToGoogleCalendar.deleteUpcomingOnGoogleCal(
                                googleCalendarIsEnabled, eventToDelete,
                                instanceIndex, totalInstance);
                model.deleteUpcomingEvents(eventToDelete);
            } else { //will catch FLAG_ALL
                operationOnGoogleCalIsSuccessful =
                        connectToGoogleCalendar.deleteAllOnGoogleCal (
                                googleCalendarIsEnabled, eventToDelete, instanceIndex);
                model.deleteRepeatingEvents(eventToDelete);
            }
        }

        model.commitScheduler();

        if (operationOnGoogleCalIsSuccessful | connectToGoogleCalendar.isGoogleCalendarDisabled()) {
            return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete.getEventName()));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete.getEventName())
                    + "\n" + MESSAGE_INTERNET_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
