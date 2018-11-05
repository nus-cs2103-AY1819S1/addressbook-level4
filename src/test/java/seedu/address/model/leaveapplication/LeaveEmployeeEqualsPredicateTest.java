package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Test;

import seedu.address.testutil.LeaveApplicationWithEmployeeBuilder;

public class LeaveEmployeeEqualsPredicateTest {

    @Test
    public void equals() {
        LeaveEmployeeEqualsPredicate firstPredicate = new LeaveEmployeeEqualsPredicate(ALICE);
        LeaveEmployeeEqualsPredicate secondPredicate = new LeaveEmployeeEqualsPredicate(BENSON);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LeaveEmployeeEqualsPredicate firstPredicateCopy = new LeaveEmployeeEqualsPredicate(ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_employeeMatch_returnsTrue() {
        LeaveEmployeeEqualsPredicate predicate = new LeaveEmployeeEqualsPredicate(ALICE);
        assertTrue(predicate.test(new LeaveApplicationWithEmployeeBuilder().withEmployee(ALICE).build()));
    }

    @Test
    public void test_employeeDoesNotMatch_returnsFalse() {
        LeaveEmployeeEqualsPredicate predicate = new LeaveEmployeeEqualsPredicate(ALICE);
        assertFalse(predicate.test(new LeaveApplicationWithEmployeeBuilder().withEmployee(BENSON).build()));
    }
}
