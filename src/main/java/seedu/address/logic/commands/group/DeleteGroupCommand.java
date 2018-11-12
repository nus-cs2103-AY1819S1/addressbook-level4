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
 * Deletes a group the user is a part of.
 */
public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a group from "
            + "your groups list.\n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "My Lonely Club";

    public static final String MESSAGE_SUCCESS = "Successfully deleted group with name: %1$s";
    public static final String MESSAGE_NO_SUCH_GROUP = "Sorry, the group that you want to "
            + "delete does not exist in your list of groups";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before deleting groups";

    private final Name toDelete;

    /**
     * Creates a DeleteGroupCommand that will delete the group with name groupName from the
     * currently logged in user's list of groups.
     */
    public DeleteGroupCommand(Name groupName) {
        requireNonNull(groupName);
        this.toDelete = groupName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if you try to delete a group who is not in groups
        if (!model.isInGroup(toDelete)) {
            throw new CommandException(MESSAGE_NO_SUCH_GROUP);
        }

        model.deleteGroup(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && toDelete.equals(((DeleteGroupCommand) other).toDelete));
    }
}
