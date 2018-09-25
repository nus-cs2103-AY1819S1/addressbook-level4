package seedu.address.model.expense;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Category {
    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Category can take any values, and it should not be blank";

    /*
     * The first character of the category must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CATEGORY_VALIDATION_REGEX = "[^\\s].*";

    public final String categoryName;
    public final ArrayList<Expense> expenseList;

    /**
     * Constructs an {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CATEGORY_CONSTRAINTS);
        categoryName = category;
        expenseList = new ArrayList<>();
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) { return test.matches(CATEGORY_VALIDATION_REGEX); }

    public void addIntoCategory(Expense expense) { expenseList.add(expense); }

    @Override
    public String toString() {
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
