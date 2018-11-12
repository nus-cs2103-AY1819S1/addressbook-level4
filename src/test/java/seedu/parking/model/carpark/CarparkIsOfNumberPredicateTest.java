package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.CarparkBuilder;

public class CarparkIsOfNumberPredicateTest {

    @Test
    public void equals() {

        CarparkIsOfNumberPredicate firstPredicate = new CarparkIsOfNumberPredicate("TJ39");
        CarparkIsOfNumberPredicate secondPredicate = new CarparkIsOfNumberPredicate("PL23");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CarparkIsOfNumberPredicate firstPredicateCopy = new CarparkIsOfNumberPredicate("TJ39");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different car park -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_carparkIsOfNumber_returnsTrue() {

        CarparkIsOfNumberPredicate predicate = new CarparkIsOfNumberPredicate("TJ39");
        assertTrue(predicate.test(new CarparkBuilder().withCarparkNumber("TJ39").build()));
    }

    @Test
    public void test_carparkIsNotOfNumber_returnsFalse() {

        CarparkIsOfNumberPredicate predicate = new CarparkIsOfNumberPredicate("TJ39");
        assertFalse(predicate.test(new CarparkBuilder().withCarparkNumber("PL20").build()));
    }
}
