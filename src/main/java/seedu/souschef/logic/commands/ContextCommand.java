package seedu.souschef.logic.commands;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.logic.parser.Context;

/**
 * Changes context.
 */
public class ContextCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Context changed : %1$s";
    private final Context context;

    public ContextCommand(Context context) {
        this.context = context;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {
        history.setContext(context);
        return new CommandResult(String.format(MESSAGE_SUCCESS, context.command.toLowerCase()));
    }
}
