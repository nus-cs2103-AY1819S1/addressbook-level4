package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.OCCASION;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowOccasionRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.occasion.Occasion;

/**
 * Finds and lists all occasions in address book whose occasion name, or occasion date, or occasion location
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindOccasionCommand extends Command {
    public static final String COMMAND_WORD = "findoccasion";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all occasions whose occasion name, "
            + "occasion date, occasion location contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " on/meeting party"
            + "Example: " + COMMAND_WORD + " od/2018-01-01"
            + "Example: " + COMMAND_WORD + " loc/soc utown";

    private final Predicate<Occasion> predicate;

    public FindOccasionCommand(Predicate<Occasion> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredOccasionList(predicate);
        model.setActiveType(OCCASION);
        EventsCenter.getInstance().post(new ShowOccasionRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_OCCASIONS_LISTED_OVERVIEW, model.getFilteredOccasionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindOccasionCommand // instanceof handles nulls
                && predicate.equals(((FindOccasionCommand) other).predicate)); // state check
    }

}
