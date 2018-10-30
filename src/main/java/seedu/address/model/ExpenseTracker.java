package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.UniqueExpenseList;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.NotificationHandler;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameExpense comparison)
 */
public class ExpenseTracker implements ReadOnlyExpenseTracker {

    protected Username username;
    protected Optional<Password> password;
    private final UniqueExpenseList expenses;
    private Budget maximumBudget;

    private NotificationHandler notificationHandler;

    /**
     * Creates an empty ExpenseTracker with the given username.
     * @param username the username of the ExpenseTracker
     */
    public ExpenseTracker(Username username, Optional<Password> password) {
        this.username = username;
        this.password = password;
        this.expenses = new UniqueExpenseList();
        this.maximumBudget = new Budget("28.00");
        this.notificationHandler = new NotificationHandler();
    }

    /**
     * Creates an ExpenseTracker using the Expenses in the {@code toBeCopied}
     */
    public ExpenseTracker(ReadOnlyExpenseTracker toBeCopied) {
        this(toBeCopied.getUsername(), toBeCopied.getPassword());
        this.maximumBudget = toBeCopied.getMaximumBudget();
        this.notificationHandler = toBeCopied.getNotificationHandler();
        resetData(toBeCopied);
    }
    //// budget operations


    /**
     * Modifies the maximum budget for the current expense tracker
     * @param budget a valid Budget
     */
    public void modifyMaximumBudget(Budget budget) {
        double previousSpending = this.maximumBudget.getCurrentExpenses();
        this.maximumBudget = budget;
        this.maximumBudget.modifyExpenses(previousSpending);


    }

    public void setRecurrenceFrequency(long seconds) {
        this.maximumBudget.setRecurrenceFrequency(seconds);
    }


    //// list overwrite operaticons

    /**
     * Replaces the contents of the expense list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setExpenses(expenses);
        expenses.forEach(expense -> this.maximumBudget.addExpense(expense.getCost().getCostValue()));
    }

    /**
     * Resets the existing data of this {@code ExpenseTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenseTracker newData) {
        requireNonNull(newData);
        this.setExpenses(newData.getExpenseList());
        this.maximumBudget = newData.getMaximumBudget();
    }

    /// notification-level operations

    /**
     * Adds a {@code notification} to the {@code notificationHandler}
     * @param notification to add
     */
    public void addNotification(Notification notification) {
        this.notificationHandler.add(notification);

    }

    /**
     * Replaces the contents of the notification list with {@code expenses}.
     * {@code expenses} must not contain duplicate notification lists.
     */
    public void setNotifications(ObservableList<Notification> notifications) {
        this.notificationHandler.setNotifications(notifications);
    }

    public void setNotificationHandler(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    /**
     * Checks if a {@code WarningNotification} object should be added to the list.
     * @return true if {@code WarningNotification} should be added, false otherwise.
     */
    public boolean checkIfAddWarningNotification(Budget budget) {
        return notificationHandler.isTimeToSendWarning(budget);
    }

    /**
     * Checks if a {@code TipNotification} object should be added to the list.
     * @return true if {@code TipNotification} should be added, false otherwise.
     */
    public boolean checkIfAddTipNotification() {
        return notificationHandler.isTimeToSendTip();
    }

    /**
     * Toggle the ability to send {@code TipNotification}
     * @param option to toggle to.
     */
    public void toggleTipNotification(boolean option) {
        notificationHandler.setTipEnabled(option);
    }

    /**
     * Toggle the ability to send {@code WarningNotification}
     * @param option to toggle to.
     */
    public void toggleWarningNotification(boolean option) {
        notificationHandler.setWarningEnabled(option);
    }

    public NotificationHandler getNotificationHandler() {
        return notificationHandler;
    }

    /**
     * Modify notificationHandler {@code WarningNotification}
     */
    public void modifyNotificationHandler(LocalDateTime date, boolean isTipEnabled, boolean isWarningEnabled) {
        notificationHandler = new NotificationHandler(date, isTipEnabled, isWarningEnabled);
    }

    //// expense-level operations

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the address book.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return this.expenses.contains(expense);
    }

    /**
     * Adds a expense into the address book
     * @return true if expense is successfully added withouot exceeding budget, else false
     */
    public boolean addExpense(Expense p) {
        this.expenses.add(p);
        return this.maximumBudget.addExpense(p.getCost().getCostValue());
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedExpense}
     * must not be the same as another existing expense in the address book.
     */
    public void updateExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        this.expenses.setExpense(target, editedExpense);
        this.maximumBudget.alterSpending(target, editedExpense);

    }

    /**
     * Removes {@code key} from this {@code ExpenseTracker}.
     * {@code key} must exist in the address book.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
        this.maximumBudget.removeExpense(key);
    }

    public Budget getMaximumBudget() {
        return new Budget(this.maximumBudget.getBudgetCap(), this.maximumBudget.getCurrentExpenses(),
            this.maximumBudget.getNextRecurrence(), this.maximumBudget.getNumberOfSecondsToRecurAgain());
    }

    @Override
    public Username getUsername() {
        return username;
    }

    @Override
    public Optional<Password> getPassword() {
        return password;
    }

    @Override
    public boolean isMatchPassword(Optional<Password> toCheck) {
        return this.password
                .map(userPassword -> userPassword.equals(toCheck.orElse(null)))
                // if userPassword will never be equals to null if map is called
                .orElse(true); // If the current user has no password, then anyone is allowed
    }

    public void setUsername(Username newUsername) {
        this.username = newUsername;
    }
    //// util methods

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Notification> getNotificationList() {
        return notificationHandler.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        System.out.println("E: " + expenses.equals(((ExpenseTracker) other).expenses));
        System.out.println("B: " + this.maximumBudget.equals(((ExpenseTracker) other).maximumBudget));
        System.out.println("N: " + this.notificationHandler.equals(((ExpenseTracker) other).notificationHandler));
        return other == this // short circuit if same object
                || (other instanceof ExpenseTracker // instanceof handles nulls
                && expenses.equals(((ExpenseTracker) other).expenses))
                && this.maximumBudget.equals(((ExpenseTracker) other).maximumBudget)
                && this.notificationHandler.equals(((ExpenseTracker) other).notificationHandler);
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }

}
