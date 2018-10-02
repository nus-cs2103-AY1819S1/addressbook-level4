package seedu.address.model.budget;

import java.util.Calendar;

import seedu.address.model.expense.Person;

//@@author winsonhys

/**
 * Represents maximum budget of an expense tracker
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class Budget {
    private double budgetCap;
    private Calendar budgetSetTimestamp;
    private double currentExpenses;

    /**
     * Constructs a {@code Budget}
     *
     * @param budget a valid double
     */
    public Budget(double budget) {
        this.budgetCap = budget;
        this.budgetSetTimestamp = Calendar.getInstance();
        this.currentExpenses = 0.0;

    }

    /**
     * Modifies the current (@code Budget) to have a new value
     *
     * @param budget a valid double
     */
    public void modifyBudget(double budget) {
        this.budgetCap = budget;
        this.budgetSetTimestamp = Calendar.getInstance();
    }

    /**
     * Returns the most recent timestamp in which the budget is modified/created
     *
     * @return a Calendar object that consists of the most recent timestamp.
     */
    public Calendar getBudgetSetTimestamp() {
        return this.budgetSetTimestamp;
    }

    /**
     * Attemps to add expense
     * @param expense a valid expense of type double
     * @return true if expense is successfully added, false if adding expense will result in budget exceeding.
     */

    public boolean addExpense(double expense) {
        this.currentExpenses += expense;
        if (this.currentExpenses + expense > this.budgetCap) {
            return false;
        }
        return true;
    }

    public void removeExpense(Person expense) {
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
    public void alterSpending(Person target, Person editedExpense) {
        this.currentExpenses -= target.getCost().getCostValue();
        this.currentExpenses += editedExpense.getCost().getCostValue();
    }

    public double getBudgetCap() {
        return this.budgetCap;
    }

    /**
     * @return true if budget is exceeded else false
     */
    public boolean isBudgetExceeded() {
        return this.currentExpenses > this.budgetCap;
    }

    @Override
    public boolean equals(Object budget) {
        Budget anotherBudget = (Budget) budget;
        return this.currentExpenses == anotherBudget.currentExpenses
            && this.budgetSetTimestamp.equals(anotherBudget.budgetSetTimestamp)
            && this.budgetCap == anotherBudget.budgetCap;
    }
}
