package seedu.address.model.accounting;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AmountTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        //null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        //Invalid amount
        assertFalse(Amount.isValidAmount(""));
        assertFalse(Amount.isValidAmount(" "));

        assertFalse(Amount.isValidAmount("abc"));
        assertFalse(Amount.isValidAmount("1 a"));
        assertFalse(Amount.isValidAmount("-1"));

        //Valid amount
        assertTrue(Amount.isValidAmount("1"));
        assertTrue(Amount.isValidAmount("20"));
        assertTrue(Amount.isValidAmount("6000"));
        assertTrue(Amount.isValidAmount("88888888"));

    }
}
