package seedu.address.logic.commands;
//@@author winsonhys

import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.expense.CategoryTest.VALID_CATEGORY;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseTracker;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.CategoryBudget;
import seedu.address.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.address.model.exceptions.NoUserSelectedException;


public class SetCategoryBudgetCommandTest {
    private Model model = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
        this.commandHistory = new CommandHistory();
    }

    @Test
    public void execute_addCategoryBudget_successful() throws NoUserSelectedException,
        CategoryBudgetExceedTotalBudgetException {
        ModelManager expectedModel = new ModelManager(this.model.getExpenseTracker(), new UserPrefs());
        String validBudget = String.format("%.2f", expectedModel.getMaximumBudget().getBudgetCap() - 1);
        CategoryBudget toAdd = new CategoryBudget(VALID_CATEGORY, validBudget);
        expectedModel.addCategoryBudget(toAdd);
        expectedModel.commitExpenseTracker();
        SetCategoryBudgetCommand setCategoryBudgetCommand = new SetCategoryBudgetCommand(toAdd);
        String expectedMessage = String.format(SetCategoryBudgetCommand.MESSAGE_SUCCESS,
            toAdd.getCategory(), toAdd);

        assertCommandSuccess(setCategoryBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(expectedModel.getMaximumBudget().getCategoryBudgets().toArray().length == 1);

    }

    @Test
    public void execute_addCategoryBudget_editSuccessful() throws NoUserSelectedException,
        CategoryBudgetExceedTotalBudgetException {
        ModelManager expectedModel = new ModelManager(this.model.getExpenseTracker(), new UserPrefs());
        String validBudget = String.format("%.2f", expectedModel.getMaximumBudget().getBudgetCap() - 1);
        this.model.addCategoryBudget(new CategoryBudget(VALID_CATEGORY, validBudget));
        expectedModel.addCategoryBudget(new CategoryBudget(VALID_CATEGORY, validBudget));

        CategoryBudget toAdd = new CategoryBudget(VALID_CATEGORY, "1.00");
        expectedModel.addCategoryBudget(toAdd);
        expectedModel.commitExpenseTracker();
        SetCategoryBudgetCommand setCategoryBudgetCommand = new SetCategoryBudgetCommand(toAdd);
        String expectedMessage = String.format(SetCategoryBudgetCommand.MESSAGE_SUCCESS,
            toAdd.getCategory(), toAdd);

        assertCommandSuccess(setCategoryBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(expectedModel.getMaximumBudget()
            .getCategoryBudgets().toArray(new CategoryBudget[1])[0].getBudgetCap() == 1);
    }

    @Test
    public void execute_addCategoryBudget_unsuccessful() {
        String validExcessBudget = String.format("%.2f", this.model.getMaximumBudget().getBudgetCap() + 1);
        CategoryBudget toAdd = new CategoryBudget(VALID_CATEGORY, validExcessBudget);
        SetCategoryBudgetCommand setCategoryBudgetCommand = new SetCategoryBudgetCommand(toAdd);
        String expectedMessage = SetCategoryBudgetCommand.EXCEED_MESSAGE;
        assertCommandFailure(setCategoryBudgetCommand, model, commandHistory, expectedMessage);

    }

}
