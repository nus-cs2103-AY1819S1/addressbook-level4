package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.AddressbookModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressbookModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(AddressbookModel addressbookModel, CommandHistory history) {
        requireNonNull(addressbookModel);
        addressbookModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
