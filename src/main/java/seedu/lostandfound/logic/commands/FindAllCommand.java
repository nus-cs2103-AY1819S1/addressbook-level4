package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.ContainsKeywordsPredicate;

/**
 * Finds and lists all articles in article list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAllCommand extends Command {

    public static final String COMMAND_WORD = "findall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Finds all articles which fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice wallet";

    private final ContainsKeywordsPredicate predicate;

    public FindAllCommand(ContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindAllCommand // instanceof handles nulls
                && predicate.equals(((FindAllCommand) other).predicate)); // state check
    }
}
