package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ModuleBuilder;

public class AcademicYearContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("cs2103");
        List<String> secondPredicateKeywordList = Arrays.asList("cs2103", "cs2103t");

        AcademicYearContainsKeywordsPredicate firstPredicate =
                new AcademicYearContainsKeywordsPredicate(firstPredicateKeywordList);
        AcademicYearContainsKeywordsPredicate secondPredicate =
                new AcademicYearContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AcademicYearContainsKeywordsPredicate firstPredicateCopy =
                new AcademicYearContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_academicYearContainsKeywords_returnsTrue() {
        // One keyword
        AcademicYearContainsKeywordsPredicate predicate =
                new AcademicYearContainsKeywordsPredicate(Collections.singletonList("1819"));
        assertTrue(predicate.test(new ModuleBuilder().withAcademicYear("1819").build()));

        // Multiple keywords
        predicate = new AcademicYearContainsKeywordsPredicate(Arrays.asList("0102", "1819"));
        assertTrue(predicate.test(new ModuleBuilder().withAcademicYear("1819").build()));
    }

    @Test
    public void test_academicYearDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AcademicYearContainsKeywordsPredicate predicate =
                new AcademicYearContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleBuilder().withAcademicYear("1819").build()));

        // Non-matching keyword
        predicate = new AcademicYearContainsKeywordsPredicate(Arrays.asList("1718"));
        assertFalse(predicate.test(new ModuleBuilder().withAcademicYear("1819").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new AcademicYearContainsKeywordsPredicate(Arrays.asList("1617", "0203", "1718", "0910"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("cs2103").withModuleTitle("engineering")
                .withAcademicYear("1819").withSemester("2").build()));
    }
}
