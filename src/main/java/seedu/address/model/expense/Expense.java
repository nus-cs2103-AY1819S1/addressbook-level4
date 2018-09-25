package seedu.address.model.expense;

/**
 * Represents an Expense in the expense tracker.
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

    public Name getName() { return name; }

    public Category getCategory() { return category; }

    @Override
    public String toString() {
        return name.expenseName;
    }

}
