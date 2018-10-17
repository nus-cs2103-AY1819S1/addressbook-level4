package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.ItemBuilder;

public class SkuMatchPredicateTest {

    @Test
    public void equals() {
        String firstpredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        SkuMatchPredicate firstPredicate = new SkuMatchPredicate(firstpredicateKeyword);
        SkuMatchPredicate secondPredicate = new SkuMatchPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SkuMatchPredicate predicateCopy = new SkuMatchPredicate(firstpredicateKeyword);
        assertTrue(firstPredicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_skuMatchPredicate_returnsTrue() {
        // Match integer SKU
        SkuMatchPredicate predicate = new SkuMatchPredicate("0123");
        assertTrue(predicate.test(new ItemBuilder().withSku("0123").build()));

        // Match letter SKU
        predicate = new SkuMatchPredicate("ABCD");
        assertTrue(predicate.test(new ItemBuilder().withSku("ABCD").build()));

        // Match alphanumeric SKU
        predicate = new SkuMatchPredicate("A123");
        assertTrue(predicate.test(new ItemBuilder().withSku("A123").build()));

        // Mixed-case SKU
        predicate = new SkuMatchPredicate("aBc123");
        assertTrue(predicate.test(new ItemBuilder().withSku("ABC123").build()));
    }

    @Test
    public void test_skuDoesNotMatchPredicate_returnsFalse() {
        // Empty search
        SkuMatchPredicate predicate = new SkuMatchPredicate("");
        assertFalse(predicate.test(new ItemBuilder().withSku("0123").build()));

        // Non-matching search
        predicate = new SkuMatchPredicate("ABCD");
        assertFalse(predicate.test(new ItemBuilder().withSku("0123").build()));
    }
}
