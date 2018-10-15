package seedu.souschef.logic.commands;

import seedu.souschef.commons.core.Messages;
import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.recipe.NameContainsKeywordsPredicate;

/**
 * Finds and lists all recipes in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "find";

    private final Model<T> model;
    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(Model model, NameContainsKeywordsPredicate predicate) {
        this.model = model;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(CommandHistory history) {
        model.updateFilteredList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_LISTED_OVERVIEW, model.getFilteredList().size(),
                        history.getContext().toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && model.equals(((FindCommand<T>) other).model)
                && predicate.equals(((FindCommand<T>) other).predicate)); // state check
    }
}
