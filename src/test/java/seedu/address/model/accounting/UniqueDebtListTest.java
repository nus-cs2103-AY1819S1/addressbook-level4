package seedu.address.model.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDebts.DEBT_A;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.model.accounting.exception.DebtNotFoundException;
import seedu.address.model.accounting.exception.DuplicateDebtException;

public class UniqueDebtListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueDebtList debts = new UniqueDebtList();

    /**
     * Test the NullPointerException throwing with null input for contains
     */
    @Test
    public void contains_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debts.contains(null);
    }

    /**
     * Test the failure of contains if debt is not in the UniqueDebtListTest
     */
    @Test
    public void contains_debtNotInList_returnsFalse() {
        assertFalse(debts.contains(DEBT_A));
    }

    /**
     * Test the success of contains if debt is in the UniqueDebtListTest
     */
    @Test
    public void contains_debtInList_returnsTrue() {
        debts.add(DEBT_A);
        assertTrue(debts.contains(DEBT_A));
    }

    /**
     * Test the NullPointerException throwing with null input for add
     */
    @Test
    public void add_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debts.add(null);
    }

    /**
     * Test the DuplicateDebtException throwing if adding the same debt twice
     */
    @Test
    public void add_duplicateDebt_throwsDuplicateDebtException() {
        debts.add(DEBT_A);
        thrown.expect(DuplicateDebtException.class);
        debts.add(DEBT_A);
    }

    /**
     * Test the NullPointerException throwing with null input for remove
     */
    @Test
    public void remove_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debts.remove(null);
    }

    /**
     * Test the DebtNotFoundException throwing with not exist debt input for remove.
     */
    @Test
    public void remove_debtDoesNotExist_throwsDebtNotFoundException() {
        thrown.expect(DebtNotFoundException.class);
        debts.remove(DEBT_A);
    }

    /**
     * Test the success of remove by remove all the Debt in a UniqueDebtList and
     * comparing with another empty UniqueDebtList.
     */
    @Test
    public void remove_existingDebt_removesDebt() {
        debts.add(DEBT_A);
        debts.remove(DEBT_A);
        UniqueDebtList expectedUniqueDebtList = new UniqueDebtList();
        assertEquals(expectedUniqueDebtList, debts);
    }

    /**
     * Test UnsupportedOperationException throwing by modify an unmodifiable UniqueDebtList.
     */
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        debts.asUnmodifiableObservableList().remove(0);
    }

}
