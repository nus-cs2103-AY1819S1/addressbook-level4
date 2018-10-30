package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.calendarevent.TitleContainsKeywordsPredicate;

/**
 * Finds and lists all events in the calendar of the scheduler whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "find event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " cs1010s ma3220 cs2103";

    private final TitleContainsKeywordsPredicate predicate;

    public FindEventCommand(TitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCalendarEventList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW,
                model.getFilteredCalendarEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindEventCommand // instanceof handles nulls
            && predicate.equals(((FindEventCommand) other).predicate)); // state check
    }

    // TODO this will have to switch tabs
}
