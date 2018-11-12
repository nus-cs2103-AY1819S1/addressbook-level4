package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.SwitchToSearchTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Selects a calendar event identified using its displayed index from the calendar event list in the scheduler.
 */
public class SelectEventCommand extends Command {

    public static final String COMMAND_WORD = "select event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the event identified by the index number used in the displayed event list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CALENDAR_EVENT_SUCCESS = "Selected Event: %1$s";

    private final Index targetIndex;

    public SelectEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<CalendarEvent> filteredCalendarEventList = model.getFilteredAndSortedCalendarEventList();

        if (targetIndex.getZeroBased() >= filteredCalendarEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new SwitchToSearchTabEvent());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_CALENDAR_EVENT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectEventCommand // instanceof handles nulls
            && targetIndex.equals(((SelectEventCommand) other).targetIndex)); // state check
    }
}
