package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate(firstPredicateKeywordList);
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different article -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword in name field
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("wallet"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Nike wallet").build()));

        // One keyword in finder field
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").build()));

        // One keyword in description field
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("12pm"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Library").build()));

        // One keyword in phone field
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("88888888"));
        assertTrue(predicate.test(new ArticleBuilder().withPhone("88888888").build()));

        // One keyword in email field
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("lim@test.com"));
        assertTrue(predicate.test(new ArticleBuilder().withEmail("lim@test.com").build()));

        // One keyword in tag field
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("black"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("black", "blue").build()));

        // Multiple keywords in name field
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withName("Alice Bob").build()));

        // Multiple keywords in finder field
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withFinder("Alice Bob").build()));

        // Multiple keywords in description field
        predicate = new ContainsKeywordsPredicate(Arrays.asList("12pm", "Bob"));
        assertTrue(predicate.test(new ArticleBuilder().withDescription("12pm Bob").build()));

        // Multiple keywords in tag field
        predicate = new ContainsKeywordsPredicate(Arrays.asList("black", "Blue"));
        assertTrue(predicate.test(new ArticleBuilder().withTags("black", "Blue").build()));

        // Multiple keywords in different fields
        predicate = new ContainsKeywordsPredicate(Arrays.asList("waLLet", "bOB"));
        assertTrue(predicate.test(new ArticleBuilder().withName("wallet").withFinder("Bob").build()));
        predicate = new ContainsKeywordsPredicate(Arrays.asList("waLLet", "bOB", "raining"));
        assertTrue(predicate.test(new ArticleBuilder().withName("wallet").withFinder("Bob")
                .withDescription("Raining heavily").build()));
        predicate = new ContainsKeywordsPredicate(Arrays.asList("lim@test.com", "bOB", "blue"));
        assertTrue(predicate.test(new ArticleBuilder().withEmail("lim@test.com")
                .withTags("blue", "sticker").withFinder("Bob").build()));
        predicate = new ContainsKeywordsPredicate(Arrays.asList("66666666", "blue"));
        assertTrue(predicate.test(new ArticleBuilder().withPhone("66666666")
                .withTags("blue", "sticker").withDescription("blue wallet").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Arrays.asList("-r"));
        assertFalse(predicate.test(new ArticleBuilder().withName("Wallet").withDescription("test description")
                .withFinder("Bob").withPhone("99999999").withEmail("hi@tt.com").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ArticleBuilder().withName("wallet").withDescription("test description")
                .withFinder("Bob Hoe").withPhone("99999999").withEmail("hi@tt.com").build()));
    }
}
