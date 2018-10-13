package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.commands.exceptions.CommandException;

/**
 * Changes context.
 */
public class ContextCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Context changed : %1$s";
    private final String context;

    public ContextCommand(String context) {
        this.context = context;
    }

    @Override
    public CommandResult execute(CommandHistory history) throws CommandException {
        history.setContext(context);
        return new CommandResult(String.format(MESSAGE_SUCCESS, context));
    }
}
