package seedu.address.model.wish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SAVED_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SAVED_AMOUNT_BOB;

import org.junit.Test;

import seedu.address.commons.core.amount.Amount;
import seedu.address.testutil.Assert;

public class SavedAmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SavedAmount((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new SavedAmount((SavedAmount) null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String[] invalidSavedAmounts = { "1234123412341234.67", // larger than 1000 trillion
            "11.111", // too many decimal places
            "-111.1", // negative value
            ".90" // no ones place
        };

        Assert.assertThrows(IllegalArgumentException.class,
                SavedAmount.MESSAGE_SAVED_AMOUNT_TOO_LARGE, () -> new SavedAmount(invalidSavedAmounts[0]));

        Assert.assertThrows(IllegalArgumentException.class,
                SavedAmount.MESSAGE_SAVED_AMOUNT_INVALID, () -> new SavedAmount(invalidSavedAmounts[1]));

        Assert.assertThrows(IllegalArgumentException.class,
                SavedAmount.MESSAGE_SAVED_AMOUNT_NEGATIVE, () -> new SavedAmount(invalidSavedAmounts[2]));

        Assert.assertThrows(IllegalArgumentException.class,
                SavedAmount.MESSAGE_SAVED_AMOUNT_INVALID, () -> new SavedAmount(invalidSavedAmounts[3]));
    }

    @Test
    public void copyConstructor_success() {
        SavedAmount savedAmount = new SavedAmount("1");
        SavedAmount copy = new SavedAmount(savedAmount);
        assertEquals(copy, savedAmount);
    }

    @Test
    public void isValidSavedAmount() {
        // null saved amount
        Assert.assertThrows(NullPointerException.class, () -> SavedAmount.isValidSavedAmount(null));

        // invalid prices
        assertFalse(SavedAmount.isValidSavedAmount("")); // empty string
        assertFalse(SavedAmount.isValidSavedAmount(" ")); // spaces only
        assertFalse(SavedAmount.isValidSavedAmount("91..1")); // double dot
        assertFalse(SavedAmount.isValidSavedAmount("price")); // non-numeric
        assertFalse(SavedAmount.isValidSavedAmount("1.0e321")); // exp
        assertFalse(SavedAmount.isValidSavedAmount("9312 1534")); // spaces within digits
        assertFalse(SavedAmount.isValidSavedAmount("2.")); // no digits after decimal point

        // valid prices
        assertTrue(SavedAmount.isValidSavedAmount("+93121534")); // no decimal digit
        assertTrue(SavedAmount.isValidSavedAmount("1.0")); // one decimal digit
        assertTrue(SavedAmount.isValidSavedAmount("-1.02")); // two decimal digits
    }

    @Test
    public void isSameAmount() {
        Amount amount = new Amount(VALID_SAVED_AMOUNT_BOB);
        SavedAmount savedAmount = new SavedAmount(VALID_SAVED_AMOUNT_BOB);
        assertEquals(amount, savedAmount.getAmount());
    }

    @Test
    public void incrementSavedAmountIsValid() {
        SavedAmount savedAmount0 = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        SavedAmount savedAmount1 = new SavedAmount(VALID_SAVED_AMOUNT_BOB);

        SavedAmount sum = savedAmount0.incrementSavedAmount(savedAmount1.getAmount());
        SavedAmount expected = new SavedAmount("23.23");
        assertTrue(sum.equals(expected));
    }

    @Test
    public void invalidIncrementSavedAmount() {
        SavedAmount savedAmount0 = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        Amount savedAmount1Negative = new Amount("-" + VALID_SAVED_AMOUNT_BOB);

        Assert.assertThrows(IllegalArgumentException.class,
                SavedAmount.MESSAGE_SAVED_AMOUNT_NEGATIVE, () ->
                        savedAmount0.incrementSavedAmount(savedAmount1Negative)
        );
    }

    @Test
    public void equals() {
        SavedAmount savedAmount1 = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        SavedAmount savedAmount1b = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        SavedAmount savedAmount2 = new SavedAmount(VALID_SAVED_AMOUNT_BOB);

        // same object
        assertTrue(savedAmount1.equals(savedAmount1));

        // same value
        assertTrue(savedAmount1.equals(savedAmount1b));

        // different value
        assertFalse(savedAmount1.equals(savedAmount2));

        // null
        assertFalse(savedAmount1.equals(null));

        // different type
        assertFalse(savedAmount1.equals(new Name(VALID_NAME_AMY)));
    }

    @Test
    public void hashCodeTest() {
        SavedAmount savedAmount0 = new SavedAmount(VALID_SAVED_AMOUNT_AMY);

        assertTrue(savedAmount0.hashCode() == savedAmount0.hashCode());
    }

    @Test
    public void toStringTest() {
        SavedAmount savedAmount0 = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        assertTrue(savedAmount0.toString().equals(VALID_SAVED_AMOUNT_AMY));
    }
}
