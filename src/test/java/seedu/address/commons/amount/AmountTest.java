package seedu.address.commons.amount;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.commons.core.amount.Amount;

public class AmountTest {

    @Test
    public void constructor_illegalArguments_throwsExceptions() {

        assertThrows(IllegalArgumentException.class, () -> new Amount("111.111"));
        assertThrows(IllegalArgumentException.class, () -> new Amount("123412341234123.11"));
        assertThrows(IllegalArgumentException.class, () -> new Amount(".12"));
        assertThrows(IllegalArgumentException.class, () -> new Amount("123."));
        assertThrows(IllegalArgumentException.class, () -> new Amount("-.45"));
    }

    @Test
    public void negatedAmountTest() {
        Amount amountNegative = new Amount("111");
        assertTrue(amountNegative.getNegatedAmount().equals(new Amount("-111")));
    }

    @Test
    public void absoluteAmountTest() {
        Amount amountNegative = new Amount("-111");
        assertTrue(amountNegative.getAbsoluteAmount().equals(new Amount("111")));
    }

    @Test
    public void validAdd() {
        Amount amount1 = new Amount("111.00");

        Amount amount2 = new Amount("-30.00");
        // at least one parameter null -> null pointer exception
        assertThrows(NullPointerException.class, () -> Amount.add(null, amount1));
        assertThrows(NullPointerException.class, () -> Amount.add(amount2, null));

        // add is commutative -> order should not matter
        assertTrue(Amount.add(amount1, amount2).equals(Amount.add(amount2, amount1)));

        // check result
        assertTrue(Amount.add(amount1, amount2).equals(new Amount("81.00")));
    }

    @Test
    public void equals() {
        Amount amount1a = new Amount("111");
        Amount amount1b = new Amount("111");
        Amount amount1c = new Amount("111.0");
        Amount amount1d = new Amount("111.00");
        Amount amount1e = new Amount("111.00");

        Amount amount2 = new Amount("111.55");

        assertFalse(amount1a.equals(null));

        assertFalse(amount1b.equals(59456));

        assertTrue(amount1a.equals(amount1a));
        assertTrue(amount1a.equals(amount1b));
        assertTrue(amount1b.equals(amount1c));
        assertTrue(amount1c.equals(amount1d));
        assertTrue(amount1d.equals(amount1e));

        assertFalse(amount1a.equals(amount2));
    }

    @Test
    public void hashCodeTest() {
        Amount amount = new Amount("111");
        assertTrue(amount.hashCode() == amount.hashCode());
    }
}
