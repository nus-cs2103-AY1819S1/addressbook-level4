package seedu.souschef.logic.commands;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.UniqueType;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command<T extends UniqueType> {

    /**
     * Executes the command and returns the result message.
     *
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(History history) throws CommandException;

}
