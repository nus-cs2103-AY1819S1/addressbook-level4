package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import ssp.scheduleplanner.testutil.TaskBuilder;

public class TagsContainsAllKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsContainsAllKeywordsPredicate firstPredicate =
                new TagsContainsAllKeywordsPredicate(firstPredicateKeywordList);
        TagsContainsAllKeywordsPredicate secondPredicate =
                new TagsContainsAllKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainsAllKeywordsPredicate firstPredicateCopy =
                new TagsContainsAllKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainsAllKeywords_returnsTrue() {
        // One keyword
        TagsContainsAllKeywordsPredicate predicate = new TagsContainsAllKeywordsPredicate(Collections.singletonList(
                "CS2101"));
        assertTrue(predicate.test(new TaskBuilder().withName("Lecture").withTags("CS2101", "CS2103").build()));

        // Multiple keywords
        predicate = new TagsContainsAllKeywordsPredicate(Arrays.asList("CS2101", "CS2103"));
        assertTrue(predicate.test(new TaskBuilder().withName("Lecture").withTags("CS2101", "CS2103").build()));

        // Mixed-case keywords
        predicate = new TagsContainsAllKeywordsPredicate(Arrays.asList("Cs2101", "cS2103"));
        assertTrue(predicate.test(new TaskBuilder().withName("Lecture").withTags("CS2101", "CS2103").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagsContainsAllKeywordsPredicate predicate = new TagsContainsAllKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withName("Lecture").withTags("CS2100", "CS2103").build()));

        // Non-matching keyword
        predicate = new TagsContainsAllKeywordsPredicate(Arrays.asList("CS2101"));
        assertFalse(predicate.test(new TaskBuilder().withName("Lecture").withTags("CS2103").build()));

        // One non-matching keyword
        predicate = new TagsContainsAllKeywordsPredicate(Arrays.asList("CS2103", "Tutorial"));
        assertFalse(predicate.test(new TaskBuilder().withName("CS2103 Tutorial").withTags("CS2103", "Lecture").build()));
    }
}
