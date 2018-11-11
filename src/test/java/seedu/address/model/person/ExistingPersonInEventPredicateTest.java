package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ExistingPersonInEventPredicateTest {

    @Test
    public void equals() {
        Set<Person> firstPredicatePersonSet = Collections.singleton(ALICE);
        Set<Person> secondPredicatePersonSet = new HashSet<>(Arrays.asList(ALICE, BOB));

        ExistingPersonInEventPredicate firstPredicate = new ExistingPersonInEventPredicate(firstPredicatePersonSet);
        ExistingPersonInEventPredicate secondPredicate = new ExistingPersonInEventPredicate(secondPredicatePersonSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExistingPersonInEventPredicate firstPredicateCopy = new ExistingPersonInEventPredicate(firstPredicatePersonSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personInSet_returnsTrue() {
        // One person
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(Collections.singleton(ALICE));
        assertTrue(predicate.test(ALICE));

        // Multiple persons
        predicate = new ExistingPersonInEventPredicate(new HashSet<>(Arrays.asList(ALICE, BOB)));
        assertTrue(predicate.test(ALICE));

        // Same person but not exactly equals
        predicate = new ExistingPersonInEventPredicate(Collections.singleton(ALICE));
        assertTrue(predicate.test(new PersonBuilder(ALICE).withEmail("newemail@example.com").build()));
    }

    @Test
    public void test_personNotInSet_returnsFalse() {
        // Zero persons
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(Collections.emptySet());
        assertFalse(predicate.test(ALICE));

        // Non-matching person
        predicate = new ExistingPersonInEventPredicate(Collections.singleton(ALICE));
        assertFalse(predicate.test(BOB));

        // Person with same phone, email and address, but different name (!isSamePerson)
        predicate = new ExistingPersonInEventPredicate(Collections.singleton(ALICE));
        assertFalse(predicate.test(new PersonBuilder(ALICE).withName("Alice").build()));
    }
}
