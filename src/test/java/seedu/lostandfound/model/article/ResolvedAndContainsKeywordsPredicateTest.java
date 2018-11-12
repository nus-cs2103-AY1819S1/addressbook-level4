package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class ResolvedAndContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ResolvedAndContainsKeywordsPredicate firstPredicate =
                new ResolvedAndContainsKeywordsPredicate(firstPredicateKeywordList);
        ResolvedAndContainsKeywordsPredicate secondPredicate =
                new ResolvedAndContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ResolvedAndContainsKeywordsPredicate firstPredicateCopy =
                new ResolvedAndContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different article -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_resolvedAndContainsKeywords_returnsTrue() {
        // no keywords
        ResolvedAndContainsKeywordsPredicate predicate =
                new ResolvedAndContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new ArticleBuilder().withIsResolved(true).build()));

        // One keyword in name field
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("wallet"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Nike wallet").withIsResolved(true).build()));

        // One keyword in finder field
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").withIsResolved(true).build()));

        // One keyword in description field
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("12pm"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Library").withIsResolved(true).build()));

        // One keyword in phone field
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("88888888"));
        assertTrue(predicate.test(new ArticleBuilder().withPhone("88888888").withIsResolved(true).build()));

        // One keyword in email field
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("lim@test.com"));
        assertTrue(predicate.test(new ArticleBuilder().withEmail("lim@test.com").withIsResolved(true).build()));

        // One keyword in tag field
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("black"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("black", "blue").withIsResolved(true).build()));

        // Multiple keywords in name field
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Alice Bob").withIsResolved(true).build()));

        // Multiple keywords in finder field
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").withIsResolved(true).build()));

        // Multiple keywords in description field
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("12pm", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Bob").withIsResolved(true).build()));

        // Multiple keywords in tag field
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("black", "Blue"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("black", "Blue").withIsResolved(true).build()));

        // Multiple keywords in different fields
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("waLLet", "bOB"));
        assertTrue(predicate.test(new ArticleBuilder().withName("wallet").withFinder("Bob")
                .withIsResolved(true).build()));
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("waLLet", "bOB", "raining"));
        assertTrue(predicate.test(new ArticleBuilder().withName("wallet").withFinder("Bob")
                .withDescription("Raining heavily").withIsResolved(true).build()));
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("lim@test.com", "bOB", "blue"));
        assertTrue(predicate.test(new ArticleBuilder().withEmail("lim@test.com").withIsResolved(true)
                .withTags("blue", "sticker").withFinder("Bob").build()));
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("66666666", "blue"));
        assertTrue(predicate.test(new ArticleBuilder().withPhone("66666666").withIsResolved(true)
                .withTags("blue", "sticker").withDescription("blue wallet").build()));
    }

    @Test
    public void test_notResolvedAndContainsKeywords_returnsFalse() {
        // no keywords and not resolved
        ResolvedAndContainsKeywordsPredicate predicate =
                new ResolvedAndContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ArticleBuilder().withIsResolved(false).build()));

        // One keyword in name field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("wallet"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Nike wallet").withIsResolved(false).build()));

        // One keyword in finder field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.test(new ArticleBuilder().withFinder("Alice Bob").withIsResolved(false).build()));

        // One keyword in description field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("12pm"));
        assertFalse(predicate.test(new ArticleBuilder().withDescription("12pm Library").withIsResolved(false).build()));

        // One keyword in phone field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("88888888"));
        assertFalse(predicate.test(new ArticleBuilder().withPhone("88888888").withIsResolved(false).build()));

        // One keyword in email field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("lim@test.com"));
        assertFalse(predicate.test(new ArticleBuilder().withEmail("lim@test.com").withIsResolved(false).build()));

        // One keyword in tag field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Collections.singletonList("black"));
        assertFalse(predicate.test(new ArticleBuilder().withTags("black", "blue").withIsResolved(false).build()));

        // Multiple keywords in name field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Alice Bob").withIsResolved(false).build()));

        // Multiple keywords in finder field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertFalse(predicate.test(new ArticleBuilder().withFinder("Alice Bob").withIsResolved(false).build()));

        // Multiple keywords in description field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("12pm", "Bob"));
        assertFalse(predicate.test(new ArticleBuilder().withDescription("12pm Bob").withIsResolved(false).build()));

        // Multiple keywords in tag field and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("black", "Blue"));
        assertFalse(predicate.test(new ArticleBuilder().withTags("black", "Blue").withIsResolved(false).build()));

        // Multiple keywords in different fields and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("waLLet", "bOB"));
        assertFalse(predicate.test(new ArticleBuilder().withName("wallet").withFinder("Bob")
                .withIsResolved(false).build()));
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("waLLet", "bOB", "raining"));
        assertFalse(predicate.test(new ArticleBuilder().withName("wallet").withFinder("Bob")
                .withDescription("Raining heavily").withIsResolved(false).build()));
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("lim@test.com", "bOB", "blue"));
        assertFalse(predicate.test(new ArticleBuilder().withEmail("lim@test.com").withIsResolved(false)
                .withTags("blue", "sticker").withFinder("Bob").build()));
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("66666666", "blue"));
        assertFalse(predicate.test(new ArticleBuilder().withPhone("66666666").withIsResolved(false)
                .withTags("blue", "sticker").withDescription("blue wallet").build()));
    }

    @Test
    public void test_resolvedButDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ResolvedAndContainsKeywordsPredicate predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("-r"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Wallet").withDescription("test description")
                .withFinder("Bob").withPhone("99999999").withEmail("hi@tt.com").withIsResolved(true).build()));

        // Non-matching keyword
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ArticleBuilder().withName("wallet").withDescription("test description")
                .withFinder("Bob Hoe").withPhone("99999999").withEmail("hi@tt.com").withIsResolved(true).build()));
    }

    @Test
    public void test_notResolvedAndDoesNotContainKeywords_returnsFalse() {
        // Zero keywords and not resolved
        ResolvedAndContainsKeywordsPredicate predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("-r"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Wallet").withDescription("test description")
                .withFinder("Bob").withPhone("99999999").withEmail("hi@tt.com").withIsResolved(false).build()));

        // Non-matching keyword and not resolved
        predicate = new ResolvedAndContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ArticleBuilder().withName("wallet").withDescription("test description")
                .withFinder("Bob Hoe").withPhone("99999999").withEmail("hi@tt.com").withIsResolved(false).build()));
    }
}
