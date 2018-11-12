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
 * Creates a group with the creator automatically in the group
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group with the given group name."
            + "Parameters: "
            + PREFIX_GROUP + "GROUP\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "My Lonely Club";

    public static final String MESSAGE_SUCCESS = "Group created: %1$s";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before creating groups.";

    public static final String MESSAGE_DUPLICATE_GROUP = "Sorry, a group with that name exists."
            + " Please choose a different group name.";

    private final Name toAdd;

    /**
     * Creates an AddGroupCommand that allows user to create a group with Name toAdd.
     * User who is the creator of the group is automatically added to the group.
     */
    public AddGroupCommand(Name toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is currently logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        // throw exception if that group already exists
        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && toAdd.equals(((AddGroupCommand) other).toAdd));
    }
}
