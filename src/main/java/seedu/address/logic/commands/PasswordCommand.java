package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.AdminPasswordModificationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;

/**
 * Command to change the password
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "passwd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change your password\n"
        + "Example: " + COMMAND_WORD;

    public static final String STARTING_PASSWORD_MESSAGE = "Please enter in your current password.";
    public static final String FAILED_PASSWORD_MESSAGE = "Your entered password did not match. Command aborted.";
    public static final String PROGRESS_PASSWORD_MESSAGE = "Please enter in your new password.";
    public static final String SHOWING_PASSWORD_MESSAGE = "Password changed!";
    public static final String ADMIN_MODIFICATION_MESSAGE = "Warning: Modification of the admin password cannot be "
        + "corrected with undo or redo.";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        CommandResult buildingResult = new CommandResult(STARTING_PASSWORD_MESSAGE);

        if (model.getLoggedInUser().isAdminUser()) {
            buildingResult.absorb(new CommandResult(ADMIN_MODIFICATION_MESSAGE));
        }

        buildingResult.addIntercepter(oldPassword -> {
            User currentUser = model.getLoggedInUser();
            if (currentUser.getPassword().matches(oldPassword)) {
                CommandResult result = new CommandResult(PROGRESS_PASSWORD_MESSAGE);

                result.addIntercepter(newPassword -> {
                    if (!Password.isValidPassword(newPassword)) {
                        throw new CommandException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
                    }

                    if (currentUser.isAdminUser()) {
                        EventsCenter.getInstance().post(new AdminPasswordModificationEvent(new Password(newPassword)));
                        return new CommandResult(SHOWING_PASSWORD_MESSAGE);
                    }

                    Person editedPerson = new Person(currentUser.getName(), currentUser.getPhone(),
                        currentUser.getEmail(), currentUser.getAddress(), currentUser.getSalary(),
                        currentUser.getUsername(), new Password(newPassword), currentUser.getProjects(),
                        currentUser.getPermissionSet(), currentUser.getLeaveApplications(),
                        currentUser.getProfilePic());
                    model.updatePerson(currentUser.getPerson(), editedPerson);
                    model.commitAddressBook();
                    return new CommandResult(SHOWING_PASSWORD_MESSAGE);
                });
                return result;
            } else {
                return new CommandResult(FAILED_PASSWORD_MESSAGE);
            }
        });
        return buildingResult;
    }
}
