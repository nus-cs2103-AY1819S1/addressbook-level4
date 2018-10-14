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
 * Accepts a friend request.
 */
public class AcceptFriendCommand extends Command {
    public static final String COMMAND_WORD = "acceptFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows a user to accept a "
            + "friend request from another user.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Meena567";

    public static final String MESSAGE_SUCCESS = "Successfully accepted friend request of: %1$s";
    public static final String MESSAGE_NO_REQUEST = "Sorry, that user is not in your "
            + "friend requests' list.";

    private final Username toAdd;

    /**
     * Accepts friend request from the specified {@code Friendship} friend.
     */
    public AcceptFriendCommand(Username toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if you try to accept a friend request that does not accept
        if (!model.hasUsernameFriendRequest(toAdd)) {
            throw new CommandException(MESSAGE_NO_REQUEST);
        }

        model.acceptFriend(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcceptFriendCommand // instanceof handles nulls
                && toAdd.equals(((AcceptFriendCommand) other).toAdd));
    }
}
