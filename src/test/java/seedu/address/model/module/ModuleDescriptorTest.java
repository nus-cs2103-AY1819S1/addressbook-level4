package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULECODE_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_SEMESTER_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_BINARY;

import org.junit.Test;

import seedu.address.testutil.ModuleDescriptorBuilder;

public class ModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ModuleDescriptor descriptorWithSameValues = new ModuleDescriptor(DESC_CS2100);
        assertTrue(DESC_CS2100.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2100.equals(DESC_CS2100));

        // null -> returns false
        assertFalse(DESC_CS2100.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2100.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2100.equals(MODULECODE_DESC_ST2131));

        // different module code -> returns false
        ModuleDescriptor editedAmy = new ModuleDescriptorBuilder().withModuleCode(VALID_MODULECODE_ST2131).build();
        assertFalse(DESC_CS2100.equals(editedAmy));

        // different module title -> returns false
        editedAmy = new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_CS2100).build();
        assertFalse(DESC_CS2100.equals(editedAmy));

        // different academic year -> returns false
        editedAmy = new ModuleDescriptorBuilder().withAcademicYear(VALID_ACADEMICYEAR_CS2100).build();
        assertFalse(DESC_CS2100.equals(editedAmy));

        // different semester -> returns false
        editedAmy = new ModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2100).build();
        assertFalse(DESC_CS2100.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new ModuleDescriptorBuilder().withTags(VALID_TAG_BINARY).build();
        assertFalse(DESC_CS2100.equals(editedAmy));
    }
}


