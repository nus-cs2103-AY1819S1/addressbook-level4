package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Enqueues a given patient (for consultation).
 */
public class EnqueueCommand extends Command {
    public static final String COMMAND_WORD = "enqueue";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Enqueues the selected patient "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer)";

    private final Index index;

    /**
     * @param index The index of the patient as in the displayed patient list.
     */
    public EnqueueCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model)
        return null;
    }
}
