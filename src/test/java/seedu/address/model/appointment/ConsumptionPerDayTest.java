package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ConsumptionPerDayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ConsumptionPerDay(null));
    }

    @Test
    public void constructor_invalidConsumptionPerDay_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ConsumptionPerDay(invalidAmount));
    }

    @Test
    public void isValidConsumptionPerDay() {
        // null consumption per day
        Assert.assertThrows(NullPointerException.class, () -> ConsumptionPerDay.isValidConsumptionPerDay(null));

        // invalid consumption per day
        assertFalse(ConsumptionPerDay.isValidConsumptionPerDay("")); // empty string
        assertFalse(ConsumptionPerDay.isValidConsumptionPerDay(" ")); // spaces only
        assertFalse(ConsumptionPerDay.isValidConsumptionPerDay("^")); // only non-alphanumeric characters
        assertFalse(ConsumptionPerDay.isValidConsumptionPerDay("peter*")); // contains non-alphanumeric characters
        assertFalse(ConsumptionPerDay.isValidConsumptionPerDay("peterjack")); // alphabets only
        assertFalse(ConsumptionPerDay.isValidConsumptionPerDay("123 123")); // number with space

        // valid name
        assertTrue(ConsumptionPerDay.isValidConsumptionPerDay("12345")); // numbers only
        assertTrue(ConsumptionPerDay.isValidConsumptionPerDay("123412341241241234")); // long numbers
    }
}
