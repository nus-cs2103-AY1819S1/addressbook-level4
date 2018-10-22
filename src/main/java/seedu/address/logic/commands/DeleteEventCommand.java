package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventDate;

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

    private final EventDate eventDate;
    private final Index targetIndex;

    public DeleteEventCommand(EventDate eventDate, Index targetIndex) {
        this.eventDate = eventDate;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("method not yet implemented");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && eventDate.equals(((DeleteEventCommand) other).eventDate)
                && targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }
}
