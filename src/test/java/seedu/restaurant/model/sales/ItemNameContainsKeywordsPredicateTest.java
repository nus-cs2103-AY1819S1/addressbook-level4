package seedu.restaurant.model.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.testutil.sales.RecordBuilder;

public class ItemNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ItemNameContainsKeywordsPredicate firstPredicate =
                new ItemNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ItemNameContainsKeywordsPredicate secondPredicate =
                new ItemNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ItemNameContainsKeywordsPredicate firstPredicateCopy =
                new ItemNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_itemNameContainsKeywords_returnsTrue() {
        // One keyword
        ItemNameContainsKeywordsPredicate predicate =
                new ItemNameContainsKeywordsPredicate(Collections.singletonList("Fried"));
        assertTrue(predicate.test(new RecordBuilder().withName("Fried Rice").build()));

        // Multiple keywords
        predicate = new ItemNameContainsKeywordsPredicate(Arrays.asList("Fried", "Rice"));
        assertTrue(predicate.test(new RecordBuilder().withName("Fried Rice").build()));

        // Only one matching keyword
        predicate = new ItemNameContainsKeywordsPredicate(Arrays.asList("Rice", "Pasta"));
        assertTrue(predicate.test(new RecordBuilder().withName("Chicken Pasta").build()));

        // Mixed-case keywords
        predicate = new ItemNameContainsKeywordsPredicate(Arrays.asList("FRieD", "rICe"));
        assertTrue(predicate.test(new RecordBuilder().withName("Fried Rice").build()));
    }

    @Test
    public void test_itemNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ItemNameContainsKeywordsPredicate predicate =
                new ItemNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecordBuilder().withName("Rice").build()));

        // Non-matching keyword
        predicate = new ItemNameContainsKeywordsPredicate(Arrays.asList("Rice"));
        assertFalse(predicate.test(new RecordBuilder().withName("Mushroom Pasta").build()));

        // Keywords match date, quantity sold, price, but does not match item name
        predicate = new ItemNameContainsKeywordsPredicate(Arrays.asList("28-02-2018", "50", "5.0"));
        assertFalse(predicate.test(new RecordBuilder().withName("Fried Rice").withDate("28-02-2018")
                        .withQuantitySold("50").withPrice("5.0").build()));
    }
}
