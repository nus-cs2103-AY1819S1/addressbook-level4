package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.PermissionSet;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    PermissionSet requiredPermission = new PermissionSet();
    public static final String MESSAGE_LACK_REQUIRED_PERMISSION = "You do not have the permission required to use this command.";

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    protected abstract CommandResult runBody(Model model, CommandHistory history) throws CommandException;

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!haveRequiredPermission()) {
            throw new CommandException(MESSAGE_LACK_REQUIRED_PERMISSION);
        }
        return runBody(model, history);
    }

    public boolean haveRequiredPermission() {
        //TODO: Add check if have requiredPermission
        return true;
    }
}
