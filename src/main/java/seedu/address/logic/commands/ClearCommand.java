package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsTagPredicate;

import java.util.List;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all persons tagged with or under "
            + "the specified keywords(case-insensitive) or clears all persons for keyword 'all'.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " all"
            + "Example: " + COMMAND_WORD + " basketball";
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Hallper has been cleared!";
    public static final String MESSAGE_CLEAR_SPECIFIC_SUCCESS = "Cleared contacts under %1$s in Hallper";

    private final ContactContainsTagPredicate predicate;

    private final List<String> target;
    private boolean deleteAll;

    public ClearCommand(List<String> target, ContactContainsTagPredicate predicate) {
        this.target = target;
        this.predicate = predicate;
        this.deleteAll = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        for (String s : target) {
            if (s.toLowerCase().equals("all")) {
                this.deleteAll = true;
                break;
            }
        }
        if (deleteAll) {
            model.resetData(new AddressBook());
            model.commitAddressBook();
            return new CommandResult(MESSAGE_CLEAR_ALL_SUCCESS);
        } else {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(String.format(MESSAGE_CLEAR_SPECIFIC_SUCCESS, target));
        }
    }
}
