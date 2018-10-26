package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_FIVE;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_ONE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.OccasionBuilder;

public class OccasionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Occasion occasion = new OccasionBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        occasion.getTags().remove(0);
    }


    @Test
    public void isSameOccasion() {
        // same object -> returns true
        assertTrue(TYPICAL_OCCASION_ONE.isSameOccasion(TYPICAL_OCCASION_ONE));

        // null -> returns false
        assertFalse(TYPICAL_OCCASION_ONE.isSameOccasion(null));

        // different occasionDate and occasionLocation -> returns false
        Occasion editedOccasionOne = new OccasionBuilder(TYPICAL_OCCASION_ONE).withOccasionDate(VALID_OCCASIONDATE_TWO)
                .withOccasionLocation(VALID_OCCASIONLOCATION_TWO).build();
        assertFalse(TYPICAL_OCCASION_ONE.isSameOccasion(editedOccasionOne));

        // different occasionName -> returns false
        editedOccasionOne = new OccasionBuilder(TYPICAL_OCCASION_ONE).withOccasionName(VALID_OCCASIONNAME_TWO).build();
        assertFalse(TYPICAL_OCCASION_ONE.isSameOccasion(editedOccasionOne));

        // same occasionName, same occasionDate, different attributes -> returns true
        editedOccasionOne = new OccasionBuilder(TYPICAL_OCCASION_ONE).withOccasionLocation(VALID_OCCASIONLOCATION_TWO)
                .withTags(VALID_TAG_SLEEP).build();
        assertTrue(TYPICAL_OCCASION_ONE.isSameOccasion(editedOccasionOne));

        // same occasionName, same occasionDate, same occasionLocaton, different attributes -> returns true
        editedOccasionOne = new OccasionBuilder(TYPICAL_OCCASION_ONE).withTags(VALID_TAG_STUDY).build();
        assertTrue(TYPICAL_OCCASION_ONE.isSameOccasion(editedOccasionOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Occasion occasionOneCopy = new OccasionBuilder(TYPICAL_OCCASION_ONE).build();
        assertTrue(TYPICAL_OCCASION_ONE.equals(occasionOneCopy));

        // same object -> returns true
        assertTrue(TYPICAL_OCCASION_ONE.equals(TYPICAL_OCCASION_ONE));

        // null -> returns false
        assertFalse(TYPICAL_OCCASION_ONE.equals(null));

        // different type -> returns false
        assertFalse(TYPICAL_OCCASION_ONE.equals(5));

        // different person -> returns false
        assertFalse(TYPICAL_OCCASION_ONE.equals(TYPICAL_OCCASION_FIVE));

        // different occasionName -> returns false
        Occasion editedOccasionOne =
                new OccasionBuilder(TYPICAL_OCCASION_ONE).withOccasionName(VALID_OCCASIONNAME_TWO).build();
        assertFalse(TYPICAL_OCCASION_ONE.equals(editedOccasionOne));

        // different occasionDate -> returns false
        editedOccasionOne = new OccasionBuilder(TYPICAL_OCCASION_ONE).withOccasionDate(VALID_OCCASIONDATE_TWO).build();
        assertFalse(TYPICAL_OCCASION_ONE.equals(editedOccasionOne));

        // different occasionLocation -> returns false
        editedOccasionOne =
                new OccasionBuilder(TYPICAL_OCCASION_ONE).withOccasionLocation(VALID_OCCASIONLOCATION_TWO).build();
        assertFalse(TYPICAL_OCCASION_ONE.equals(editedOccasionOne));

        // different tags -> returns false
        editedOccasionOne = new OccasionBuilder(TYPICAL_OCCASION_ONE).withTags(VALID_TAG_STUDY).build();
        assertFalse(TYPICAL_OCCASION_ONE.equals(editedOccasionOne));
    }
}
