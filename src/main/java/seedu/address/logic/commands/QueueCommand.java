package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class QueueCommand extends Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("Queue Command should not call this version of execute");
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should opreate on.
     * @param patientQueue {@code patientQueue} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution
     */
    public abstract CommandResult execute(Model model, PatientQueue patientQueue, CommandHistory history)
        throws CommandException;

}
