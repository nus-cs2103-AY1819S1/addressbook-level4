package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy =
                new DescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different article -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("12pm"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Bob").build()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("12pm", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Bob").build()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("12pm", "bOB"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Bob").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Arrays.asList("-k"));
        assertFalse(predicate.test(new ArticleBuilder().withDescription("12pm").build()));

        // match one keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("library", "11pm"));
        assertFalse(predicate.test(new ArticleBuilder().withDescription("11pm").build()));

        // match all but one keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("library", "11pm", "raining"));
        assertFalse(predicate.test(new ArticleBuilder().withDescription("11pm library").build()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ArticleBuilder().withDescription("12pm Bob").build()));
    }
}
