package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderPhoneContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "91123112";
        String secondPredicateKeyword = "8212245";

        OrderPhoneContainsKeywordPredicate firstPredicate =
                new OrderPhoneContainsKeywordPredicate(firstPredicateKeyword);
        OrderPhoneContainsKeywordPredicate secondPredicate =
                new OrderPhoneContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderPhoneContainsKeywordPredicate firstPredicateCopy =
                new OrderPhoneContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // Exact number
        OrderPhoneContainsKeywordPredicate predicate =
                new OrderPhoneContainsKeywordPredicate("12345678");
        assertTrue(predicate.test(new OrderBuilder().withPhone("12345678").build()));


        // Trailing whitespace
        predicate = new OrderPhoneContainsKeywordPredicate("12345678    ");
        assertTrue(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Spaces in between
        predicate = new OrderPhoneContainsKeywordPredicate("1234   6789");
        assertTrue(predicate.test(new OrderBuilder().withPhone("12346789").build()));

        // Spaces everywhere
        predicate = new OrderPhoneContainsKeywordPredicate("  12  34   678 9");
        assertTrue(predicate.test(new OrderBuilder().withPhone("12346789").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keyword
        OrderPhoneContainsKeywordPredicate predicate = new OrderPhoneContainsKeywordPredicate("");
        assertFalse(predicate.test(new OrderBuilder().withPhone("2345").build()));

        // Non-matching keyword
        predicate = new OrderPhoneContainsKeywordPredicate("23457890");
        assertFalse(predicate.test(new OrderBuilder().withPhone("123456789").build()));

        // Invalid keyword
        predicate = new OrderPhoneContainsKeywordPredicate("abc123");
        assertFalse(predicate.test(new OrderBuilder().withPhone("123456789").build()));
    }
}
