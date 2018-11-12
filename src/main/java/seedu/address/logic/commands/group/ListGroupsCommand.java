package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;

/**
 * List the groups of the currently logged in user.
 */
public class ListGroupsCommand extends Command {
    public static final String COMMAND_WORD = "listGroups";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : List all groups";
    public static final String MESSAGE_SUCCESS = "Listed all groups.";
    public static final String MESSAGE_NOT_LOGGED_IN =
            "You must login before listing your received groups.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        model.groupListing(model.getGroupList());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}

