package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EARTH_FALT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GIT;

import org.junit.Test;

import seedu.address.logic.commands.EditCCommand.EditCardDescriptor;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class EditCardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCardDescriptor descriptorWithSameValues = new EditCardDescriptor(DESC_EARTH_FALT);
        assertTrue(DESC_EARTH_FALT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EARTH_FALT.equals(DESC_EARTH_FALT));

        // null -> returns false
        assertFalse(DESC_EARTH_FALT.equals(null));

        // different types -> returns false
        assertFalse(DESC_EARTH_FALT.equals(5));

        // different values -> returns false
        assertFalse(DESC_EARTH_FALT.equals(DESC_GIT_COMMIT));

        // different question -> returns false
        EditCardDescriptor editedEarthFlatQ = new EditCardDescriptorBuilder(DESC_EARTH_FALT)
                .withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        assertFalse(DESC_EARTH_FALT.equals(editedEarthFlatQ));

        // different answer -> returns false
        editedEarthFlatQ = new EditCardDescriptorBuilder(DESC_EARTH_FALT)
                .withAnswer(VALID_ANSWER_GIT_COMMIT).build();
        assertFalse(DESC_EARTH_FALT.equals(editedEarthFlatQ));

        // different tags -> returns false
        editedEarthFlatQ = new EditCardDescriptorBuilder(DESC_EARTH_FALT)
                .withTags(VALID_TAG_GIT).build();
        assertFalse(DESC_EARTH_FALT.equals(editedEarthFlatQ));
    }
}
