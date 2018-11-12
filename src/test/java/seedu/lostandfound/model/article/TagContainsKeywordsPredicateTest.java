package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different article -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("blue"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("blue", "worn").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("blue", "worn"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("blue", "worn").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("blue", "worn"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("blue", "worn").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("-k"));
        assertFalse(predicate.test(new ArticleBuilder().withTags("blue").build()));

        // match one keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("bear", "tiger"));
        assertFalse(predicate.test(new ArticleBuilder().withTags("tiger").build()));

        // match all but one keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("bear", "tiger", "fox"));
        assertFalse(predicate.test(new ArticleBuilder().withTags("tiger", "bear").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("red"));
        assertFalse(predicate.test(new ArticleBuilder().withTags("blue", "worn").build()));
    }
}
