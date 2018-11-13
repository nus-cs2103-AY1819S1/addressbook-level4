package seedu.address.model.wish;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.WishBuilder;

public class WishContainsKeywordPredicateTest {
    private Wish testWish;

    @Before
    public void createTestWish() {
        testWish = new WishBuilder().withName("iPhone Xr")
                .withTags("faMiLy", "iMporTant", "rAre")
                .withRemark("WRap in piNk").build();
    }

    @Test
    public void test_wishContainsAllKeywords_returnsTrue() {
        // Multiple matching keywords for each attribute
        List<String> nameKeywords = Arrays.asList("iph", "x");
        List<String> tagKeywords = Arrays.asList("fam", "impo");
        List<String> remarkKeywords = Arrays.asList("pink", "wrap");
        WishContainsKeywordsPredicate predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertTrue(predicate.test(testWish));

        // Single matching keyword for all attribute
        nameKeywords = Arrays.asList("iph");
        tagKeywords = Arrays.asList("fam");
        remarkKeywords = Arrays.asList("pink");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertTrue(predicate.test(testWish));

        // Single matching keyword for some attributes
        nameKeywords = Arrays.asList("iph", "x");
        tagKeywords = Arrays.asList("fam");
        remarkKeywords = Arrays.asList("pink", "wrap");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertTrue(predicate.test(testWish));

        // No keywords for some attributes
        nameKeywords = Arrays.asList();
        tagKeywords = Arrays.asList("fam", "impo");
        remarkKeywords = Arrays.asList();
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertTrue(predicate.test(testWish));

        // No keywords for all attributes
        nameKeywords = Arrays.asList();
        tagKeywords = Arrays.asList();
        remarkKeywords = Arrays.asList();
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertTrue(predicate.test(testWish));

    }
    @Test
    public void test_wishContainsAnyKeywords_returnsTrue() {
        // Multiple matching keywords for each attribute with some non matching keywords
        List<String> nameKeywords = Arrays.asList("samsung", "iph", "x");
        List<String> tagKeywords = Arrays.asList("fam", "cheap", "impo");
        List<String> remarkKeywords = Arrays.asList("pink", "wrap", "yellow");
        WishContainsKeywordsPredicate predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, false);

        assertTrue(predicate.test(testWish));

        // Single matching keywords for each attribute with some non matching keywords
        nameKeywords = Arrays.asList("samsung", "iph");
        tagKeywords = Arrays.asList("fam", "cheap");
        remarkKeywords = Arrays.asList("wrap", "yellow");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, false);

        // No keywords for some attributes with non some non matching keywords for others
        nameKeywords = Arrays.asList();
        tagKeywords = Arrays.asList("fam", "cheap", "rare", "precious");
        remarkKeywords = Arrays.asList("wrap", "yellow");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, false);

        // No keywords for all attributes
        nameKeywords = Arrays.asList();
        tagKeywords = Arrays.asList();
        remarkKeywords = Arrays.asList();
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, false);

    }

    @Test
    public void test_wishDoesNotContainAllKeywords_returnsFalse() {
        // Multiple matching keywords for each attribute with some non matching keywords
        List<String> nameKeywords = Arrays.asList("iph", "x", "samsung");
        List<String> tagKeywords = Arrays.asList("fam", "precious", "impo");
        List<String> remarkKeywords = Arrays.asList("banana", "pink", "wrap");
        WishContainsKeywordsPredicate predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));

        // Single matching/non matching keyword for all attribute
        nameKeywords = Arrays.asList("motorola");
        tagKeywords = Arrays.asList("fam");
        remarkKeywords = Arrays.asList("yellow");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));

        // Single matching/non matching keyword for some attributes
        nameKeywords = Arrays.asList("iph", "x");
        tagKeywords = Arrays.asList("fam");
        remarkKeywords = Arrays.asList("fame");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));

        // No keywords for some attributes and some non matching keywords for some attributes
        nameKeywords = Arrays.asList();
        tagKeywords = Arrays.asList("fam", "impo", "impi");
        remarkKeywords = Arrays.asList();
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));
    }

    @Test
    public void test_wishDoesNotContainAnyKeywords_returnsFalse() {
        // Multiple non matching attributes for all wishes
        List<String> nameKeywords = Arrays.asList("iphob", "xs", "samsung");
        List<String> tagKeywords = Arrays.asList("famer", "precious", "impob");
        List<String> remarkKeywords = Arrays.asList("banana", "pinks", "wraps");
        WishContainsKeywordsPredicate predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));

        // Single non matching keyword for all attribute
        nameKeywords = Arrays.asList("motorola");
        tagKeywords = Arrays.asList("famq");
        remarkKeywords = Arrays.asList("yellow");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));

        // Multiple non matching keyword for some attributes
        nameKeywords = Arrays.asList("iph", "x");
        tagKeywords = Arrays.asList("rare");
        remarkKeywords = Arrays.asList("fame", "water");
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));

        // No keywords for some attributes and some non matching keywords for some attributes
        nameKeywords = Arrays.asList();
        tagKeywords = Arrays.asList("fam", "impo", "impi");
        remarkKeywords = Arrays.asList();
        predicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        assertFalse(predicate.test(testWish));
    }
}
