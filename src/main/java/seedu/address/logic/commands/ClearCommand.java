package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.ContactContainsTagPredicate;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Hallper has been cleared!";
    public static final String MESSAGE_CLEAR_SPECIFIC_SUCCESS = "Cleared contacts under %1$s in Hallper";

    private final ContactContainsTagPredicate predicate;

    private final String target;

    public ClearCommand(String target, ContactContainsTagPredicate predicate) {
        this.target = target;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (target.toLowerCase().equals("all")) {
            model.resetData(new AddressBook());
            model.commitAddressBook();
            return new CommandResult(MESSAGE_CLEAR_ALL_SUCCESS);
        } else {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(String.format(MESSAGE_CLEAR_SPECIFIC_SUCCESS, target));
        }
    }
}
