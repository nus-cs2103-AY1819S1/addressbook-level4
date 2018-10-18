package seedu.address.model.expenses;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpensesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expenses(null));
    }

    @Test
    public void addExpense() {
        // null expense added
        Assert.assertThrows(NullPointerException.class, () -> (new Expenses()).addExpense(null));
    }
}
