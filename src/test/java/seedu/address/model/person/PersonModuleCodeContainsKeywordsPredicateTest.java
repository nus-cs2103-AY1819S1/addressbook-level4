package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.PersonBuilder;

public class PersonModuleCodeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("cs2103");
        List<String> secondPredicateKeywordList = Arrays.asList("cs2103", "cs2103t");

        PersonModuleCodeContainsKeywordsPredicate firstPredicate =
                new PersonModuleCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonModuleCodeContainsKeywordsPredicate secondPredicate =
                new PersonModuleCodeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonModuleCodeContainsKeywordsPredicate firstPredicateCopy =
                new PersonModuleCodeContainsKeywordsPredicate(firstPredicateKeywordList);
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
        List<Module> moduleList = new ArrayList<>();
        moduleList.add(new ModuleBuilder().withModuleCode("cs2103").build());
        // One keyword
        PersonModuleCodeContainsKeywordsPredicate predicate =
                new PersonModuleCodeContainsKeywordsPredicate(Collections.singletonList("cs2103"));
        assertTrue(predicate.test(new PersonBuilder().withModuleList(moduleList).build()));

        // Multiple keywords
        moduleList.add(new ModuleBuilder().withModuleCode("cs2103t").build());
        predicate = new PersonModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103", "cs2103t"));
        assertTrue(predicate.test(new PersonBuilder().withModuleList(moduleList).build()));
    }

    @Test
    public void test_moduleCodeDoesNotContainKeywords_returnsFalse() {
        List<Module> moduleList = new ArrayList<>();
        moduleList.add(new ModuleBuilder().withModuleCode("cs2103").build());
        // Zero keywords
        PersonModuleCodeContainsKeywordsPredicate predicate =
                new PersonModuleCodeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withModuleList(moduleList).build()));

        // Non-matching keyword
        predicate = new PersonModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103t"));
        assertFalse(predicate.test(new PersonBuilder().withModuleList(moduleList).build()));
    }
}
