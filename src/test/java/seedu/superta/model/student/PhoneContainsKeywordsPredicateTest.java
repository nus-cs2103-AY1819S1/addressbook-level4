package seedu.superta.model.student;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class PhoneContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1234567");
        List<String> secondPredicateKeywordList = Arrays.asList("1234567", "245677");

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
            new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
