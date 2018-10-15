package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class DifficultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Difficulty(null));
    }

    @Test
    public void constructor_invalidDifficulty_throwsIllegalArgumentException() {
        String invalidDifficulty = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Difficulty(invalidDifficulty));
    }

    @Test
    public void isValidDifficulty() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Difficulty.isValidDifficulty(null));

        // invalid difficulty
        assertFalse(Difficulty.isValidDifficulty("")); // empty string
        assertFalse(Difficulty.isValidDifficulty(" ")); // spaces only
        assertFalse(Difficulty.isValidDifficulty("91")); // less than 3 numbers
        assertFalse(Difficulty.isValidDifficulty("phone")); // non-numeric
        assertFalse(Difficulty.isValidDifficulty("9011p041")); // alphabets within digits
        assertFalse(Difficulty.isValidDifficulty("9312 1534")); // spaces within digits

        // valid difficulty
        assertTrue(Difficulty.isValidDifficulty("3")); // exactly 3 numbers
        assertTrue(Difficulty.isValidDifficulty("1"));
        assertTrue(Difficulty.isValidDifficulty("5")); // max
    }
}
