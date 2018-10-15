package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.commons.events.model.budget.BudgetNewMonthCheck;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.expense.Expense;
import seedu.address.model.user.Username;


//@@author JasonChong96
/**
 * Helper methods related to models.
 */
public class ModelUtil {
    public static final Username TEST_USERNAME = new Username("AAA");

    /**
     * An extension of the {@code ModelManager} where developers can pass in the time instead of checking against
     * system clock. Class of a model with a test user that is already logged in with username TEST_USERNAME. The address book is
     *      * empty. Budget cap is $28 with $10 current expenses
     */
    private static class ModelSelfInjectedTime extends ModelManager {
        private LocalDate month;
        public ModelSelfInjectedTime() throws NonExistentUserException, UserAlreadyExistsException,
            NoUserSelectedException{
            super();
            this.addUser(TEST_USERNAME);
            this.loadUserData(TEST_USERNAME);
            this.modifyMaximumBudget(new Budget(28.00, 10.00));

        }

        @Override
        protected void indicateMonthCheck() {
            raise(new BudgetNewMonthCheck(month));
        }

        protected void setMonth (LocalDate month) {
            this.month = month;
        }


    }

    /**
     * Creates a model with a test user that is already logged in with username TEST_USERNAME. The address book is
     * empty. Budget cap is $28 with $0 current expenses
     * @return a model logged in with a test user
     */
    public static Model modelWithTestUser() throws NonExistentUserException, UserAlreadyExistsException,
        NoUserSelectedException {
        Model model = new ModelManager();
        model.addUser(TEST_USERNAME);
        model.loadUserData(TEST_USERNAME);
        model.modifyMaximumBudget(new Budget(28.00, 0.00));
        return model;
    }

    /**
     * Creates a modelWithTestUser that checks against the next month
     * @return A modelWithTestUser that checks against the next month
     * @throws NonExistentUserException
     * @throws UserAlreadyExistsException
     * @throws NoUserSelectedException
     */
    public static ModelSelfInjectedTime modelWithTestUserNextMonthCheck() throws NonExistentUserException,
        UserAlreadyExistsException,
        NoUserSelectedException {
        ModelSelfInjectedTime model = new ModelSelfInjectedTime();
        model.setMonth(model.getAddressBook().getMaximumBudget().getCurrentMonth().plusMonths(1));
        return model;

    }

    /**
     * Creates a modelWithTestUser that checks against the current month
     * @return A modelWithTestUser that checks against the current month
     * @throws NonExistentUserException
     * @throws UserAlreadyExistsException
     * @throws NoUserSelectedException
     */
    public static ModelSelfInjectedTime modelWithTestUserCurrentMonthCheck() throws NonExistentUserException,
        UserAlreadyExistsException,
        NoUserSelectedException {
        ModelSelfInjectedTime model = new ModelSelfInjectedTime();
        model.setMonth(model.getAddressBook().getMaximumBudget().getCurrentMonth());
        return model;

    }
}


