package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Collections;

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
 * Finds and lists all items in menu whose tags contains the day of the week.
 */
public class TodaySpecialCommand extends Command {

    public static final String COMMAND_WORD = "today-special";

    public static final String COMMAND_ALIAS = "ts";

    private final TagContainsKeywordsPredicate predicate;

    public TodaySpecialCommand(Calendar calendar) {
        this.predicate = preparePredicate(calendar);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }

    /**
     * Parses DAY_OF_WEEK into a {@code TagContainsKeywordsPredicate}.
     */
    public static TagContainsKeywordsPredicate preparePredicate(Calendar calendar) {
        String str;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            str = "sunday";
            break;
        case Calendar.MONDAY:
            str = "monday";
            break;
        case Calendar.TUESDAY:
            str = "tuesday";
            break;
        case Calendar.WEDNESDAY:
            str = "wednesday";
            break;
        case Calendar.THURSDAY:
            str = "thursday";
            break;
        case Calendar.FRIDAY:
            str = "friday";
            break;
        case Calendar.SATURDAY:
            str = "saturday";
            break;
        default:
            str = ""; // Invalid Tag Name
            break;
        }
        return new TagContainsKeywordsPredicate(Collections.singletonList(str));
    }
}
