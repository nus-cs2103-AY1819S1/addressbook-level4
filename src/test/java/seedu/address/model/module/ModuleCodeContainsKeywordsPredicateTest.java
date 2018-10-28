package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ModuleBuilder;

public class ModuleCodeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("cs2103");
        List<String> secondPredicateKeywordList = Arrays.asList("cs2103", "cs2103t");

        ModuleCodeContainsKeywordsPredicate firstPredicate =
                new ModuleCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        ModuleCodeContainsKeywordsPredicate secondPredicate =
                new ModuleCodeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleCodeContainsKeywordsPredicate firstPredicateCopy =
                new ModuleCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleCodeContainsKeywords_returnsTrue() {
        // One keyword
        ModuleCodeContainsKeywordsPredicate predicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("cs2103"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("cs2103").build()));

        // Multiple keywords
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103", "cs2103t"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("cs2103").build()));

    }

    @Test
    public void test_moduleCodeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleCodeContainsKeywordsPredicate predicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("cs2103").build()));

        // Non-matching keyword
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("cs2103t").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103t", "software", "1718", "1"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("cs2103").withModuleTitle("engineering")
                .withAcademicYear("1718").withSemester("2").build()));
    }
}
