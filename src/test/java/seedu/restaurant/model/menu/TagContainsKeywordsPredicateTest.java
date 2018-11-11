package seedu.restaurant.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.testutil.menu.ItemBuilder;

//@@author yican95
public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyword = Collections.singletonList("first");
        List<String> secondPredicateKeyword = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeyword);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeyword_returnsTrue() {
        // Only 1 tag
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Monday"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Juice").withTags("Monday").build()));

        // Multiple tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Monday", "Apple"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Juice").withTags("Monday", "Apple").build()));

        // Only one matching tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Monday", "Pie"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Juice").withTags("Monday").build()));
    }

    @Test
    public void test_tagDoesNotContainKeyword_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ItemBuilder().withName("Apple").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Pie"));
        assertFalse(predicate.test(new ItemBuilder().withName("Apple Juice").withTags("Drink").build()));

        // Keywords match name and price, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Apple", "2"));
        assertFalse(predicate.test(new ItemBuilder().withName("Apple Juice").withPrice("2").build()));
    }
}
