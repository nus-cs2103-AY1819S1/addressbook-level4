package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;

//@@author Jiang Chen
/**
 * Represent a category of expenses.
 * Guarantee: details are present and not null, filed values are validated, immutable.
 */
public class Category {
    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Category can take any values, and it should not be blank";

    public static final String CATEGORY_VALIDATION_REGEX = "\\p{Alnum}+";


    private final String categoryName;

    private final ArrayList<Expense> expenseList = new ArrayList<>();

    /**
     * Constructs an {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CATEGORY_CONSTRAINTS);
        this.categoryName = category;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    /**
     * Add an Expense into the {@code expenseList} in Category.
     * */
    public void addIntoCategory(Expense expense) {
        this.expenseList.add(expense);
    }

    public ArrayList<Expense> getExpenseList() {
        return this.expenseList;
    }

    public String getName() {
        return this.categoryName;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.categoryName.equals(((Category) other).categoryName)); // state check
    }

    @Override
    public int hashCode() {
        return this.categoryName.hashCode();
    }

}
