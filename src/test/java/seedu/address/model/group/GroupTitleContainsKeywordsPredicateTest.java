package seedu.address.model.group;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tag.Tag;

public class GroupTitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("CS2103T");
        List<String> secondPredicateKeywordList = Arrays.asList("CS2103T", "CS2101");

        GroupTitleContainsKeywordsPredicate firstPredicate =
            new GroupTitleContainsKeywordsPredicate(firstPredicateKeywordList);
        GroupTitleContainsKeywordsPredicate secondPredicate =
            new GroupTitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupTitleContainsKeywordsPredicate firstPredicateCopy =
            new GroupTitleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different object -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupContainsKeywords_returnsTrue() {
        // Matching keyword
        GroupTitleContainsKeywordsPredicate predicate =
            new GroupTitleContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new Tag("CS2103T")));

        // Mixed-case keywords
        predicate = new GroupTitleContainsKeywordsPredicate(Collections.singletonList("cS2103t"));
        assertTrue(predicate.test(new Tag("CS2103T")));
    }

    @Test
    public void test_groupDoesNotContainKeywords_returnsFalse() {
        // No keywords
        GroupTitleContainsKeywordsPredicate predicate =
            new GroupTitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Tag("CS2103T")));

        // Non-matching keyword
        predicate = new GroupTitleContainsKeywordsPredicate(Arrays.asList("CS2101"));
        assertFalse(predicate.test(new Tag("CS2103T")));
    }
}
