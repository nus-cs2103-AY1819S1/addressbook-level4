package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
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
