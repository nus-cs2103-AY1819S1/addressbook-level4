package seedu.address.model.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDebts.DEBT_A;
import static seedu.address.testutil.TypicalUsers.getTypicalUsers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DebtTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void changeDebtStatus() {
        DEBT_A.changeDebtStatus(DebtStatus.ACCEPTED);
        assertEquals(DEBT_A.getDebtStatus(), DebtStatus.ACCEPTED);

        DEBT_A.changeDebtStatus(DebtStatus.BALANCED);
        assertFalse(DEBT_A.getDebtStatus().equals(DebtStatus.ACCEPTED));

        thrown.expect(NullPointerException.class);
        DEBT_A.changeDebtStatus(null);
    }

    @Test
    public void changeDebtAmount() {

        DEBT_A.changeDebtAmount(new Amount("14"));
        assertEquals(DEBT_A.getAmount(), new Amount("14"));

        DEBT_A.changeDebtAmount(new Amount("7"));
        assertFalse(DEBT_A.getAmount().equals(new Amount("13")));

        thrown.expect(NullPointerException.class);
        DEBT_A.changeDebtAmount(null);
    }

    @Test
    public void equals() {
        Debt test = DEBT_A;
        assertTrue(DEBT_A.equals(test));

        assertTrue(DEBT_A.equals(DEBT_A));

        assertFalse(DEBT_A.equals(null));

        assertFalse(DEBT_A.equals(5));

        //assertFalse(DEBT_A.equals(DEBT_B));

        test = new Debt(getTypicalUsers().get(2), DEBT_A.getDebtor(),
                DEBT_A.getAmount(), DEBT_A.getDebtId(), DEBT_A.getDebtStatus());
        assertFalse(DEBT_A.equals(test));

        test = new Debt(DEBT_A.getCreditor(), getTypicalUsers().get(2),
                DEBT_A.getAmount(), DEBT_A.getDebtId(), DEBT_A.getDebtStatus());
        assertFalse(DEBT_A.equals(test));

        test = new Debt(DEBT_A.getCreditor(), DEBT_A.getDebtor(),
                new Amount("100"), DEBT_A.getDebtId(), DEBT_A.getDebtStatus());
        assertFalse(DEBT_A.equals(test));

        test = new Debt(DEBT_A.getCreditor(), DEBT_A.getDebtor(),
                DEBT_A.getAmount(), new DebtId("3333333333333"), DEBT_A.getDebtStatus());
        assertFalse(DEBT_A.equals(test));

        test = new Debt(DEBT_A.getCreditor(), DEBT_A.getDebtor(),
                DEBT_A.getAmount(), DEBT_A.getDebtId(), DebtStatus.CLEARED);
        assertFalse(DEBT_A.equals(test));

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        test = new Debt(DEBT_A.getCreditor(), DEBT_A.getDebtor(), DEBT_A.getAmount());
        assertFalse(DEBT_A.equals(test));
    }

}
