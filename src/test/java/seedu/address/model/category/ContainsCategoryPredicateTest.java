package seedu.address.model.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.util.EntryBuilder;

public class ContainsCategoryPredicateTest {

    @Test
    public void equals() {
        ContainsCategoryPredicate workPredicate = new ContainsCategoryPredicate("work");
        ContainsCategoryPredicate nusPredicate = new ContainsCategoryPredicate("education");

        // same object -> returns true
        assertTrue(workPredicate.equals(workPredicate));

        // same values -> returns true
        ContainsCategoryPredicate workPredicateCopy = new ContainsCategoryPredicate("work");
        assertTrue(workPredicate.equals(workPredicateCopy));

        // different types -> returns false
        assertFalse(workPredicate.equals(1));

        // null -> returns false
        assertFalse(workPredicate.equals(null));

        // different person -> returns false
        assertFalse(workPredicate.equals(nusPredicate));
    }

    @Test
    public void test_containsCategory_returnsTrue() {
        // Exact match
        ContainsCategoryPredicate predicate = new ContainsCategoryPredicate("work");
        assertTrue(predicate.test(new EntryBuilder().withCategory("work").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Mixed-case keywords
        ContainsCategoryPredicate predicate = new ContainsCategoryPredicate("wOrK");
        assertFalse(predicate.test(new EntryBuilder().withCategory("work").build()));

        // Non-matching keyword
        predicate = new ContainsCategoryPredicate("work");
        assertFalse(predicate.test(new EntryBuilder().withCategory("education").build()));

        // Keywords match tags, but does not match name
        predicate = new ContainsCategoryPredicate("java");
        assertFalse(predicate.test(new EntryBuilder().withCategory("work").withTags("java").build()));
    }
}
