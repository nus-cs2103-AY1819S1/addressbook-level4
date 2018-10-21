package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelToDo;

/**
 * Represents a command with hidden internal logic and the ability to be executed for todolist.
 */
public abstract class CommandToDo {
    /**
     * Executes the command and returns the result message.
     *
     * @param modelToDo   {@code ModelToDo} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException;
}
