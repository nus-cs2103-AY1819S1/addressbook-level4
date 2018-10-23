package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalModules.CS1101S;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

        // different moduletitle -> returns false
        Module editedCS1101S = new ModuleBuilder(CS1101S).build();

        // different modulecode -> returns false

    }

    @Test
    public void equals() {

    }
}
