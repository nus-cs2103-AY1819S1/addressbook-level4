package seedu.modsuni.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_CODE_ACC1002;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalModules.CS1010;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.ModuleBuilder;

public class ModuleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(CS1010.isSameModule(CS1010));

        // null -> returns false
        assertFalse(CS1010.isSameModule(null));

        // different code -> returns false
        Module editedCs1010 = new ModuleBuilder(CS1010).withCode(VALID_CODE_ACC1002).build();
        assertFalse(CS1010.isSameModule(editedCs1010));
    }

    @Test
    public void checkPrereq() {
        List<Code> codes = new ArrayList<>();
        codes.add(CS1010.getCode());

        Prereq prereq = new Prereq();

        PrereqDetails prereqDetails = new PrereqDetails();
        prereqDetails.setCode(Optional.of(CS1010.getCode()));
        List<PrereqDetails> prereqOr = new ArrayList<>();
        prereqOr.add(prereqDetails);

        prereq.setOr(Optional.of(prereqOr));

        Module acc1002 = new ModuleBuilder(ACC1002).withPrereq(prereq).build();
        assertTrue(acc1002.checkPrereq(codes));
    }

    @Test
    public void isPrefixModule() {
        Module cs1010Prefix = new Module(new Code("CS"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        // same object -> returns true
        assertTrue(CS1010.isPrefixModule((CS1010)));

        // null -> returns false
        assertFalse(CS1010.isPrefixModule((null)));

        // this is the prefix of other module -> returns true
        assertTrue(cs1010Prefix.isPrefixModule(CS1010));

        // other module is the prefix of this -> returns false
        assertFalse(CS1010.isPrefixModule(cs1010Prefix));

        // is not the prefix -> returns false
        assertFalse(cs1010Prefix.isPrefixModule(ACC1002));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module cs1010Copy = new ModuleBuilder(CS1010).build();
        assertTrue(CS1010.equals(cs1010Copy));

        // same object -> returns true
        assertTrue(CS1010.equals(CS1010));

        // null -> retturns false
        assertFalse(CS1010.equals(null));

        // different module -> returns false
        assertFalse(CS1010.equals(ACC1002));

        // different code -> returns false
        Module editedCs1010 = new ModuleBuilder(CS1010).withCode(VALID_CODE_ACC1002).build();
        assertFalse(CS1010.equals(editedCs1010));
    }
}
