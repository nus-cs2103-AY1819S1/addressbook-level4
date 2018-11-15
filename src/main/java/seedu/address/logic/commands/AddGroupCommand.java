package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Lists all persons in the address book to the user.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_group";

    public static final String ALIAS = "ag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new group. "
            + "Parameters: "
            + PREFIX_NAME + "GROUP NAME "
            + "[" + PREFIX_DESCRIPTION + "OPTIONAL DESCRIPTION]";

    public static final String MESSAGE_SUCCESS = "Added group successfully.";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final Group newGroup;

    /**
     * Creates an CreateGroupCommand to create the specified {@code Group}
     */
    public AddGroupCommand(Group group) {
        requireNonNull(group);
        newGroup = group;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        CommandUtil.addTargetToModelIfNoDuplicates(model, newGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && newGroup.equals(((AddGroupCommand) other).newGroup));
    }
}
