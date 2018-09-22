package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Sets the date of an event.
 */
public class SetDateCommand extends Command {

    public static final String COMMAND_WORD = "setDate";

    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Date set: %1$s";

    private final Date date;
    private Event event;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public SetDateCommand(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        event = history.getSelectedEvent();
        event.setDate(date);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, event));
    }
}
