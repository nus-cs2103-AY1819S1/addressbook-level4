package seedu.address.model.restaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RatingTest {
    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        int invalidRating = 155;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // invalid addresses
        assertFalse(Rating.isValidRating(0)); // Less than 1
        assertFalse(Rating.isValidRating(120)); // Greater than 5

        // valid addresses
        assertTrue(Rating.isValidRating(1)); // Limit
        assertTrue(Rating.isValidRating(2));
        assertTrue(Rating.isValidRating(5)); // Limit
    }
}
