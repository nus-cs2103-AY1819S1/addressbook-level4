package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.TagsContainsKeywordsPredicate;

/**
 * Filters and lists all tasks in schedule planner whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all tasks whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " tutorial exam cca";

    private final TagsContainsKeywordsPredicate predicate;

    public FilterCommand(TagsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
