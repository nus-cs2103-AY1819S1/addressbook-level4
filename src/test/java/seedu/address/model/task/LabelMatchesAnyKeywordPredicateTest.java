package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.LabelsBuilder.createLabelsFromKeywords;

import java.util.Set;

import org.junit.Test;

import seedu.address.model.tag.Label;
import seedu.address.testutil.TaskBuilder;

public class LabelMatchesAnyKeywordPredicateTest {

    @Test
    public void equals() {
        Set<Label> firstDummyKeywords = createLabelsFromKeywords("habibi");
        Set<Label> secondDummyKeywords = createLabelsFromKeywords("hablahabla");
        Set<Label> thirdDummyKeywords = createLabelsFromKeywords("hablahabla", "habibi");

        LabelMatchesAnyKeywordPredicate firstPredicate = new LabelMatchesAnyKeywordPredicate(firstDummyKeywords);
        LabelMatchesAnyKeywordPredicate secondPredicate = new LabelMatchesAnyKeywordPredicate(secondDummyKeywords);
        LabelMatchesAnyKeywordPredicate thirdPredicate = new LabelMatchesAnyKeywordPredicate(thirdDummyKeywords);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LabelMatchesAnyKeywordPredicate firstPredicateCopy = new LabelMatchesAnyKeywordPredicate(firstDummyKeywords);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same keywords, case insensitive -> returns true
        Set<Label> firstDummyKeywordCaseInsensitive = createLabelsFromKeywords("hAbiBI");
        LabelMatchesAnyKeywordPredicate firstPredicateCaseInsensitive =
            new LabelMatchesAnyKeywordPredicate(firstDummyKeywordCaseInsensitive);
        assertTrue(firstPredicate.equals(firstPredicateCaseInsensitive));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keylabels -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different number of keylabels -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_labelMatchesKeyword_returnsTrue() {
        // One Label
        LabelMatchesAnyKeywordPredicate predicate =
            new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("Urgent"));
        assertTrue(predicate.test(new TaskBuilder().withLabels("Urgent").build()));

        // Multiple Labels
        assertTrue(predicate.test(new TaskBuilder().withLabels("Urgent", "Critical", "VeryReallyUrgent").build()));

        // Case-Insensitive Variations
        assertTrue(predicate.test(new TaskBuilder().withLabels("UrGeNt").build()));
    }

    @Test
    public void test_labelMatchesAnyKeywords_returnsTrue() {
        // Multiple Labels
        LabelMatchesAnyKeywordPredicate predicate =
            new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("Urgent", "NotUrgent", "Duplicate"));
        assertTrue(predicate.test(new TaskBuilder().withLabels("Urgent").build()));

        // Multiple Labels
        assertTrue(predicate.test(new TaskBuilder().withLabels("Urgent", "Critical", "VeryReallyUrgent").build()));

        // Case-Insensitive Variations
        assertTrue(predicate.test(new TaskBuilder().withLabels("UrGeNt").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        LabelMatchesAnyKeywordPredicate predicate =
            new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("Severe"));
        assertFalse(predicate.test(new TaskBuilder().withLabels("Urgent").build()));

        // Keywords match priority value, description and name, but does not match label
        // DueDate is not tested as Label's format cannot match DueDate's
        predicate = new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("2"));
        assertFalse(predicate.test(new TaskBuilder().withPriorityValue("2").build()));

        predicate = new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("Milestone1"));
        assertFalse(predicate.test(new TaskBuilder().withName("Milestone1").build()));

        predicate = new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("Donoteat"));
        assertFalse(predicate.test(new TaskBuilder().withDescription("Donoteat").build()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_emptyString_throwsException() {
        // Empty string as a keyword
        LabelMatchesAnyKeywordPredicate predicate = new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords(""));
    }



}
