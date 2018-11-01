package seedu.address.logic.commands;
//@@author winsonhys

import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseTracker;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.CategoryBudget;
import seedu.address.model.budget.TotalBudget;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;


public class BudgetIntegrationTest {
    private Model model = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void addExpense_budgetUpdated() throws CommandException, NoUserSelectedException {
        double originalExpenses = this.model.getMaximumBudget().getCurrentExpenses();
        Expense toAdd = new ExpenseBuilder().withCost("2.00").build();
        new AddCommand(toAdd).execute(this.model, this.commandHistory);
        assertTrue(this.model.getMaximumBudget().getCurrentExpenses() == originalExpenses + 2.00);
    }

    @Test
    public void addExpense_categoryBudgetUpdated() throws CommandException, NoUserSelectedException {
        String categoryName = "NewCategory";
        double originalExpenses = this.model.getMaximumBudget().getCurrentExpenses();
        this.model.modifyMaximumBudget(new TotalBudget("9999.00"));
        Expense toAdd = new ExpenseBuilder().withCost("2.00").withCategory(categoryName).build();
        new SetCategoryBudgetCommand(new CategoryBudget(categoryName, "999.00"))
            .execute(this.model, this.commandHistory);
        new AddCommand(toAdd).execute(this.model, this.commandHistory);
        assertTrue(this.model.getMaximumBudget().getCurrentExpenses() == originalExpenses + 2.00);
        CategoryBudget cBudget = this.model.getMaximumBudget().getCategoryBudgets().toArray(new CategoryBudget[1])[0];
        assertTrue(cBudget.getCurrentExpenses() == 2);

    }

}
