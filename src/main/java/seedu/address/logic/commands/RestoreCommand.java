package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
          + ": Deletes the person identified by the index number used in the archived person list.\n"
          + "Adds the selected person to the active person list. \n"
          + "Parameters: INDEX (must be a positive integer)\n"
          + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESTORED_PERSON_SUCCESS = "Restored Person: %1$s";
    //public static final String MESSAGE_DELETE_SELF_FAILURE = "You cannot delete yourself";

    private final Index targetIndex;

    public RestoreCommand(Index targetIndex) {
        requiredPermission.addPermissions(Permission.RESTORE_EMPLOYEE);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getArchivedPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRestore = lastShownList.get(targetIndex.getZeroBased());

        model.restorePerson(personToRestore);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_RESTORED_PERSON_SUCCESS, personToRestore));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                  || (other instanceof RestoreCommand // instanceof handles nulls
                  && targetIndex.equals(((RestoreCommand) other).targetIndex)); // state check
    }
}
