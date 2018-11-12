package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.EventNameContainsKeywordsPredicate;

/**
 * Finds and lists all events in scheduler whose event name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS_ONE = "fin";
    public static final String COMMAND_ALIAS_TWO = "fi";
    public static final String COMMAND_ALIAS_THREE = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose event names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " study play work";

    private final EventNameContainsKeywordsPredicate predicate;

    public FindCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
