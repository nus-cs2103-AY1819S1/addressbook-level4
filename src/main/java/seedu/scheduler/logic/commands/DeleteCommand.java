package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.Calendar;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
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
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();
    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        model.commitScheduler();
        Calendar service = connectToGoogleCalendar.getCalendar();
        String gEventId = String.valueOf(eventToDelete.getUuid()).replaceAll("-","");
        try {
            service.events().delete("primary", gEventId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
