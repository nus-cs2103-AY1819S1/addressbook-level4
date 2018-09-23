package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating number
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid rating numbers
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("rating")); // non-numeric
        assertFalse(Rating.isValidRating("11")); // larger than 10
        assertFalse(Rating.isValidRating("-1")); // less than 10
        assertFalse(Rating.isValidRating("A1")); // non-numeric with numeric

        // valid rating numbers
        assertTrue(Rating.isValidRating("0")); // between 0 to 10
        assertTrue(Rating.isValidRating("5"));
        assertTrue(Rating.isValidRating("10"));
    }
}
