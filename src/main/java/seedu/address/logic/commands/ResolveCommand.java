package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.article.Article;

/**
 * Resolves an article identified using it's displayed index from the address book.
 */
public class ResolveCommand extends Command {

    public static final String COMMAND_WORD = "resolve";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resolves the person identified by the index number used in the displayed article list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESOLVED_ARTICLE_SUCCESS = "Resolved Article: %1$s";

    private final Index targetIndex;

    public ResolveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Article> lastShownList = model.getFilteredArticleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
        }

        Article articleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteArticle(articleToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_RESOLVED_ARTICLE_SUCCESS, articleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResolveCommand // instanceof handles nulls
                && targetIndex.equals(((ResolveCommand) other).targetIndex)); // state check
    }
}
