package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GIT;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditCardDescriptor;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class EditCardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCardDescriptor descriptorWithSameValues = new EditCardDescriptor(DESC_EARTH_FLAT);
        assertTrue(DESC_EARTH_FLAT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EARTH_FLAT.equals(DESC_EARTH_FLAT));

        // null -> returns false
        assertFalse(DESC_EARTH_FLAT.equals(null));

        // different types -> returns false
        assertFalse(DESC_EARTH_FLAT.equals(5));

        // different values -> returns false
        assertFalse(DESC_EARTH_FLAT.equals(DESC_GIT_COMMIT));

        // different question -> returns false
        EditCardDescriptor editedEarthFlatQ = new EditCardDescriptorBuilder(DESC_EARTH_FLAT)
                .withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        assertFalse(DESC_EARTH_FLAT.equals(editedEarthFlatQ));

        // different answer -> returns false
        editedEarthFlatQ = new EditCardDescriptorBuilder(DESC_EARTH_FLAT)
                .withAnswer(VALID_ANSWER_GIT_COMMIT).build();
        assertFalse(DESC_EARTH_FLAT.equals(editedEarthFlatQ));

        // different tags -> returns false
        editedEarthFlatQ = new EditCardDescriptorBuilder(DESC_EARTH_FLAT)
                .withTags(VALID_TAG_GIT).build();
        assertFalse(DESC_EARTH_FLAT.equals(editedEarthFlatQ));
    }
}
