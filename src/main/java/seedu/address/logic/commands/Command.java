package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     * Implementation of command depends on executePrimitive.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public final CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.checkOverdue();
        CommandResult result = executePrimitive(model, history);
        //Second check is a defensive check
        model.checkOverdue();
        return result;
    }

    /**
     * Execution primitive used by execution command. Inherited commands need to override this method
     * to define the implementation of the function.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult executePrimitive(Model model, CommandHistory history) throws CommandException;
}
