package seedu.address.storage;
//@author winsonhys

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.model.budget.TotalBudget;
import seedu.address.model.budget.TotalBudgetTest;
import seedu.address.storage.budget.XmlAdaptedBudget;


public class XmlAdaptedTotalBudgetTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testEquals() {
        XmlAdaptedBudget validXmlAdaptedBudget = new XmlAdaptedBudget(new TotalBudget(TotalBudgetTest.VALID_BUDGET));

        String anotherValidBudgetString = "3.00";
        assertNotEquals(validXmlAdaptedBudget,
            new XmlAdaptedBudget(new TotalBudget(anotherValidBudgetString)));
        assertEquals(validXmlAdaptedBudget, validXmlAdaptedBudget);
        assertNotEquals(validXmlAdaptedBudget, new Object());
    }

}
