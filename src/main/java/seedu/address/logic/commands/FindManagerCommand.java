package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.ManagerContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose manager contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindManagerCommand extends Command {

    public static final String COMMAND_WORD = "find-m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Finds all persons whose manager contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Ben Marcus";

    private final ManagerContainsKeywordsPredicate predicate;

    public FindManagerCommand(ManagerContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindManagerCommand // instanceof handles nulls
                && predicate.equals(((FindManagerCommand) other).predicate)); // state check
    }
}
