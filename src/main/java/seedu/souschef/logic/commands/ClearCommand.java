package seedu.souschef.logic.commands;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Clears contents inside model.
 */
public class ClearCommand<T extends UniqueType> extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the %1$s.";

    public static final String MESSAGE_CLEAR_SUCCESS = "%1$s cleared.";

    private final Model model;

    public ClearCommand(Model<T> model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) {
        this.model.resetList();
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_CLEAR_SUCCESS, history.getContextString()));
    }
}
