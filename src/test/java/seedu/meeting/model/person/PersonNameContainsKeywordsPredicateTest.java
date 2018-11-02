package seedu.meeting.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.meeting.model.person.util.PersonNameContainsKeywordsPredicate;
import seedu.meeting.testutil.PersonBuilder;

public class PersonNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeywordList = Arrays.asList("first", "second", "third");

        PersonNameContainsKeywordsPredicate firstPredicate = new PersonNameContainsKeywordsPredicate(
                firstPredicateKeywordList, secondPredicateKeywordList, thirdPredicateKeywordList);
        PersonNameContainsKeywordsPredicate secondPredicate = new PersonNameContainsKeywordsPredicate(
                thirdPredicateKeywordList, secondPredicateKeywordList, firstPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonNameContainsKeywordsPredicate firstPredicateCopy = new PersonNameContainsKeywordsPredicate(
            firstPredicateKeywordList, secondPredicateKeywordList, thirdPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different person in any list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsAllKeywords_returnsTrue() {
        // One keyword
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
                Collections.singletonList("Alice"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonNameContainsKeywordsPredicate(
            Arrays.asList("Alice", "Bob"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new PersonNameContainsKeywordsPredicate(
            Arrays.asList("aLIce", "bOB"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameContainsAllKeywords_returnsFalse() {
        // Only one matching keyword
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
            Arrays.asList("Bob", "Carol"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Non-matching keyword
        predicate = new PersonNameContainsKeywordsPredicate(
            Arrays.asList("Carol"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonNameContainsKeywordsPredicate(
            Arrays.asList("12345", "alice@email.com", "Main", "Street"),
            Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsTrue() {
        // One keyword
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("Alice"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("Alice", "Bob"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("aLIce", "bOB"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("Bob", "Carol"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsFalse() {
        // Non-matching keyword
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
            Arrays.asList("Carol"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(),
            Arrays.asList("12345", "alice@email.com", "Main", "Street"),
            Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameContainsNoneKeywords_returnsFalse() {
        // One keyword
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("Alice", "Bob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("aLIce", "bOB"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_nameContainsNoneKeywords_returnsTrue() {
        // Non-matching keyword
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(),
            Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameContainsZeroKeywords() {
        // Zero keywords
        PersonNameContainsKeywordsPredicate predicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
