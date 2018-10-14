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

    private final Username toDelete;

    /**
     * Creates a DeleteFriendRequestCommand that will delete
     * an invitation to add the specified {@code Integer} friend.
     */
    public DeleteFriendRequestCommand(Username toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

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
