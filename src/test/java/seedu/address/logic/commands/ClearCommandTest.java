package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.encryption.EncryptionUtil.DEFAULT_ENCRYPTION_KEY;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseTracker;

import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ExpenseTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.InvalidDataException;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.user.Username;
import seedu.address.testutil.ModelUtil;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyExpenseTracker_success() throws NoUserSelectedException, UserAlreadyExistsException,
            NonExistentUserException, InvalidDataException {
        Model model = ModelUtil.modelWithTestUser();
        Model expectedModel = ModelUtil.modelWithTestUser();
        expectedModel.commitExpenseTracker();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyExpenseTracker_success() throws NoUserSelectedException {
        Model model = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());

        Model expectedModel = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
        expectedModel.resetData(new ExpenseTracker(new Username("typicalExpenseTracker"), null,
                DEFAULT_ENCRYPTION_KEY));

        Budget clearedBudget = model.getMaximumBudget();
        clearedBudget.clearSpending();
        expectedModel.modifyMaximumBudget(clearedBudget);
        expectedModel.commitExpenseTracker();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
