package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalEntries.getTransactionFourEntries;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.cca.Cca;
import seedu.address.testutil.CcaBuilder;

/**
 * @author ericyjw
 */
public class TransactionMathTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void calculateAmountSuccessfully() {
        Cca toBeUpdated = new CcaBuilder(BASKETBALL)
            .withBudget(900)
            .withSpent(600)
            .withOutstanding(100)
            .build();

        Cca expectedCca = new CcaBuilder(BASKETBALL)
            .withBudget(900)
            .withSpent(600)
            .withOutstanding(300)
            .build();

        Cca updatedCca = TransactionMath.updateDetails(toBeUpdated);

        assertEquals(updatedCca, expectedCca);
    }

    @Test
    public void calculateAmountSuccessfully_netSpentLessThanZero() {
        Cca toBeUpdated = new CcaBuilder(BASKETBALL)
            .withBudget(400)
            .withTransaction(getTransactionFourEntries())
            .build();

        Cca expectedCca = new CcaBuilder(BASKETBALL)
            .withBudget(400)
            .withSpent(300)
            .withOutstanding(100)
            .build();

        Cca updatedCca = TransactionMath.updateDetails(toBeUpdated);

        assertEquals(updatedCca, expectedCca);
    }

    @Test
    public void outstandingLessThanBudget_throwsException() {
        Cca toBeUpdated = new CcaBuilder(BASKETBALL)
            .withBudget(100)
            .withTransaction(getTransactionFourEntries())
            .build();

        exception.expect(IllegalArgumentException.class);
        TransactionMath.updateDetails(toBeUpdated);
    }
}
