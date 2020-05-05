package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.TagsContainsAllKeywordsPredicate;

/**
 * Filters and lists all tasks in schedule planner whose tags contains ALL of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterStrictCommand extends Command {

    public static final String COMMAND_WORD = "filterstrict";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all tasks whose tags match ALL of the "
            + "specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " tutorial exam cca";

    private final TagsContainsAllKeywordsPredicate predicate;

    public FilterStrictCommand(TagsContainsAllKeywordsPredicate predicate) {
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
                || (other instanceof FilterStrictCommand // instanceof handles nulls
                && predicate.equals(((FilterStrictCommand) other).predicate)); // state check
    }
}
