package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Person;

/**
 * View all the permission an employee have.
 */
public class ViewPermissionCommand extends Command {

    public static final String COMMAND_WORD = "viewpermission";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display all permissions the person identified by the index number used in the displayed person list"
            + " have.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS_DISPLAY_PERMISSION = "Permission for %s: \n %s";

    private final Index targetIndex;

    public ViewPermissionCommand(Index targetIndex) {
        requiredPermission.addPermissions(Permission.ASSIGN_PERMISSION);
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = filteredPersonList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SUCCESS_DISPLAY_PERMISSION, targetPerson.getName(),
                targetPerson.getPermissionSet()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewPermissionCommand // instanceof handles nulls
                && targetIndex.equals(((ViewPermissionCommand) other).targetIndex)); // state check
    }

}
