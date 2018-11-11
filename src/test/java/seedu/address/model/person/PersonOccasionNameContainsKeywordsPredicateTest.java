package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.occasion.Occasion;
import seedu.address.testutil.OccasionBuilder;
import seedu.address.testutil.PersonBuilder;

public class PersonOccasionNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("project");
        List<String> secondPredicateKeywordList = Arrays.asList("project", "meeting");

        PersonOccasionNameContainsKeywordsPredicate firstPredicate =
                new PersonOccasionNameContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonOccasionNameContainsKeywordsPredicate secondPredicate =
                new PersonOccasionNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonOccasionNameContainsKeywordsPredicate firstPredicateCopy =
                new PersonOccasionNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_occasionNameContainsKeywords_returnsTrue() {
        List<Occasion> occasionList = new ArrayList<>();
        occasionList.add(new OccasionBuilder().withOccasionName("project due").build());
        // One keyword
        PersonOccasionNameContainsKeywordsPredicate predicate =
                new PersonOccasionNameContainsKeywordsPredicate(Collections.singletonList("project"));
        assertTrue(predicate.test(new PersonBuilder().withOccasionList(occasionList).build()));

        // Multiple keywords
        occasionList.add(new OccasionBuilder().withOccasionName("christmas").build());
        predicate = new PersonOccasionNameContainsKeywordsPredicate(Arrays.asList("project", "exam"));
        assertTrue(predicate.test(new PersonBuilder().withOccasionList(occasionList).build()));
    }

    @Test
    public void test_occasionNameDoesNotContainKeywords_returnsFalse() {
        List<Occasion> occasionList = new ArrayList<>();
        occasionList.add(new OccasionBuilder().withOccasionName("tutorial").build());
        // Zero keywords
        PersonOccasionNameContainsKeywordsPredicate predicate =
                new PersonOccasionNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withOccasionList(occasionList).build()));

        // Non-matching keyword
        predicate = new PersonOccasionNameContainsKeywordsPredicate(Arrays.asList("tutorials"));
        assertFalse(predicate.test(new PersonBuilder().withOccasionList(occasionList).build()));
    }
}
