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


    public final String categoryName;

    public final ArrayList<Expense> expenseList = new ArrayList<>();

    /**
     * Constructs an {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CATEGORY_CONSTRAINTS);
        categoryName = category;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    public void addIntoCategory(Expense expense) {
        expenseList.add(expense);
    }

    @Override
    public String toString() {
        String categoryString = "";
        for (Expense i : expenseList){
            categoryString += i;
        }
        return categoryName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && categoryName.equals(((Category) other).categoryName)); // state check
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

}
