package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.expense.Category;
import seedu.address.model.expense.Expense;


//@@author Jiang Chen
/**
 * Manage all Category class created during addition of expense.
 * Duplicate is not allowed.
 * */
public class CategoryList {
    public final HashMap<Category, ArrayList<Expense>> categoryList;

    /**
     * Create a HashMap of {@code Category}. The key is Category itself and the value is {@code expenseList}
     * */
    public CategoryList() {
        this.categoryList = new HashMap<>();
    }

    /**
     * Add a new {@code Category} into CategoryList.
     * */
    public void addCategory(Category category) {
        ArrayList<Expense> newExpenseList = new ArrayList<>();
        categoryList.put(category, newExpenseList);
    }

    /**
     * This is a tentative method. It is used for testing categorization.
     * */
    public void addExpense(Category category, Expense expense) {
        ArrayList<Expense> currentList = categoryList.get(category);
        currentList.add(expense);
    }

    public ArrayList<Expense> getExpenseList(Category key) {
        return categoryList.get(key);
    }

    /**
     * Returns true if a category with the same identity as {@code category} exists in the address book.
     */
    public boolean hasCategory(Category category) {
        return categoryList.containsKey(category);
    }

    @Override
    public String toString() {
        String categoryString = "";
        for (Category i : categoryList.keySet()) {
            categoryString += i;
            categoryString += " ";
            for (Expense e : categoryList.get(i)) {
                categoryString += e;
            }
            categoryString += " ";
        }
        return categoryString;
    }

    @Override
    public int hashCode() {
        return categoryList.hashCode();
    }


}
