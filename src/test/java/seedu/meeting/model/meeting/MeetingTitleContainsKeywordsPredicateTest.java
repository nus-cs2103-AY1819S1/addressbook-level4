package seedu.meeting.model.meeting;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.meeting.util.MeetingTitleContainsKeywordsPredicate;
import seedu.meeting.testutil.MeetingBuilder;

public class MeetingTitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeywordList = Arrays.asList("first", "second", "third");

        MeetingTitleContainsKeywordsPredicate firstPredicate = new MeetingTitleContainsKeywordsPredicate(
            firstPredicateKeywordList, secondPredicateKeywordList, thirdPredicateKeywordList);
        MeetingTitleContainsKeywordsPredicate secondPredicate = new MeetingTitleContainsKeywordsPredicate(
            thirdPredicateKeywordList, secondPredicateKeywordList, firstPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MeetingTitleContainsKeywordsPredicate firstPredicateCopy = new MeetingTitleContainsKeywordsPredicate(
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
    public void test_nameContainsAllKeywords_returnsTrue() throws ParseException {
        // One keyword
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.singletonList("Daily"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Multiple keywords
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Arrays.asList("Daily", "Weekly"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Mixed-case keywords
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Arrays.asList("daILy", "wEEklY"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));
    }

    @Test
    public void test_nameContainsAllKeywords_returnsFalse() throws ParseException {
        // Only one matching keyword
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Arrays.asList("Weekly", "Monthly"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Monthly").build()));

        // Non-matching keyword
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Arrays.asList("Monthly"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Arrays.asList("Conference", "room", "1", "@"),
            Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily").withDescription("Conference")
            .withLocation("A room").withTime("11-11-2019@11:11").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsTrue() throws ParseException {
        // One keyword
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("Daily"), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Multiple keywords
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("Daily", "Weekly"), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Mixed-case keywords
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("dAiLy", "weEkLy"), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Only one matching keyword
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList("Weekly", "Monthly"), Collections.emptyList());
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Monthly").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsFalse() throws ParseException {
        // Non-matching keyword
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Arrays.asList("Monthly"), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(),
            Arrays.asList("Conference", "room", "1", "@"),
            Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily").withDescription("Conference")
            .withLocation("A room").withTime("11-11-2019@11:11").build()));
    }

    @Test
    public void test_nameContainsNoneKeywords_returnsFalse() throws ParseException {
        // One keyword
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Daily"));
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Multiple keywords
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("Daily", "Weekly"));
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Mixed-case keywords
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("dAiLy", "weeklY"));
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Only one matching keyword
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Arrays.asList("Weekly", "Monthly"));
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily Monthly").build()));
    }

    @Test
    public void test_nameContainsNoneKeywords_returnsTrue() throws ParseException {
        // Non-matching keyword
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.singletonList("Monthly"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily Weekly").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(),
            Arrays.asList("Conference", "room", "1", "@"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Daily").withDescription("Conference")
            .withLocation("A room").withTime("11-11-2019@11:11").build()));
    }

    @Test
    public void test_nameContainsZeroKeywords() throws ParseException {
        // Zero keywords
        MeetingTitleContainsKeywordsPredicate predicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Daily").build()));
    }
}
