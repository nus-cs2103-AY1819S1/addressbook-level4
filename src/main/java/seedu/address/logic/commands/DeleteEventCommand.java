package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Deletes an event identified using its displayed date and index from the address book.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the date and index number used in the displayed event list.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_INDEX + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2018-09-18 "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final EventDate targetDate;
    private final Index targetIndex;

    public DeleteEventCommand(EventDate targetDate, Index targetIndex) {
        this.targetDate = targetDate;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<List<Event>> lastShownList = model.getFilteredEventListByDate();

        // check if date exists in events in lastShownList
        if (lastShownList.isEmpty() || lastShownList.stream()
                .noneMatch(list -> list.get(0).getEventDate().equals(targetDate))) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);
        }

        List<List<Event>> targetDateList =
                lastShownList.stream()
                        .filter(list -> list.get(0).getEventDate().equals(targetDate))
                        .collect(Collectors.toList());

        // lastShownList should only have one list matching a given specific EventDate
        assert(targetDateList.size() == 1);

        List<Event> listToRemoveFrom = targetDateList.get(0);

        if (targetIndex.getZeroBased() >= listToRemoveFrom.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = listToRemoveFrom.get(targetIndex.getZeroBased());

        // reset favourite to null if deleted event is the favourite
        if (model.isFavourite(eventToDelete))
            model.updateFavourite((String)null);

        model.deleteEvent(eventToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && targetDate.equals(((DeleteEventCommand) other).targetDate)
                && targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }
}
