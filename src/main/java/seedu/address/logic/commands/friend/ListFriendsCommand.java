package seedu.address.logic.commands.friend;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;

/**
 * List the friends of the logged in user.
 */
public class ListFriendsCommand extends Command {
    public static final String COMMAND_WORD = "listFriends";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : List all friends.";

    public static final String MESSAGE_SUCCESS = "Listed all friends.";

    public static final String MESSAGE_NOT_LOGGED_IN =
            "You must login before listing your friends.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        model.friendListing(model.getFriendsList());
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

}

