package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;

public abstract class ExpenseField {
    private String fieldString;

    public ExpenseField(String fieldString) {
        requireNonNull(fieldString);
        this.fieldString = fieldString;
    }

    @Override
    public String toString() {
        return fieldString;
    }
}
