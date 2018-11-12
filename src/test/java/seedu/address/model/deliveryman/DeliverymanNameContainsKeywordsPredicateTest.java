package seedu.address.model.deliveryman;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.DeliverymanBuilder;

public class DeliverymanNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        DeliverymanNameContainsKeywordsPredicate firstPredicate =
                new DeliverymanNameContainsKeywordsPredicate(firstPredicateKeyword);
        DeliverymanNameContainsKeywordsPredicate secondPredicate =
                new DeliverymanNameContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliverymanNameContainsKeywordsPredicate firstPredicateCopy =
                new DeliverymanNameContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different deliveryman -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        DeliverymanNameContainsKeywordsPredicate predicate =
                new DeliverymanNameContainsKeywordsPredicate("Alice");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));

        // Keyword match 2 word
        predicate = new DeliverymanNameContainsKeywordsPredicate("Bob");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));

        // Partial Match
        predicate = new DeliverymanNameContainsKeywordsPredicate("Ali");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new DeliverymanNameContainsKeywordsPredicate("aLIce bOB");
        assertTrue(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        DeliverymanNameContainsKeywordsPredicate predicate = new DeliverymanNameContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(new DeliverymanBuilder().withName("Alice Bob").build()));
    }
}
