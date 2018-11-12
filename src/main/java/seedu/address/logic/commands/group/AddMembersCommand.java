package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.List;

import javafx.util.Pair;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.user.Username;

/**
 * Allows Users to add other Users to groups.
 */
public class AddMembersCommand extends Command {
    public static final String COMMAND_WORD = "addMembers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds other Users into a group. "
            + "Parameters: "
            + PREFIX_GROUP + "GROUP_NAME "
            + "[" + PREFIX_USERNAME + "USERNAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "My Lonely Club "
            + PREFIX_USERNAME + "Meena567 "
            + PREFIX_USERNAME + "johnnydoe";

    public static final String MESSAGE_SUCCESS = "Members added to group: %1$s";
    public static final String MESSAGE_USER_DOES_NOT_EXIST = "Sorry, username to be added is not valid. "
            + "No users were added to the group.";
    public static final String MESSAGE_NOT_IN_GROUP = "Sorry, you are not a part of the group. "
            + "You cannot add members to a group that you are not a part of.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before adding Users to a group.";
    public static final String MESSAGE_ALREADY_IN_GROUP = "Sorry, username to be added is in the group. "
            + "No users were added to the group.";
    public static final String MESSAGE_HAS_REQUEST = "Sorry, User already has a request for that group.";
    public static final String MESSAGE_NOT_ALL_UNIQUE_USERS = "Sorry, not all usernames are unique. "
            + "No users were added to the group.";

    private final Pair<Name, List<Username>> toAdd;

    /**
     * Creates an AddMembersCommand to add the users corresponding to the list of usernames in pair.
     * Adds the users to group with group name specified in pair
     */
    public AddMembersCommand(Pair<Name, List<Username>> pair) {
        requireNonNull(pair);
        toAdd = pair;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Name groupName = toAdd.getKey();
        List<Username> listUsernames = toAdd.getValue();

        // throw exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if current user is not part of the group
        if (!model.isInGroup(groupName)) {
            throw new CommandException(MESSAGE_NOT_IN_GROUP);
        }

        // throw exception if all the Users specified are not real users
        if (!model.isAllValidUsers(listUsernames)) {
            throw new CommandException(MESSAGE_USER_DOES_NOT_EXIST);
        }

        // throw exception if User is already part of the group
        if (model.hasUsersInGroup(toAdd)) {
            throw new CommandException(MESSAGE_ALREADY_IN_GROUP);
        }

        // throw exception if User already has group request
        if (model.hasRequestForUsers(toAdd)) {
            throw new CommandException(MESSAGE_HAS_REQUEST);
        }

        if (!model.isUniqueUsernames(listUsernames)) {
            throw new CommandException(MESSAGE_NOT_ALL_UNIQUE_USERS);
        }

        model.addPendingUsersGroup(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
