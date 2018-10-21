package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

// @@author Derek-Hardy
/**
 * Deletes a group identified using it's displayed title from the address book.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the title used in the displayed group list.\n"
            + "Parameters: title (must be a valid title)\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";

    private final Group toDelete;

    /**
     * Creates an DeleteGroupCommand to delete the specified {@code Group}
     */
    public DeleteGroupCommand(Group toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasGroup(toDelete)) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }

        model.removeGroup(toDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && toDelete.equals(((DeleteGroupCommand) other).toDelete)); // state check
    }
}
