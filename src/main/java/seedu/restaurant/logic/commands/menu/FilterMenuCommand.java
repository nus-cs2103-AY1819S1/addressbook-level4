package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.TagContainsKeywordsPredicate;

//@@author yican95
/**
 * Finds and lists all items in menu whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterMenuCommand extends Command {

    public static final String COMMAND_WORD = "filter-menu";

    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " monday tuesday wednesday";

    private final TagContainsKeywordsPredicate predicate;

    public FilterMenuCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterMenuCommand // instanceof handles nulls
                    && predicate.equals(((FilterMenuCommand) other).predicate)); // state check
    }
}
