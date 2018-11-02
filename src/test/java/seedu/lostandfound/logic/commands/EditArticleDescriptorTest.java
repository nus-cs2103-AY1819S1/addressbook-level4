package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_RED;

import org.junit.Test;

import seedu.lostandfound.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.lostandfound.testutil.EditArticleDescriptorBuilder;

public class EditArticleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditArticleDescriptor descriptorWithSameValues = new EditArticleDescriptor(DESC_POWERBANK);
        assertTrue(DESC_POWERBANK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_POWERBANK.equals(DESC_POWERBANK));

        // null -> returns false
        assertFalse(DESC_POWERBANK.equals(null));

        // different types -> returns false
        assertFalse(DESC_POWERBANK.equals(5));

        // different values -> returns false
        assertFalse(DESC_POWERBANK.equals(DESC_MOUSE));

        // different name -> returns false
        EditArticleDescriptor editedAmy = new EditArticleDescriptorBuilder(DESC_POWERBANK)
                .withName(VALID_NAME_MOUSE).build();
        assertFalse(DESC_POWERBANK.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_POWERBANK).withPhone(VALID_PHONE_MOUSE).build();
        assertFalse(DESC_POWERBANK.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_POWERBANK).withEmail(VALID_EMAIL_MOUSE).build();
        assertFalse(DESC_POWERBANK.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_POWERBANK).withDescription(VALID_DESCRIPTION_MOUSE).build();
        assertFalse(DESC_POWERBANK.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_POWERBANK).withTags(VALID_TAG_RED).build();
        assertFalse(DESC_POWERBANK.equals(editedAmy));
    }
}
