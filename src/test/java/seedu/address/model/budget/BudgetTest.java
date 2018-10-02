package seedu.address.model.budget;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidBudget = "as";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid costs
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        assertFalse(Budget.isValidBudget("200")); // number only

        // valid costs
        assertTrue(Budget.isValidBudget("255.00"));
        assertTrue(Budget.isValidBudget("1.00")); // one dollar
        assertTrue(Budget.isValidBudget("231231232131231.00")); // high ses budget
    }
}
