package seedu.restaurant.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

public class IngredientNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IngredientName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> IngredientName.isValidName(null));

        // invalid name
        assertFalse(IngredientName.isValidName("")); // empty string
        assertFalse(IngredientName.isValidName(" ")); // spaces only
        assertFalse(IngredientName.isValidName("^")); // contains special characters
        assertFalse(IngredientName.isValidName("fish*")); // contains non-alphanumeric characters
        assertFalse(IngredientName.isValidName("12345")); // number string
        assertFalse(IngredientName.isValidName("chicken 2")); // contains numbers

        // valid name
        assertTrue(IngredientName.isValidName("Chicken")); // alphabets without space
        assertTrue(IngredientName.isValidName("chicken thigh")); // lower case alphabets
        assertTrue(IngredientName.isValidName("Chicken Thigh")); // both upper and lower case alphabets
        assertTrue(IngredientName.isValidName("Chicken Thigh without bone")); // long names
    }
}
