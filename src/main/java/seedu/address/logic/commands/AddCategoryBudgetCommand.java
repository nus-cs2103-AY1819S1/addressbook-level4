package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.CategoryBudget;
import seedu.address.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.address.model.exceptions.NoUserSelectedException;

/**
 * Adds a Category Budget into the expense tracker.
 */

public class AddCategoryBudgetCommand extends Command {
    public static final String COMMAND_WORD = "setBudget";
    public static final String COMMAND_ALIAS = "sb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a Category budget cap for one of the "
        + "Categories in the expense tracker "
        + "Parameters: CATEGORY MONEY (Must be a positive float with 2 decimal places)\n"
        + "Example " + COMMAND_WORD + " 130.00";

    public static final String MESSAGE_SUCCESS = "%s Budget set to %1$s";

    public static final String EXCEED_MESSAGE = "The sum of your category budgets cannot exceed your total budget";

    private CategoryBudget toSet;

    public AddCategoryBudgetCommand(CategoryBudget budget) {
        requireNonNull(budget);
        this.toSet = budget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NoUserSelectedException, CommandException {
        try {
            requireNonNull(model);
            model.addCategoryBudget(this.toSet);
            model.commitExpenseTracker();
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toSet.getCategory(), this.toSet));
        } catch (CategoryBudgetExceedTotalBudgetException catBudExc) {
            throw new CommandException(EXCEED_MESSAGE);
        }
    }

}
