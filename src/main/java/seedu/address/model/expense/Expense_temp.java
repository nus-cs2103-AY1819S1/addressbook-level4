package seedu.address.model.expense;

/**
 * Represents an Expense in the expense tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {
    private Name name;
    private Category_temp categoryTemp;

    /**
     * Construct a temporary {@code Expense} for testing purpose.
     * @param - Name cannot be null
     * @param - categoryTemp can be null
     * */
    public Expense(Name name, Category_temp categoryTemp) {
        this.name = name;
        this.categoryTemp = categoryTemp;
    }

    public Category_temp getCategoryTemp() {
        return this.categoryTemp;
    }

    @Override
    public String toString() {
        return this.name.expenseName;
    }

}
