//@@author LZYAndy
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Find a person through his/her email address in address book.
 * Keyword matching is case sensitive.
 */
public class FindEmailCommand extends Command {

    public static final String COMMAND_WORD = "find/e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a person through his/her email address "
            + "and displays that person.\n"
            + "Parameters: Email\n"
            + "Example: " + COMMAND_WORD + " e1234567@u.nus.edu";

    private final EmailContainsKeywordsPredicate predicate;

    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindEmailCommand // instanceof handles nulls
                && predicate.equals(((FindEmailCommand) other).predicate)); // state check
    }
}
//@@author LZYAndy
