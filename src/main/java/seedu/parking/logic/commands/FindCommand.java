package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.core.Messages;
import seedu.parking.commons.events.ui.FindResultChangedEvent;
import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;

/**
 * Finds and lists all car parks in car park finder whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String FORMAT = "find KEYWORD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all car parks whose names contain any of "
            + "the specified keywords (case-insensitive)\nand displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " punggol";

    private final CarparkContainsKeywordsPredicate predicate;

    public FindCommand(CarparkContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCarparkList(predicate);
        model.updateLastPredicateUsedByFindCommand(predicate);

        EventsCenter.getInstance().post(new FindResultChangedEvent(
                model.getFilteredCarparkList().toArray(new Carpark[]{})));
        return new CommandResult(
                String.format(Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW, model.getFilteredCarparkList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
