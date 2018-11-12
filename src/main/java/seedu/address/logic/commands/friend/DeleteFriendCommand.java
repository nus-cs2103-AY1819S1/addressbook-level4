package seedu.address.logic.commands.friend;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Username;

/**
 * Deletes a friend in the user's friend list.
 */
public class DeleteFriendCommand extends Command {
    public static final String COMMAND_WORD = "deleteFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a user from "
            + "your friend list.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Meena567";

    public static final String MESSAGE_SUCCESS = "Successfully deleted friend with username: %1$s";
    public static final String MESSAGE_NO_SUCH_FRIEND = "Sorry, the friend that you want to "
            + "delete does not exist in your list of friends";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before deleting friends";

    private final Username toDelete;

    /**
     * Creates a DeleteFriendCommand which deletes the specified username toDelete from the
     * currently logged in user's list of friends.
     */
    public DeleteFriendCommand(Username toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if user no user is logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if you try to delete a friend who is not in friends
        if (!model.hasUsernameFriend(toDelete)) {
            throw new CommandException(MESSAGE_NO_SUCH_FRIEND);
        }

        model.deleteFriend(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFriendCommand // instanceof handles nulls
                && toDelete.equals(((DeleteFriendCommand) other).toDelete));
    }
}
