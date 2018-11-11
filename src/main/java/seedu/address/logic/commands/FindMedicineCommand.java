package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.MedicineNameContainsKeywordsPredicate;

/**
 * Finds and lists all medicines in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMedicineCommand extends Command {
    public static final String COMMAND_WORD = "findmedicine";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all medicines whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " panadol zyrtec penicillin";

    private final MedicineNameContainsKeywordsPredicate predicate;

    public FindMedicineCommand(MedicineNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredMedicineList(predicate);

        EventsCenter.getInstance().post(new ShowMedicineListEvent());

        return new CommandResult(
                String.format(Messages.MESSAGE_MEDICINES_LISTED_OVERVIEW, model.getFilteredMedicineList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMedicineCommand // instanceof handles nulls
                && predicate.equals(((FindMedicineCommand) other).predicate)); // state check
    }
}
