package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_ST2131;
import static seedu.address.testutil.TypicalModules.CS1101S;
import static seedu.address.testutil.TypicalModules.CS1231;
import static seedu.address.testutil.TypicalPersons.ALICE;

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
        assertTrue(CS1101S.isSameModule(CS1101S));

        // null -> returns false
        assertFalse(CS1101S.isSameModule(null));

        // different moduletitle -> returns true
        Module editedCS1101S = new ModuleBuilder(CS1101S).withModuleTitle(VALID_MODULETITLE_ST2131)
                .build();
        assertTrue(CS1101S.isSameModule(editedCS1101S));

        // different modulecode -> returns false
        editedCS1101S = new ModuleBuilder(CS1101S).withModuleCode(VALID_MODULECODE_CS2100)
                .build();
        assertFalse(CS1101S.isSameModule(editedCS1101S));

        // different academic year -> returns false
        editedCS1101S = new ModuleBuilder(CS1101S).withAcademicYear(VALID_ACADEMICYEAR_CS2100)
                .build();
        assertFalse(CS1101S.isSameModule(editedCS1101S));

        // different semester -> returns false
        editedCS1101S = new ModuleBuilder(CS1101S).withSemester("4")
                .build();
        assertFalse(CS1101S.isSameModule(editedCS1101S));

        // different students list -> return true
        UniquePersonList validStudents = new UniquePersonList();
        validStudents.add(ALICE);
        editedCS1101S = new ModuleBuilder(CS1101S).withStudents(validStudents).build();
        assertTrue(CS1101S.isSameModule(editedCS1101S));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module copiedCS1101S = new ModuleBuilder(CS1101S).build();
        assertTrue(CS1101S.equals(copiedCS1101S));

        // same object -> returns true
        assertTrue(CS1101S.equals(CS1101S));

        // null -> returns false
        assertFalse(CS1101S.equals(null));

        // different type -> returns false
        assertFalse(CS1101S.equals(5));

        // different module -> returns false
        assertFalse(CS1101S.equals(CS1231));

        // different module code -> returns false
        copiedCS1101S = new ModuleBuilder(CS1101S)
                .withModuleCode(VALID_MODULECODE_CS2100)
                .build();
        assertFalse(CS1101S.equals(copiedCS1101S));

        // different module title -> returns false
        copiedCS1101S = new ModuleBuilder(CS1101S)
                .withModuleTitle(VALID_MODULETITLE_ST2131)
                .build();
        assertFalse(CS1101S.equals(copiedCS1101S));

        // different academic year -> returns false
        copiedCS1101S = new ModuleBuilder(CS1101S)
                .withAcademicYear(VALID_ACADEMICYEAR_CS2100)
                .build();
        assertFalse(CS1101S.equals(copiedCS1101S));

        // different semester -> returns false
        copiedCS1101S = new ModuleBuilder(CS1101S).withSemester("4").build();
        assertFalse(CS1101S.equals(copiedCS1101S));

        // different students list -> returns false
        UniquePersonList validStudents = new UniquePersonList();
        validStudents.add(ALICE);
        copiedCS1101S = new ModuleBuilder(CS1101S).withStudents(validStudents).build();
        assertFalse(CS1101S.equals(copiedCS1101S));
    }
}
