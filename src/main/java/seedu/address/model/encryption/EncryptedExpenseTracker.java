package seedu.address.model.encryption;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenseTracker;
import seedu.address.model.ReadOnlyExpenseTracker;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

public class EncryptedExpenseTracker {
    private Username username;
    private final Optional<Password> password;
    private final UniqueEncryptedExpenseList expenses;
    private final Budget maximumBudget;

    /**
     * Creates an empty EncryptedExpenseTracker with the given username.
     * @param username the username associated to the ExpenseTracker
     */
    public EncryptedExpenseTracker(Username username, Optional<Password> password) {
        this(username, password, new Budget("28.00"));
    }

    public EncryptedExpenseTracker(Username username, Optional<Password> password, Budget budget) {
        this.username = username;
        this.password = password;
        this.expenses = new UniqueEncryptedExpenseList();
        this.maximumBudget = budget;
    }

    public static EncryptedExpenseTracker encryptTracker(ReadOnlyExpenseTracker src) throws IllegalValueException {
        EncryptedExpenseTracker result =  new EncryptedExpenseTracker(src.getUsername(), src.getPassword(),
                src.getMaximumBudget());
        for (Expense expense : src.getExpenseList()) {
            result.addExpense(EncryptedExpense.encryptExpense(expense, src.getEncryptionKey()));
        }
        return result;
    }

    public ReadOnlyExpenseTracker decryptTracker(String key) throws IllegalValueException {
        ExpenseTracker result = new ExpenseTracker(username, password, key);
        for (EncryptedExpense expense : expenses) {
            result.addExpense(expense.getDecryptedExpense(key));
        }
        result.modifyMaximumBudget(maximumBudget);
        return result;
    }

    /**
     * Checks if the input password matches the password of the current user. If the user has no password, then true
     * is returned.
     * @param toCheck the password to check as an optional
     * @return true if the user has no password or if the input password matches his/her password, or else false
     */
    public boolean isMatchPassword(Optional<Password> toCheck) {
        return this.password
                .map(userPassword -> userPassword.equals(toCheck.orElse(null)))
                // if userPassword will never be equals to null if map is called
                .orElse(true); // If the current user has no password, then anyone is allowed
    }

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the tracker.
     */
    public boolean hasExpense(EncryptedExpense expense) {
        requireNonNull(expense);
        return this.expenses.contains(expense);
    }

    /**
     * Adds a expense into the tracker
     */
    public void addExpense(EncryptedExpense p) {
        this.expenses.add(p);
    }

    public Optional<Password> getPassword() {
        return password;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Budget getMaximumBudget() {
        return new Budget(this.maximumBudget.getBudgetCap(), this.maximumBudget.getCurrentExpenses(),
                this.maximumBudget.getNextRecurrence(), this.maximumBudget.getNumberOfSecondsToRecurAgain());
    }

    public UniqueEncryptedExpenseList getEncryptedExpenses() {
        return expenses;
    }
}
