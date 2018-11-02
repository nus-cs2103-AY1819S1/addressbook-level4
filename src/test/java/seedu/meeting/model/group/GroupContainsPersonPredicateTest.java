package seedu.meeting.model.group;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.NUS_BASKETBALL;
import static seedu.meeting.testutil.TypicalGroups.NUS_COMPUTING;
import static seedu.meeting.testutil.TypicalPersons.ALICE;
import static seedu.meeting.testutil.TypicalPersons.BOB;
import static seedu.meeting.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.meeting.model.group.util.GroupContainsPersonPredicate;

public class GroupContainsPersonPredicateTest {

    @Test
    public void equals() {
        List<Group> firstPredicateKeywordList = Collections.singletonList(GROUP_2101);
        List<Group> secondPredicateKeywordList = Arrays.asList(NUS_BASKETBALL, NUS_COMPUTING);

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
        // Person in group
        GroupContainsPersonPredicate predicate =
            new GroupContainsPersonPredicate(Collections.singletonList(NUS_BASKETBALL));
        assertTrue(predicate.test(CARL));
    }

    @Test
    public void test_groupDoesNotContainPerson_returnsFalse() {
        // No group
        GroupContainsPersonPredicate predicate =
            new GroupContainsPersonPredicate(Collections.emptyList());
        assertFalse(predicate.test(BOB));

        // Person not in any group
        predicate = new GroupContainsPersonPredicate(Arrays.asList(NUS_COMPUTING));
        assertFalse(predicate.test(BOB));

        // Person in another group
        assertFalse(predicate.test(ALICE));
    }
}
