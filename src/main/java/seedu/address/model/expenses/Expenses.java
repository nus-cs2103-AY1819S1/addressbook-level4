package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains all of the expenses incurred by the guests of a given room.
 */
public class Expenses {

    private final List<Expense> expenseList;

    /**
     * Constructs an {@code Expenses} object.
     */
    public Expenses() {
        expenseList = new LinkedList<>();
    }

    public Expenses(List<Expense> expenseList) {
        requireNonNull(expenseList);
        this.expenseList = new LinkedList<>(expenseList);
    }

    /**
     * Adds a new {@code Expense} to the current expenses.
     * New expense is added in front as it is more likely that recent expenses are accessed.
     *
     * @param newExpense The new expense incurred.
     */
    public void addExpense(Expense newExpense) {
        requireNonNull(newExpense);
        expenseList.add(0, newExpense);
    }

    public void setExpenses(Expenses expenses) {
        this.expenseList.clear();
        this.expenseList.addAll(expenses.expenseList);
    }

    /**
     * Clears all of the expense records when called.
     * Usually called when guests have checked out.
     */
    public void clearExpenses() {
        expenseList.clear();
    }

    public List<Expense> getExpensesList() {
        return Collections.unmodifiableList(expenseList);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Expense e : expenseList) {
            output.append(e.toString() + "\n");
        }
        return output.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expenses // instanceof handles nulls
                && expenseList.equals(((Expenses) other).expenseList)); // state check
    }

    @Override
    public int hashCode() {
        return expenseList.hashCode();
    }
}
