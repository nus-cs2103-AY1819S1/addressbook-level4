package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book who has an interest that matches exactly with the argument keyword.
 * Only one interest can be specified in the keyword for every search.
 * Keyword matching is case insensitive.
 */
public class FindUserByInterestCommand extends Command {

    public static final String COMMAND_WORD = "findUserByInterest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whos has an interest "
            + "that is identical to "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " study";

    private final InterestContainsKeywordsPredicate predicate;

    public FindUserByInterestCommand(InterestContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindUserByInterestCommand // instanceof handles nulls
                && predicate.equals(((FindUserByInterestCommand) other).predicate)); // state check
    }
}
