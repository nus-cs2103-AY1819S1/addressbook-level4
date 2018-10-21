package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.logic.parser.CliSyntax.LIST_OF_ALL_FLAG;

import java.util.List;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_INVALID_DELETE_FLAG = "The flag used in command is invalid.\n"
            + "Valid flags are (use one flag only)\n"
            + "-a: Delete all repeated events\n"
            + "-u: Delete all upcoming events";

    private final Index targetIndex;
    private final Flag[] flags;

    public DeleteCommand(Index targetIndex, Flag... flags) {
        this.targetIndex = targetIndex;
        this.flags = flags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        for (Flag flag : flags) {
            if (!LIST_OF_ALL_FLAG.contains(flag)) {
                throw new CommandException(MESSAGE_INVALID_DELETE_FLAG);
            }
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (flags.length == 0) {
            model.deleteEvent(eventToDelete);
        } else if (flags.length > 1){
            throw new CommandException(MESSAGE_INVALID_DELETE_FLAG);
        } else {
            if (flags[0].equals(FLAG_UPCOMING)) {
                model.deleteUpcomingEvents(eventToDelete);
            } else { //will catch FLAG_ALL
                model.deleteRepeatingEvents(eventToDelete);
            }
        }

        model.commitScheduler();
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete.getEventName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
