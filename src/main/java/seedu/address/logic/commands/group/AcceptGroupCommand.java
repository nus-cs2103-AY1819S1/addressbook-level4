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
 * Accepts a group request.
 */
public class AcceptGroupCommand extends Command {
    public static final String COMMAND_WORD = "acceptGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows a user to accept a "
            + "group request.\n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "My Lonely Club";

    public static final String MESSAGE_SUCCESS = "Successfully accepted group request of: %1$s";
    public static final String MESSAGE_NO_REQUEST = "Sorry, that group is not in your "
            + "group requests' list.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before accepting group";

    private final Name groupName;

    /**
     * Creates a AcceptGroupCommand that allows logged in user to accept the group request
     */
    public AcceptGroupCommand(Name groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if you try to accept a group request that does not accept
        if (!model.hasGroupRequest(groupName)) {
            throw new CommandException(MESSAGE_NO_REQUEST);
        }

        model.acceptGroupRequest(groupName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcceptGroupCommand // instanceof handles nulls
                && groupName.equals(((AcceptGroupCommand) other).groupName));
    }
}
