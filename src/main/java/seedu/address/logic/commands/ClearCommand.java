package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.ArchiveList;
import seedu.address.model.Model;
import seedu.address.model.person.User;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new AddressBook());
        model.resetArchive(new ArchiveList());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean haveRequiredPermission(User user) {
        if (user == null) {
            return false;
        }

        return user.isAdminUser();
    }
}
