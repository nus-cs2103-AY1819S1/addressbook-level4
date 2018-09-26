package seedu.address.model;

import java.util.HashMap;

import seedu.address.model.expense.Category;
import seedu.address.model.expense.Expense;


//@@author Jiang Chen
/**
 * Manage all Category class created during addition of expense.
 * Duplicate is not allowed.
 * */
public class CategoryList {
    private final HashMap<String, Category> list;

    /**
     * Create a HashMap of {@code Category}.
     * The key is name of {@code Category} and the value is {@code Category} itself.
     * */
    public CategoryList() {
        this.list = new HashMap<>();
    }

    /**
     * Add a new {@code Category} into CategoryList.
     * */
    public void addCategory(Category category) {
        this.list.put(category.getName(), category);
    }

    /**
     * add {@code Expense} into an existed {@code Category}.
     * */
    public void addExpense(Category category, Expense expense) {
        Category currentCate = this.list.get(category.getName());
        currentCate.addIntoCategory(expense);
    }

    public Category getCategory(String key) {
        return this.list.get(key);
    }

    public HashMap<String, Category> getList() {
        return this.list;
    }

    /**
     * Returns true if a category with the same identity as {@code category} exists in the address book.
     */
    public boolean hasCategory(Category category) {
        return this.list.containsKey(category.getName());
    }

    @Override
    public String toString() {
        String categoryString = "";
        for (String i : this.list.keySet()) {
            categoryString += i;
            categoryString += " ";
            for (Expense e : this.list.get(i).getExpenseList()) {
                categoryString += e;
            }
            categoryString += " ";
        }
        return categoryString;
    }

    @Override
    public int hashCode() {
        return this.list.hashCode();
    }


}
