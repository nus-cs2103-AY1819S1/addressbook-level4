package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_FIVE;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_ONE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.OccasionBuilder;
import seedu.address.testutil.OccasionDescriptorBuilder;

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
    public void isSameOccasionInstantiatedByOccasionDescriptor() {
        OccasionDescriptor descriptorWithSameValues = new OccasionDescriptor(DESC_ONE);
        Occasion occasionWithSameValues = new Occasion(descriptorWithSameValues);

        // same object -> returns true
        assertTrue(occasionWithSameValues.isSameOccasion(occasionWithSameValues));

        // null -> returns false
        assertFalse(occasionWithSameValues.isSameOccasion(null));

        // different occasion name -> returns true
        Occasion editedOccasionOne = new OccasionBuilder(occasionWithSameValues).withOccasionName(VALID_OCCASIONNAME_ONE)
                .build();
        assertTrue(occasionWithSameValues.isSameOccasion(editedOccasionOne));

        // different occasion date -> returns false
        editedOccasionOne = new OccasionBuilder(occasionWithSameValues).withOccasionDate(VALID_OCCASIONDATE_TWO).build();
        assertFalse(occasionWithSameValues.isSameOccasion(editedOccasionOne));

        // different attendence list -> return true
        UniquePersonList validStudents = new UniquePersonList(new ArrayList<>());
        validStudents.add(ALICE);
        editedOccasionOne = new OccasionBuilder(occasionWithSameValues).withAttendanceList(validStudents).build();
        assertTrue(occasionWithSameValues.isSameOccasion(editedOccasionOne));
    }

    @Test
    public void isSameOccasionCreatedByNameDateTag() {
        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag(VALID_TAG_STUDY));
        Occasion occasionWithSameValues = new Occasion(new OccasionName(VALID_OCCASIONNAME_TWO),
                new OccasionDate(VALID_OCCASIONDATE_TWO), tags);

        assertTrue(occasionWithSameValues.isSameOccasion(occasionWithSameValues));
    }

    @Test
    public void isSameOccasionAfterEdit() {
        OccasionDescriptor descriptorWithSameValues = new OccasionDescriptor(DESC_ONE);
        Occasion occasionToEdit = new Occasion(descriptorWithSameValues);
        OccasionDescriptor descriptorOfOccasionToEdit = new OccasionDescriptorBuilder(DESC_ONE)
                .withOccasionName(VALID_OCCASIONNAME_ONE).build();
        Occasion occasionEdited = occasionToEdit.createEditedOccasion(occasionToEdit, descriptorOfOccasionToEdit);

        // same object -> returns true
        assertTrue(occasionToEdit.isSameOccasion(occasionEdited));

        // null -> returns false
        assertFalse(occasionToEdit.isSameOccasion(null));
    }

    @Test
    public void isSameOccasionAfterMakeDeepDuplicate() {
        OccasionDescriptor descriptorWithSameValues = new OccasionDescriptor(DESC_ONE);
        Occasion occasionToDeepDuplicate = new Occasion(descriptorWithSameValues);
        Occasion occasionDeepDuplicated = occasionToDeepDuplicate.makeDeepDuplicate();

        // same object -> returns true
        assertTrue(occasionToDeepDuplicate.isSameOccasion(occasionDeepDuplicated));

        // null -> returns false
        assertFalse(occasionToDeepDuplicate.isSameOccasion(null));
    }


    @Test
    public void isSameOccasionAfterMakeShallowDuplicate() {
        OccasionDescriptor descriptorWithSameValues = new OccasionDescriptor(DESC_ONE);
        Occasion occasionToShallowDuplicate = new Occasion(descriptorWithSameValues);
        Occasion occasionDuplicated = occasionToShallowDuplicate.makeShallowDuplicate();

        // same object -> returns true
        assertTrue(occasionToShallowDuplicate.isSameOccasion(occasionDuplicated));

        // null -> returns false
        assertFalse(occasionToShallowDuplicate.isSameOccasion(null));
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
