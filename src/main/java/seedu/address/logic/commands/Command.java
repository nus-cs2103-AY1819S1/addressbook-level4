package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.User;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 * Set permission needed to execute the method by adding it into requiredPermission variable.
 */
public abstract class Command {

    public static final String MESSAGE_LACK_REQUIRED_PERMISSION = "You do not have the permission required"
            + " to use this command.";

    protected PermissionSet requiredPermission = new PermissionSet();

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    protected abstract CommandResult runBody(Model model, CommandHistory history) throws CommandException;

    /**
     * Check if user have required permission, then executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!haveRequiredPermission(model.getLoggedInUser())) {
            throw new CommandException(MESSAGE_LACK_REQUIRED_PERMISSION);
        }
        return runBody(model, history);
    }

    /**
     * Check if user have permissions required by the command.
     *
     * @return true if have permissions, false otherwise.
     */
    public boolean haveRequiredPermission(User user) {
        if (requiredPermission.getGrantedPermission().size() == 0) {
            return true;
        }

        if (user == null) {
            return false;
        }

        return user.getPermissionSet().containsAll(requiredPermission);
    }
}
