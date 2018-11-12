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
 * Deletes a friend request the user received.
 */
public class DeleteFriendRequestCommand extends Command {
    public static final String COMMAND_WORD = "deleteFriendRequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a friend request from the "
            + "list of friend requests.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Meena567";

    public static final String MESSAGE_SUCCESS = "Successfully deleted friend request of user : %1$s";
    public static final String MESSAGE_NO_SUCH_REQUEST = "Sorry, that User did not send you a friend request.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before deleting friend requests.";

    private final Username toDelete;

    /**
     * Creates a DeleteFriendRequestCommand that will delete friend request from user with username toDelete.
     */
    public DeleteFriendRequestCommand(Username toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if no such user sent friendRequest
        if (!model.hasUsernameFriendRequest(toDelete)) {
            throw new CommandException(MESSAGE_NO_SUCH_REQUEST);
        }

        model.deleteFriendRequest(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFriendRequestCommand // instanceof handles nulls
                && toDelete.equals(((DeleteFriendRequestCommand) other).toDelete));
    }
}
