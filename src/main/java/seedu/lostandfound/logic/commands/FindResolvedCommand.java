package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.ResolvedAndContainsKeywordsPredicate;

/**
 * Finds and lists all articles in article list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindResolvedCommand extends Command {

    public static final String COMMAND_WORD = "findresolved";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Finds all resolved articles which fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice wallet";

    private final ResolvedAndContainsKeywordsPredicate predicate;

    public FindResolvedCommand(ResolvedAndContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredArticleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ARTICLES_LISTED_OVERVIEW, model.getFilteredArticleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindResolvedCommand // instanceof handles nulls
                && predicate.equals(((FindResolvedCommand) other).predicate)); // state check
    }
}
