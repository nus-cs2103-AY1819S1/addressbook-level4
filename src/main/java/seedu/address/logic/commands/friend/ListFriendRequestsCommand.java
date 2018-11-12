package seedu.address.logic.commands.friend;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;

/**
 * Lists the friend requests received by the logged in user.
 */
public class ListFriendRequestsCommand extends Command {
    public static final String COMMAND_WORD = "listFriendRequests";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : List all friend requests received";

    public static final String MESSAGE_SUCCESS = "Listed all friend requests received.";

    public static final String MESSAGE_NOT_LOGGED_IN =
            "You must login before listing your received friend requests.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        model.friendListing(model.getFriendRequestsList());

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}

