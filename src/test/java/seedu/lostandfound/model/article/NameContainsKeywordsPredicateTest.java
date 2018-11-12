package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different article -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Nike"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Nike Wallet").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Nike", "Wallet"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Nike Wallet").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("nIkE", "wALLET"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Nike Wallet").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("-r"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Nike").build()));

        // match one keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Nike", "Hoe"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Nike").build()));

        // match all but one keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Nike", "White", "Wallet"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Nike Wallet").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("White"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Nike Wallet").build()));
    }
}
