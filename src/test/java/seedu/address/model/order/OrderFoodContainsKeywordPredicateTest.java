package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderFoodContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        OrderFoodContainsKeywordPredicate firstPredicate =
                new OrderFoodContainsKeywordPredicate(firstPredicateKeyword);
        OrderFoodContainsKeywordPredicate secondPredicate =
                new OrderFoodContainsKeywordPredicate(secondPredicateKeyword);

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values -> returns true
        OrderFoodContainsKeywordPredicate firstPredicateCopy =
                new OrderFoodContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different food -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_foodContainsKeywords_returnsTrue() {
        // One keyword
        OrderFoodContainsKeywordPredicate predicate =
                new OrderFoodContainsKeywordPredicate("fried");
        assertTrue(predicate.test(new OrderBuilder().withFood("fried rice").build()));

        // Keyword match 2 word
        predicate = new OrderFoodContainsKeywordPredicate("rice");
        assertTrue(predicate.test(new OrderBuilder().withFood("fried rice").build()));

        // Leading and Trailing whitespace
        predicate = new OrderFoodContainsKeywordPredicate("     rice      ");
        assertTrue(predicate.test(new OrderBuilder().withFood("fried rice").build()));

        // Mixed-case keywords
        predicate = new OrderFoodContainsKeywordPredicate("fRiEd RiCE");
        assertTrue(predicate.test(new OrderBuilder().withFood("Fried Rice").build()));
    }

    @Test
    public void test_foodDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        OrderFoodContainsKeywordPredicate predicate = new OrderFoodContainsKeywordPredicate("tea");
        assertFalse(predicate.test(new OrderBuilder().withFood("fried rice").build()));

        // Match no food
        predicate = new OrderFoodContainsKeywordPredicate("tea");
        assertFalse(predicate.test(new OrderBuilder().withFood("fried rice", "ice milo", "roti canai").build()));
    }
}
