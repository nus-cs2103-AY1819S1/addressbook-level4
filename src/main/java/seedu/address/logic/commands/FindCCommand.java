package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import seedu.address.model.card.QuestionContainsKeywordsPredicate;

/**
 * Finds and lists all cards in trivia bundle whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCCommand extends Command {
    public static final String COMMAND_WORD = "findC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all cards whose question contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " why";

    private final QuestionContainsKeywordsPredicate predicate;

    public FindCCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, model.getFilteredCardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCCommand // instanceof handles nulls
                && predicate.equals(((FindCCommand) other).predicate)); // state check
    }
}
