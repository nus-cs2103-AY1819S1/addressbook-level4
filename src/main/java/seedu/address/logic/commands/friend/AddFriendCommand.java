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
 * Adds a Friend to the user's profile.
 */
public class AddFriendCommand extends Command {
    public static final String COMMAND_WORD = "addFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends a friend request to the other User"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Meena567";

    public static final String MESSAGE_SUCCESS = "Friend request sent to: %1$s";

    public static final String MESSAGE_DUPLICATE_FRIEND_REQUEST = "You have already sent friend request to this User";
    public static final String MESSAGE_NO_SUCH_USER = "Sorry, the username does not exist";
    public static final String MESSAGE_FRIEND_ALREADY = "You are already friends with this user";
    public static final String MESSAGE_CANNOT_ADD_ONESELF = "You cannot add yourself as a friend";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before adding friends";
    public static final String MESSAGE_ACCEPT_EXISTING_REQUEST = "You have that user's friend request."
            + " Please accept that request instead of adding them as a friend.";

    private final Username toAdd;

    /**
     * Creates an AddFriendCommand which allows the currently logged in user to send a friend request
     * to user with the specified username toAdd.
     */
    public AddFriendCommand(Username toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throws exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throws exception if there exists no such username toAdd
        if (!model.hasUser(toAdd)) {
            throw new CommandException(MESSAGE_NO_SUCH_USER);
        }

        // throw exception if trying to add friend if request is already sent
        if (model.hasUsernameSentRequest(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FRIEND_REQUEST);
        }

        // throw exception if trying to add friend if already friends
        if (model.hasUsernameFriend(toAdd)) {
            throw new CommandException(MESSAGE_FRIEND_ALREADY);
        }

        // throw exception if trying to add oneself as a friend
        if (model.isSameAsCurrentUser(toAdd)) {
            throw new CommandException(MESSAGE_CANNOT_ADD_ONESELF);
        }

        // throw exception if trying to add someone who has sent you a friend request
        if (model.hasUsernameFriendRequest(toAdd)) {
            throw new CommandException(MESSAGE_ACCEPT_EXISTING_REQUEST);
        }

        model.addFriend(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFriendCommand // instanceof handles nulls
                && toAdd.equals(((AddFriendCommand) other).toAdd));
    }
}
