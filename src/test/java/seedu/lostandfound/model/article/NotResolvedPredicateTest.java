package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleBuilder;

public class NotResolvedPredicateTest {

    @Test
    public void test_notResolved_returnsTrue() {

        NotResolvedPredicate predicate = new NotResolvedPredicate();
        assertTrue(predicate.test(new ArticleBuilder().withIsResolved(false).build()));

        // Multiple fields
        assertTrue(predicate.test(new ArticleBuilder().withName("wallet").withDescription("test description")
                .withFinder("Bob Hoe").withPhone("99999999").withEmail("hi@tt.com").withIsResolved(false).build()));
    }

    @Test
    public void test_resolved_returnsFalse() {

        NotResolvedPredicate predicate = new NotResolvedPredicate();
        assertFalse(predicate.test(new ArticleBuilder().withIsResolved(true).build()));

        // Multiple fields
        assertFalse(predicate.test(new ArticleBuilder().withName("wallet").withDescription("test description")
                .withFinder("Bob Hoe").withPhone("99999999").withEmail("hi@tt.com").withIsResolved(true).build()));
    }
}
