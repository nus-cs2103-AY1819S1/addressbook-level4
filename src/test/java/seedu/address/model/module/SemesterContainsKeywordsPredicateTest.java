package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ModuleBuilder;

public class SemesterContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1");
        List<String> secondPredicateKeywordList = Arrays.asList("1", "2");

        SemesterContainsKeywordsPredicate firstPredicate =
                new SemesterContainsKeywordsPredicate(firstPredicateKeywordList);
        SemesterContainsKeywordsPredicate secondPredicate =
                new SemesterContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SemesterContainsKeywordsPredicate firstPredicateCopy =
                new SemesterContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }



    @Test
    public void test_semesterContainsKeywords_returnsTrue() {
        // One keyword
        SemesterContainsKeywordsPredicate predicate =
                new SemesterContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new ModuleBuilder().withSemester("1").build()));

        // Multiple keywords
        predicate = new SemesterContainsKeywordsPredicate(Arrays.asList("1", "2"));
        assertTrue(predicate.test(new ModuleBuilder().withSemester("1").build()));
    }

    @Test
    public void test_semesterDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SemesterContainsKeywordsPredicate predicate =
                new SemesterContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleBuilder().withSemester("3").build()));

        // Non-matching keyword
        predicate = new SemesterContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new ModuleBuilder().withSemester("1").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new SemesterContainsKeywordsPredicate(Arrays.asList("2", "1"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("cs2103").withModuleTitle("Software Engineering")
                .withAcademicYear("1718").withSemester("3").build()));
    }
}
