package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ModuleBuilder;

public class ModuleTitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("cs2103");
        List<String> secondPredicateKeywordList = Arrays.asList("cs2103", "cs2103t");

        ModuleTitleContainsKeywordsPredicate firstPredicate =
                new ModuleTitleContainsKeywordsPredicate(firstPredicateKeywordList);
        ModuleTitleContainsKeywordsPredicate secondPredicate =
                new ModuleTitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleTitleContainsKeywordsPredicate firstPredicateCopy =
                new ModuleTitleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }



    @Test
    public void test_moduleTitleContainsKeywords_returnsTrue() {
        // One keyword
        ModuleTitleContainsKeywordsPredicate predicate =
                new ModuleTitleContainsKeywordsPredicate(Collections.singletonList("engineering"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleTitle("Software Engineering").build()));

        // Multiple keywords
        predicate = new ModuleTitleContainsKeywordsPredicate(Arrays.asList("software", "engineering"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleTitle("software engineering").build()));

        // Mixed-case keywords
        predicate = new ModuleTitleContainsKeywordsPredicate(Arrays.asList("SofTwARe", "ENgineERING"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleTitle("Software Engineering").build()));
    }

    @Test
    public void test_moduleTitleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleTitleContainsKeywordsPredicate predicate =
                new ModuleTitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleBuilder().withModuleTitle("Software Engineering").build()));

        // Non-matching keyword
        predicate = new ModuleTitleContainsKeywordsPredicate(Arrays.asList("networks"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleTitle("Software Engineering").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ModuleTitleContainsKeywordsPredicate(Arrays.asList("cs2103t", "gg", "1718", "1"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("cs2103").withModuleTitle("Software Engineering")
                .withAcademicYear("1718").withSemester("1").build()));
    }
}
