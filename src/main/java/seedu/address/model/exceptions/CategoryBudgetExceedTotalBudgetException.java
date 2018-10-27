package seedu.address.model.exceptions;
//@@author winsonhys

import seedu.address.model.budget.Budget;
import seedu.address.model.budget.CategoryBudget;

/**
 * Exception when trying to add category budget that exceeds the current total budget.
 */
public class CategoryBudgetExceedTotalBudgetException extends Exception {
    public CategoryBudgetExceedTotalBudgetException (CategoryBudget categoryBudget, Budget totalBudget) {
        super(String.format("The category budget of %.2f exceeds the total budget of %.2f ",
            categoryBudget.getBudgetCap(),
            totalBudget.getBudgetCap()));
    }
}
