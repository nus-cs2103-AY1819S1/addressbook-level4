package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.core.Messages;
import seedu.modsuni.commons.events.ui.ShowDatabaseTabRequestEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.CodeStartsKeywordsPredicate;

/**
 * Finds and lists all modules in the database module list whose code starts with any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all modules whose codes begin with any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CS101";

    private final CodeStartsKeywordsPredicate predicate;

    public SearchCommand(CodeStartsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDatabaseModuleList(predicate);

        EventsCenter.getInstance().post(new ShowDatabaseTabRequestEvent());

        return new CommandResult(
                String.format(Messages.MESSAGE_MODULE_LISTED_OVERVIEW, model.getFilteredDatabaseModuleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
