package seedu.address.model.budget;
//@@author winsonhys

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.exceptions.CategoryBudgetDoesNotExist;
import seedu.address.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.address.model.expense.Expense;
import seedu.address.storage.StorageManager;


/**
 * Represents maximum budget of an expense tracker
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class Budget {
    public static final String MESSAGE_BUDGET_CONSTRAINTS =
        "Cost should only take values in the following format: {int}.{digit}{digit}";

    public static final String BUDGET_VALIDATION_REGEX = "(\\d+).(\\d)(\\d)";

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);


    protected double budgetCap;
    protected double currentExpenses;
    protected long numberOfSecondsToRecurAgain;
    private LocalDateTime nextRecurrence;
    private HashSet<CategoryBudget> categoryBudgets;



    /**
     * Constructs a {@code Budget}
     *
     * @param budget a valid double
     */
    public Budget(String budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), BUDGET_VALIDATION_REGEX);
        this.budgetCap = Double.parseDouble(budget);
        this.nextRecurrence = null;
        this.numberOfSecondsToRecurAgain = 50000;
        this.currentExpenses = 0.0;
        this.categoryBudgets = new HashSet<>();
    }

    /**
     * Constructs a {@code Budget} with modified current expenses and recurrence
     * @param budget
     * @param currentExpenses
     * @param nextRecurrence
     * @param numberOfSecondsToRecurAgain
     */
    public Budget(double budget, double currentExpenses, LocalDateTime nextRecurrence,
                  long numberOfSecondsToRecurAgain) {
        this.budgetCap = budget;
        this.currentExpenses = currentExpenses;
        this.nextRecurrence = nextRecurrence;
        this.numberOfSecondsToRecurAgain = numberOfSecondsToRecurAgain;
        this.categoryBudgets = new HashSet<>();
    }

    /**
     * Constructs a {@code Budget} with modified current expenses
     * @param budget
     * @param currentExpenses
     */
    public Budget(double budget, double currentExpenses) {
        this.budgetCap = budget;
        this.currentExpenses = currentExpenses;
        this.nextRecurrence = null;
        this.numberOfSecondsToRecurAgain = 50000;
        this.categoryBudgets = new HashSet<>();

    }

    /**
     * Returns true if a given string is a valid budget.
     */
    public static boolean isValidBudget(String test) {
        return test.matches(BUDGET_VALIDATION_REGEX);
    }

    /**
     * Modifies the current (@code Budget) to have a new value for its current expenses
     *
     * @param expenses a valid double
     */
    public void modifyExpenses(double expenses) {
        this.currentExpenses = expenses;
    }

    /**
     * Returns the current date in which the budget is created
     *
     * @return a LocalDate object that consists of the most recent timestamp.
     */

    public LocalDateTime getNextRecurrence() {
        return this.nextRecurrence;
    }

    /**
     * Attemps to add expense
     * @param expense a valid expense of type double
     * @return true if expense is successfully added, false if adding expense will result in budget exceeding.
     */

    public boolean addExpense(double expense) {
        this.currentExpenses += expense;
        return this.currentExpenses <= this.budgetCap;
    }


    /**
     * Sets the recurrence frequency
     * @param seconds Number of seconds to recur again
     */
    public void setRecurrenceFrequency(long seconds) {
        this.numberOfSecondsToRecurAgain = seconds;
        if (this.nextRecurrence == null) {
            this.nextRecurrence = LocalDateTime.now().plusSeconds(seconds);
        }
    }

    public void removeExpense(Expense expense) {
        this.currentExpenses -= expense.getCost().getCostValue();
    }

    /**
     * Resets the total expense to 0
     */
    public void clearSpending() {
        this.currentExpenses = 0;
    }

    /**
     * Alters the current total expense
     * @param target valid expense in spending to be removed
     * @param editedExpense new expense to be added
     */
    public void alterSpending(Expense target, Expense editedExpense) {
        this.currentExpenses -= target.getCost().getCostValue();
        this.currentExpenses += editedExpense.getCost().getCostValue();
    }

    public double getBudgetCap() {
        return this.budgetCap;
    }

    public double getCurrentExpenses() {
        return this.currentExpenses;
    }

    public long getNumberOfSecondsToRecurAgain() {
        return this.numberOfSecondsToRecurAgain;
    }

    /**
     * Adds a category budget. Total sum of all category budgets cannnot exceed the budget cap.
     * @param budget
     * @throws CategoryBudgetExceedTotalBudgetException throws this if adding a category budget exceeds the current
     * total budget.
     */
    public void addCategoryBudget(CategoryBudget budget) throws CategoryBudgetExceedTotalBudgetException {
        double sumOfCurrentCategoryBudgets =
            this.categoryBudgets.stream().mapToDouble(categoryBudget -> categoryBudget.getBudgetCap()).sum();
        if (sumOfCurrentCategoryBudgets + budget.getBudgetCap() > this.getBudgetCap()) {
            throw new CategoryBudgetExceedTotalBudgetException(budget, this);
        }
        this.categoryBudgets.add(budget);
    }

    /**
     * Modifies a category budget currently in the set of category budgets.
     * @param budget
     */
    public void modifyCategoryBudget(CategoryBudget budget) throws CategoryBudgetDoesNotExist {
        if (this.categoryBudgets.remove(budget)) {
            throw new CategoryBudgetDoesNotExist(budget);
        }
        this.categoryBudgets.add(budget);
    }

    @Override
    public boolean equals(Object budget) {
        Budget anotherBudget = (Budget) budget;
        return this.currentExpenses == anotherBudget.currentExpenses
            && this.budgetCap == anotherBudget.budgetCap
            && this.numberOfSecondsToRecurAgain == anotherBudget.numberOfSecondsToRecurAgain;
    }

    @Override
    public String toString() {
        return String.format("$%f", this.budgetCap);
    }

    /**
     * Updates the current budget with the new budget if it is the start of a new month. Does nothing if not
     */
    public void checkBudgetRestart() {
        if (this.nextRecurrence == null) {
            //TODO: Notifies user that budget recurrence has not been set
            logger.info("Recurrence has not been set");
            return;
        }
        if (LocalDateTime.now().isAfter(this.nextRecurrence)) {
            this.nextRecurrence = LocalDateTime.now().plusSeconds(this.numberOfSecondsToRecurAgain);
            this.clearSpending();
            //TODO: Notifies user that budget has been restarted
            logger.info("Budget has been restarted");
        }

    }
}
