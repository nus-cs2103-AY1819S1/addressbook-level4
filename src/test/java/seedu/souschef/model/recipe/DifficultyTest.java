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
        assertFalse(Difficulty.isValidDifficulty("6")); // above 5
        assertFalse(Difficulty.isValidDifficulty("0")); // below 1

        // valid difficulty (all combination)
        assertTrue(Difficulty.isValidDifficulty("1"));
        assertTrue(Difficulty.isValidDifficulty("2"));
        assertTrue(Difficulty.isValidDifficulty("3"));
        assertTrue(Difficulty.isValidDifficulty("4"));
        assertTrue(Difficulty.isValidDifficulty("5"));
    }
}
