//@@author LZYAndy
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Find a person through his/her phone number in address book.
 * Keyword matching is case sensitive.
 */
public class FindPhoneCommand extends Command {

    public static final String COMMAND_WORD = "find/p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a person through his/her phone number "
            + "and displays that person.\n"
            + "Parameters: Phone number\n"
            + "Example: " + COMMAND_WORD + " 12345678";

    private final PhoneContainsKeywordsPredicate predicate;

    public FindPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPhoneCommand // instanceof handles nulls
                && predicate.equals(((FindPhoneCommand) other).predicate)); // state check
    }
}
//@@author LZYAndy
