package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class HealthPlanNameTest {

    @Test
    public void constructor_healthPlanName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new HealthPlanName(null));
    }

    @Test
    public void constructor_invalidHealPlanNameName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new HealthPlanName(invalidName));
    }

    @Test
    public void isValidHealthPlanName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> HealthPlanName.isValidName(null));

        // invalid name
        assertFalse(HealthPlanName.isValidName("")); // empty string
        assertFalse(HealthPlanName.isValidName(" ")); // spaces only
        assertFalse(HealthPlanName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(HealthPlanName.isValidName("lose*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(HealthPlanName.isValidName("lose weight")); // alphabets only
        assertTrue(HealthPlanName.isValidName("12345")); // numbers only
        assertTrue(HealthPlanName.isValidName("lose weight 2")); // alphanumeric characters
        assertTrue(HealthPlanName.isValidName("Lose weight")); // with capital letters
        assertTrue(HealthPlanName.isValidName("Lose weight today after following this plan")); // long names
    }
}
