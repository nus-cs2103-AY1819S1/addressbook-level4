package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class FoodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Food(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Food(invalidName));
    }

    @Test
    public void isValidFood() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Food.isValidFood(null));

        // invalid food
        assertFalse(Food.isValidFood("")); // empty string
        assertFalse(Food.isValidFood(" ")); // spaces only
        assertFalse(Food.isValidFood("^")); // only non-alphanumeric characters
        assertFalse(Food.isValidFood("peter*")); // contains non-alphanumeric characters

        // valid food
        assertTrue(Food.isValidFood("roti prata")); // alphabets only
        assertTrue(Food.isValidFood("chocolate milkshake")); // numbers only
        assertTrue(Food.isValidFood("ice tea")); // alphanumeric characters
        assertTrue(Food.isValidFood("rojak")); // with capital letters
        assertTrue(Food.isValidFood("lasagna")); // long names
    }
}
