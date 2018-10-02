package seedu.address.model.expense;

/**
 * Temporary class for testing purpose TODO: Remove it after morphing Person to Expense
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {
    private Name name;
    private Category category;

    /**
     * Construct a temporary {@code Expense} for testing purpose.
     * @param - Name cannot be null
     * @param - category can be null
     * */
    public Expense(Name name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return this.name.expenseName;
    }

}
