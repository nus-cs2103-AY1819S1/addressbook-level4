package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderPhoneContainsKeywordPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("91122113");
        List<String> secondPredicateKeywordList = Arrays.asList("91122113", "81223123");

        OrderPhoneContainsKeywordPredicate firstPredicate =
                new OrderPhoneContainsKeywordPredicate(firstPredicateKeywordList);
        OrderPhoneContainsKeywordPredicate secondPredicate =
                new OrderPhoneContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderPhoneContainsKeywordPredicate firstPredicateCopy =
                new OrderPhoneContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        OrderPhoneContainsKeywordPredicate predicate =
                new OrderPhoneContainsKeywordPredicate(Collections.singletonList("1234"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("12343678").build()));

        // Multiple keywords
        predicate = new OrderPhoneContainsKeywordPredicate(Arrays.asList("1234", "3678"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("12343678").build()));

        // Only one matching keyword
        predicate = new OrderPhoneContainsKeywordPredicate(Arrays.asList("3678", "6789"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("12346789").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        OrderPhoneContainsKeywordPredicate predicate = new OrderPhoneContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OrderBuilder().withPhone("2345").build()));

        // Non-matching keyword
        predicate = new OrderPhoneContainsKeywordPredicate(Arrays.asList("6789"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("12343678").build()));

        // Invalid keyword
        predicate = new OrderPhoneContainsKeywordPredicate(Arrays.asList("abc123"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("123456789").build()));
    }
}
