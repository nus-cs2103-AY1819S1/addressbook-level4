package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Name;

/**
 * Deletes a group request the user has received.
 */
public class DeleteGroupRequestCommand extends Command {
    public static final String COMMAND_WORD = "deleteGroupRequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a group request from the "
            + "list of group requests.\n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "My Lonely Club";

    public static final String MESSAGE_SUCCESS = "Successfully deleted group request of group : %1$s";
    public static final String MESSAGE_NO_SUCH_REQUEST = "Sorry, you do not have that group request";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before deleting group requests.";

    private final Name toDelete;

    /**
     * Creates a DeleteGroupRequestCommand that will delete the group with name toDelete
     * from the currently logged in user's list of group requests received.
     */
    public DeleteGroupRequestCommand(Name toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if there is no such group request
        if (!model.hasGroupRequest(toDelete)) {
            throw new CommandException(MESSAGE_NO_SUCH_REQUEST);
        }

        model.deleteGroupRequest(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupRequestCommand // instanceof handles nulls
                && toDelete.equals(((DeleteGroupRequestCommand) other).toDelete));
    }
}
