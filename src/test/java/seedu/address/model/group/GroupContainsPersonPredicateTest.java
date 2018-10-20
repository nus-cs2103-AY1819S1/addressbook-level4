package seedu.address.model.group;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUPTAG_CCA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUPTAG_PROJECT;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.person.GroupContainsPersonPredicate;

public class GroupContainsPersonPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(VALID_GROUPTAG_CCA);
        List<String> secondPredicateKeywordList = Arrays.asList(VALID_GROUPTAG_CCA, VALID_GROUPTAG_PROJECT);

        GroupContainsPersonPredicate firstPredicate = new GroupContainsPersonPredicate(firstPredicateKeywordList);
        GroupContainsPersonPredicate secondPredicate = new GroupContainsPersonPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupContainsPersonPredicate firstPredicateCopy = new GroupContainsPersonPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // differet types -> returns false
        assertFalse(firstPredicate.equals(0));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different object -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupContainsPerson_returnsTrue() {
        // Matching keyword
        GroupContainsPersonPredicate predicate =
            new GroupContainsPersonPredicate(Collections.singletonList(VALID_GROUPTAG_CCA));
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_groupDoesNotContainPerson_returnsFalse() {
        // No keywords
        GroupContainsPersonPredicate predicate =
            new GroupContainsPersonPredicate(Collections.emptyList());
        assertFalse(predicate.test(BOB));

        // Different case keywords (should we assume false)?
        predicate = new GroupContainsPersonPredicate(Collections.singletonList(VALID_GROUPTAG_CCA.toLowerCase()));
        assertFalse(predicate.test(BOB));

        // Non-matching keyword
        predicate = new GroupContainsPersonPredicate(Arrays.asList(VALID_GROUPTAG_PROJECT));
        assertFalse(predicate.test(BOB));
    }
}
