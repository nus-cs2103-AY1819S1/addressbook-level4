package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

/**
 * Reverts the {@code model}'s scheduler to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_ALIAS_ONE = "red";
    public static final String COMMAND_ALIAS_TWO = "re";
    public static final String COMMAND_ALIAS_THREE = "r";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoScheduler()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoScheduler();
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
