package seedu.address.storage;
//@author winsonhys

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetTest;


public class XmlAdaptedBudgetTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final String ANOTHER_VALID_BUDGET_STRING = "3.00";


    @Test
    public void testEquals() {
        XmlAdaptedBudget validXmlAdaptedBudget = new XmlAdaptedBudget(new Budget(BudgetTest.VALID_BUDGET));

        assertNotEquals(validXmlAdaptedBudget,
            new XmlAdaptedBudget(new Budget(ANOTHER_VALID_BUDGET_STRING)));
        assertEquals(validXmlAdaptedBudget, validXmlAdaptedBudget);
        assertNotEquals(validXmlAdaptedBudget, new Object());
    }

}
