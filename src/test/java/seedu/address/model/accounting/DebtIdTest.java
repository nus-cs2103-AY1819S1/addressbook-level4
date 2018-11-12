package seedu.address.model.accounting;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DebtIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DebtId(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DebtId(invalidId));
    }

    @Test
    public void isValidId() {
        //Invalid DebtId
        assertFalse(DebtId.isValidDebtId(""));
        assertFalse(DebtId.isValidDebtId(" "));

        assertFalse(DebtId.isValidDebtId("abc"));
        assertFalse(DebtId.isValidDebtId("1 a"));
        assertFalse(DebtId.isValidDebtId("-1"));
        assertFalse(DebtId.isValidDebtId("88888888"));
        assertFalse(DebtId.isValidDebtId("43219748327"));

        //Valid DebtId
        assertTrue(DebtId.isValidDebtId("3748392747382"));
        assertTrue(DebtId.isValidDebtId("00000000000000"));
        assertTrue(DebtId.isValidDebtId("684932108493128"));
        assertTrue(DebtId.isValidDebtId("47328149432423"));
    }
}
