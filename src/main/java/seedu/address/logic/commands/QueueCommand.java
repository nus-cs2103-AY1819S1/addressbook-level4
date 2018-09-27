package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class QueueCommand extends Command {
    public static final String COMMAND_WORD = "queue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the current queue";

    public static final String MESSAGE_SUCCESS = "Queue: ";
    public static final String MESSAGE_EMPTY_QUEUE = "Queue is empty";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        throw new CommandException("Not Implemented");
    }
}
