package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class FinderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FinderContainsKeywordsPredicate firstPredicate =
                new FinderContainsKeywordsPredicate(firstPredicateKeywordList);
        FinderContainsKeywordsPredicate secondPredicate =
                new FinderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FinderContainsKeywordsPredicate firstPredicateCopy =
                new FinderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different article -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_finderContainsKeywords_returnsTrue() {
        // One keyword
        FinderContainsKeywordsPredicate predicate =
                new FinderContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").build()));

        // Multiple keywords
        predicate = new FinderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new FinderContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").build()));
    }

    @Test
    public void test_finderDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FinderContainsKeywordsPredicate predicate = new FinderContainsKeywordsPredicate(Arrays.asList("-r"));
        assertFalse(predicate.test(new ArticleBuilder().withFinder("Alice").build()));

        // match one keyword
        predicate = new FinderContainsKeywordsPredicate(Arrays.asList("Alice", "Hoe"));
        assertFalse(predicate.test(new ArticleBuilder().withFinder("Alice").build()));

        // match all but one keyword
        predicate = new FinderContainsKeywordsPredicate(Arrays.asList("Alice", "Marrissa", "Hoe"));
        assertFalse(predicate.test(new ArticleBuilder().withFinder("Alice Hoe").build()));

        // Non-matching keyword
        predicate = new FinderContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ArticleBuilder().withFinder("Alice Bob").build()));
    }
}
