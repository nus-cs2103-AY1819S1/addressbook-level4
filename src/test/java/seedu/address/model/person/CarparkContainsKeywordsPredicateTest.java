package seedu.address.model.person;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.Ignore;
//import org.junit.Test;
//
//import seedu.address.model.carpark.CarparkContainsKeywordsPredicate;
//import seedu.address.testutil.PersonBuilder;

public class CarparkContainsKeywordsPredicateTest {

//    @Test
//    @Ignore
//    public void equals() {
//        List<String> firstPredicateKeywordList = Collections.singletonList("first");
//        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
//
//        CarparkContainsKeywordsPredicate firstPredicate = new CarparkContainsKeywordsPredicate(
//              firstPredicateKeywordList);
//        CarparkContainsKeywordsPredicate secondPredicate = new CarparkContainsKeywordsPredicate(
//              secondPredicateKeywordList);
//
//        // same object -> returns true
//        assertTrue(firstPredicate.equals(firstPredicate));
//
//        // same values -> returns true
//        CarparkContainsKeywordsPredicate firstPredicateCopy = new CarparkContainsKeywordsPredicate(
//              firstPredicateKeywordList);
//        assertTrue(firstPredicate.equals(firstPredicateCopy));
//
//        // different types -> returns false
//        assertFalse(firstPredicate.equals(1));
//
//        // null -> returns false
//        assertFalse(firstPredicate.equals(null));
//
//        // different carpark -> returns false
//        assertFalse(firstPredicate.equals(secondPredicate));
//    }

//    @Test
//    @Ignore
//    public void test_nameContainsKeywords_returnsTrue() {
//        // One keyword
//        CarparkContainsKeywordsPredicate predicate = new CarparkContainsKeywordsPredicate(
//              Collections.singletonList("Alice"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//
//        // Multiple keywords
//        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//
//        // Only one matching keyword
//        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
//
//        // Mixed-case keywords
//        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//    }

//    @Test
//    @Ignore
//    public void test_nameDoesNotContainKeywords_returnsFalse() {
//        // Zero keywords
//        CarparkContainsKeywordsPredicate predicate = new CarparkContainsKeywordsPredicate(Collections.emptyList());
//        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
//
//        // Non-matching keyword
//        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("Carol"));
//        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//
//        // Keywords match phone, email and address, but does not match name
//        predicate = new CarparkContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
//        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
//                .withEmail("alice@email.com").withAddress("Main Street").build()));
//    }
}
