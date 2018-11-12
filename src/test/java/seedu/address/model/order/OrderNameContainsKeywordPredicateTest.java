package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderNameContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        OrderNameContainsKeywordPredicate firstPredicate =
                new OrderNameContainsKeywordPredicate(firstPredicateKeyword);
        OrderNameContainsKeywordPredicate secondPredicate =
                new OrderNameContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderNameContainsKeywordPredicate firstPredicateCopy =
                new OrderNameContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        OrderNameContainsKeywordPredicate predicate =
                new OrderNameContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Keyword match 2 word
        predicate = new OrderNameContainsKeywordPredicate("Bob");
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Partial Match
        predicate = new OrderNameContainsKeywordPredicate("Ali");
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new OrderNameContainsKeywordPredicate("aLIce bOB");
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        OrderNameContainsKeywordPredicate predicate = new OrderNameContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }
}
