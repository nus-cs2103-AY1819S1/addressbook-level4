package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_4_ENTRIES;

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
    public void calculate_amount_successfully() {
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
    public void calculate_amount_successfully_netSpentLessThanZero() {
        Cca toBeUpdated = new CcaBuilder(BASKETBALL)
            .withBudget(400)
            .withTransaction(TRANSACTION_4_ENTRIES)
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
    public void outstanding_less_than_budget_throwsException() {
        Cca toBeUpdated = new CcaBuilder(BASKETBALL)
            .withBudget(100)
            .withTransaction(TRANSACTION_4_ENTRIES)
            .build();

        exception.expect(IllegalArgumentException.class);
        TransactionMath.updateDetails(toBeUpdated);
    }
}
