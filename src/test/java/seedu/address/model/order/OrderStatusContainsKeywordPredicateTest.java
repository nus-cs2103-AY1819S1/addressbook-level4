package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderStatusContainsKeywordPredicateTest {
    @Test
    public void equals() {
        List<OrderStatus> firstPredicateKeyword = Collections.singletonList(new OrderStatus("PENDING"));
        List<OrderStatus> secondPredicateKeyword =
                Arrays.asList(new OrderStatus("PENDING"), new OrderStatus("ONGOING"));

        OrderStatusContainsKeywordPredicate firstPredicate =
                new OrderStatusContainsKeywordPredicate(firstPredicateKeyword);
        OrderStatusContainsKeywordPredicate secondPredicate =
                new OrderStatusContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderStatusContainsKeywordPredicate firstPredicateCopy =
                new OrderStatusContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different status -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_statusContainsKeywords_returnsTrue() {
        // One keyword
        OrderStatusContainsKeywordPredicate predicate =
                new OrderStatusContainsKeywordPredicate(Collections.singletonList(new OrderStatus("PENDING")));
        assertTrue(predicate.test(new OrderBuilder().withStatus("PENDING").build()));

        // Keyword match only 2nd status
        predicate = new OrderStatusContainsKeywordPredicate(
                Arrays.asList(new OrderStatus("PENDING"), new OrderStatus("ONGOING")));
        assertTrue(predicate.test(new OrderBuilder().withStatus("ONGOING").build()));
    }

    @Test
    public void test_statusDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        OrderStatusContainsKeywordPredicate predicate =
                new OrderStatusContainsKeywordPredicate(Arrays.asList(new OrderStatus("PENDING")));
        assertFalse(predicate.test(new OrderBuilder().withStatus("COMPLETED").build()));
    }
}
