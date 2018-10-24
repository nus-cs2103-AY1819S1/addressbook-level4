package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.parking.testutil.CarparkBuilder;

public class CarparkContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CarparkContainsKeywordsPredicate firstPredicate = new CarparkContainsKeywordsPredicate(
                firstPredicateKeywordList);
        CarparkContainsKeywordsPredicate secondPredicate = new CarparkContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CarparkContainsKeywordsPredicate firstPredicateCopy = new CarparkContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different car park -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_carparkNumberContainsKeywords_returnsTrue() {
        // One keyword
        CarparkContainsKeywordsPredicate predicate = new CarparkContainsKeywordsPredicate(
                Collections.singletonList("A1"));
        assertTrue(predicate.test(new CarparkBuilder().withCarparkNumber("A1").build()));

        // Multiple keywords
        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("PUNGGOL", "SERANGOON"));
        assertTrue(predicate.test(new CarparkBuilder().withAddress("PUNGGOL SERANGOON").build()));

        // Only one matching keyword
        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("A1", "KENT"));
        assertTrue(predicate.test(new CarparkBuilder().withCarparkNumber("A1")
                .withAddress("BLK 347 ANG MO KIO AVENUE 3").build()));

        // Mixed-case keywords
        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("a1", "nEwToN"));
        assertTrue(predicate.test(new CarparkBuilder().withCarparkNumber("A1")
                .withAddress("BLK 123 NEWTON SQUARE").build()));
    }

    @Test
    public void test_carparkNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CarparkContainsKeywordsPredicate predicate = new CarparkContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CarparkBuilder().withCarparkNumber("V6").build()));

        // Non-matching keyword
        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("V32", "SENGKANG"));
        assertFalse(predicate.test(new CarparkBuilder().withCarparkNumber("A29")
                .withAddress("BLK 347 ANG MO KIO AVENUE 3").build()));

        // Keywords match car park type, free parking, but does not match address
        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("SURFACE", "7AM-10.30PM",
                "SENGKANG"));
        assertFalse(predicate.test(new CarparkBuilder().withCarparkType("SURFACE CAR PARK")
                .withFreeParking("SUN & PH FR 7AM-10.30PM").withAddress("BLK 347 ANG MO KIO AVENUE 3").build()));
    }
}
