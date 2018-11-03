package seedu.meeting.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.meeting.model.group.util.GroupTitleContainsKeywordsPredicate;
import seedu.meeting.testutil.GroupBuilder;

public class GroupTitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeywordList = Arrays.asList("first", "second", "third");

        GroupTitleContainsKeywordsPredicate firstPredicate = new GroupTitleContainsKeywordsPredicate(
            firstPredicateKeywordList, secondPredicateKeywordList, thirdPredicateKeywordList);
        GroupTitleContainsKeywordsPredicate secondPredicate = new GroupTitleContainsKeywordsPredicate(
            thirdPredicateKeywordList, secondPredicateKeywordList, firstPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupTitleContainsKeywordsPredicate firstPredicateCopy = new GroupTitleContainsKeywordsPredicate(
            firstPredicateKeywordList, secondPredicateKeywordList, thirdPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person in any list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_titleContainsAllKeywords_returnsTrue() {
        // One keyword
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.singletonList("Alpha"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Multiple keywords
        predicate = new GroupTitleContainsKeywordsPredicate(
            Arrays.asList("Alpha", "Beta"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Mixed-case keywords
        predicate = new GroupTitleContainsKeywordsPredicate(
            Arrays.asList("aLpHa", "bEtA"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));
    }

    @Test
    public void test_nameContainsAllKeywords_returnsFalse() {
        // Only one matching keyword
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Arrays.asList("Beta", "Club"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Club").build()));

        // Non-matching keyword
        predicate = new GroupTitleContainsKeywordsPredicate(
            Arrays.asList("Club"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Keywords match description, but does not match title
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.singletonList("Alphas"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha").withDescription("The Alphas").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsTrue() {
        // One keyword
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("Alpha"), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Multiple keywords
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("Alpha", "Beta"), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Mixed-case keywords
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("aLpHa", "beTA"), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Only one matching keyword
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("Beta", "Club"), Collections.emptyList());
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Club").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsFalse() {
        // Non-matching keyword
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Arrays.asList("Club"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("Alphas"), Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha").withDescription("The Alphas").build()));
    }

    @Test
    public void test_nameContainsNoneKeywords_returnsFalse() {
        // One keyword
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Alpha"));
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Multiple keywords
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("Alpha", "Beta"));
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Mixed-case keywords
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("alPHA", "BETa"));
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Only one matching keyword
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("Beta", "Club"));
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha Club").build()));
    }

    @Test
    public void test_nameContainsNoneKeywords_returnsTrue() {
        // Non-matching keyword
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Club"));
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha Beta").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Alphas"));
        assertTrue(predicate.test(new GroupBuilder().withTitle("Alpha").withDescription("The Alphas").build()));
    }

    @Test
    public void test_nameContainsZeroKeywords() {
        // Zero keywords
        GroupTitleContainsKeywordsPredicate predicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withTitle("Alpha").build()));
    }
}
