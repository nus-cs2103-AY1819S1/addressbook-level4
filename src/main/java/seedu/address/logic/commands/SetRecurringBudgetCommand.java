package seedu.address.logic.commands;
//@@author winsonhys
import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.NoUserSelectedException;

import java.time.LocalDateTime;


public class SetRecurringBudgetCommand extends Command {
    public static final String COMMAND_WORD = "setRecurringFrequency";
    public static final String COMMAND_ALIAS = "srf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a frequency for budget to reset "
        + "Parameters: MONEY (Must be a positive float with 2 decimal places)\n"
        + "Example " + COMMAND_WORD + " 130.00";

    public static final String MESSAGE_SUCCESS = "Budget recurrence is set";

    private LocalDateTime toSet;

    /**
     * Creates an AddCommand to add the specified {@code Budget}
     */

    public SetRecurringBudgetCommand(LocalDateTime recurrence) {
        requireNonNull(recurrence);
        this.toSet = recurrence;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NoUserSelectedException {
        requireNonNull(model);

        model.modifyMaximumBudget(this.toSet);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
