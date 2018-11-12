package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_ST2131;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_ONE;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_TWO;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModuleDescriptorBuilder;

public class ModuleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Module module = new ModuleBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        module.getTags().remove(0);
    }

    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(TYPICAL_MODULE_ONE.isSameModule(TYPICAL_MODULE_ONE));

        // null -> returns false
        assertFalse(TYPICAL_MODULE_ONE.isSameModule(null));

        // different moduletitle -> returns true
        Module editedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withModuleTitle(VALID_MODULETITLE_ST2131)
                .build();
        assertTrue(TYPICAL_MODULE_ONE.isSameModule(editedModuleOne));

        // different modulecode -> returns false
        editedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withModuleCode(VALID_MODULECODE_CS2100)
                .build();
        assertFalse(TYPICAL_MODULE_ONE.isSameModule(editedModuleOne));

        // different academic year -> returns false
        editedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withAcademicYear(VALID_ACADEMICYEAR_CS2100)
                .build();
        assertFalse(TYPICAL_MODULE_ONE.isSameModule(editedModuleOne));

        // different semester -> returns false
        editedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withSemester("4")
                .build();
        assertFalse(TYPICAL_MODULE_ONE.isSameModule(editedModuleOne));

        // different students list -> return true
        UniquePersonList validStudents = new UniquePersonList(new ArrayList<>());
        validStudents.add(ALICE);
        editedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withStudents(validStudents).build();
        assertTrue(TYPICAL_MODULE_ONE.isSameModule(editedModuleOne));
    }

    @Test
    public void isSameModuleInstantiatedByModuleDescriptor() {
        ModuleDescriptor descriptorWithSameValues = new ModuleDescriptor(DESC_CS2100);
        Module moduleWithSameValues = new Module(descriptorWithSameValues);

        // same object -> returns true
        assertTrue(moduleWithSameValues.isSameModule(moduleWithSameValues));

        // null -> returns false
        assertFalse(moduleWithSameValues.isSameModule(null));

        // different moduletitle -> returns true
        Module editedModuleOne = new ModuleBuilder(moduleWithSameValues).withModuleTitle(VALID_MODULETITLE_ST2131)
                .build();
        assertTrue(moduleWithSameValues.isSameModule(editedModuleOne));

        // different modulecode -> returns false
        editedModuleOne = new ModuleBuilder(moduleWithSameValues).withModuleCode(VALID_MODULECODE_ST2131).build();
        assertFalse(moduleWithSameValues.isSameModule(editedModuleOne));

        // different semester -> returns false
        editedModuleOne = new ModuleBuilder(moduleWithSameValues).withSemester("4")
                .build();
        assertFalse(moduleWithSameValues.isSameModule(editedModuleOne));

        // different students list -> return true
        UniquePersonList validStudents = new UniquePersonList(new ArrayList<>());
        validStudents.add(ALICE);
        editedModuleOne = new ModuleBuilder(moduleWithSameValues).withStudents(validStudents).build();
        assertTrue(moduleWithSameValues.isSameModule(editedModuleOne));
    }

    @Test
    public void isSameModuleAfterEdit() {
        ModuleDescriptor descriptorWithSameValues = new ModuleDescriptor(DESC_CS2100);
        Module moduleToEdit = new Module(descriptorWithSameValues);
        ModuleDescriptor descriptorOfmoduleToEdit = new ModuleDescriptorBuilder(DESC_CS2100)
                .withModuleTitle(VALID_MODULETITLE_ST2131).build();
        Module moduleEdited = moduleToEdit.createEditedModule(moduleToEdit, descriptorOfmoduleToEdit);

        // same object -> returns true
        assertTrue(moduleToEdit.isSameModule(moduleEdited));

        // null -> returns false
        assertFalse(moduleToEdit.isSameModule(null));
    }

    @Test
    public void isSameModuleAfterMakeDeepDuplicate() {
        ModuleDescriptor descriptorWithSameValues = new ModuleDescriptor(DESC_CS2100);
        Module moduleToDeepDuplicate = new Module(descriptorWithSameValues);
        Module moduleDeepDuplicated = moduleToDeepDuplicate.makeDeepDuplicate();

        // same object -> returns true
        assertTrue(moduleToDeepDuplicate.isSameModule(moduleDeepDuplicated));

        // null -> returns false
        assertFalse(moduleToDeepDuplicate.isSameModule(null));
    }


    @Test
    public void isSameModuleAfterMakeShallowDuplicate() {
        ModuleDescriptor descriptorWithSameValues = new ModuleDescriptor(DESC_CS2100);
        Module moduleToShallowDuplicate = new Module(descriptorWithSameValues);
        Module moduleDuplicated = moduleToShallowDuplicate.makeShallowDuplicate();

        // same object -> returns true
        assertTrue(moduleToShallowDuplicate.isSameModule(moduleDuplicated));

        // null -> returns false
        assertFalse(moduleToShallowDuplicate.isSameModule(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module copiedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).build();
        assertTrue(TYPICAL_MODULE_ONE.equals(copiedModuleOne));

        // same object -> returns true
        assertTrue(TYPICAL_MODULE_ONE.equals(TYPICAL_MODULE_ONE));

        // null -> returns false
        assertFalse(TYPICAL_MODULE_ONE.equals(null));

        // different type -> returns false
        assertFalse(TYPICAL_MODULE_ONE.equals(5));

        // different module -> returns false
        assertFalse(TYPICAL_MODULE_ONE.equals(TYPICAL_MODULE_TWO));

        // different module code -> returns false
        copiedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE)
                .withModuleCode(VALID_MODULECODE_CS2100)
                .build();
        assertFalse(TYPICAL_MODULE_ONE.equals(copiedModuleOne));

        // different module title -> returns false
        copiedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE)
                .withModuleTitle(VALID_MODULETITLE_ST2131)
                .build();
        assertFalse(TYPICAL_MODULE_ONE.equals(copiedModuleOne));

        // different academic year -> returns false
        copiedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE)
                .withAcademicYear(VALID_ACADEMICYEAR_CS2100)
                .build();
        assertFalse(TYPICAL_MODULE_ONE.equals(copiedModuleOne));

        // different semester -> returns false
        copiedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withSemester("4").build();
        assertFalse(TYPICAL_MODULE_ONE.equals(copiedModuleOne));

        // different students list -> returns true
        UniquePersonList validStudents = new UniquePersonList(new ArrayList<>());
        validStudents.add(ALICE);
        copiedModuleOne = new ModuleBuilder(TYPICAL_MODULE_ONE).withStudents(validStudents).build();
        assertTrue(TYPICAL_MODULE_ONE.equals(copiedModuleOne));
    }
}
