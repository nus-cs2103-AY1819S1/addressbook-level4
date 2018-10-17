package seedu.inventory.model.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;
import static seedu.inventory.testutil.TypicalStaffs.TENGXIONG;
import static seedu.inventory.testutil.TypicalStaffs.ZUL;

import org.junit.Test;

import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.StaffBuilder;

public class StaffTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Staff(null, null, null, null));
    }

    @Test
    public void isSameStaff() {
        // same object -> returns true
        assertTrue(ZUL.isSameStaff(ZUL));

        // null -> returns false
        assertFalse(ZUL.isSameStaff(null));

        // different username and password -> returns false
        Staff editedZul = new StaffBuilder(ZUL)
                .withUsername(VALID_USERNAME_ZUL).withPassword(VALID_PASSWORD_ZUL).build();
        assertFalse(ZUL.isSameStaff(editedZul));

        // different password -> returns false
        editedZul = new StaffBuilder(ZUL).withPassword(VALID_PASSWORD_ZUL).build();
        assertFalse(ZUL.isSameStaff(editedZul));

        // same name, same username, different attributes -> returns false
        editedZul = new StaffBuilder(ZUL).withPassword(VALID_PASSWORD_ZUL).withRole(Staff.Role.manager)
                .build();
        assertFalse(ZUL.isSameStaff(editedZul));

        // same name, same password, different attributes -> returns false
        editedZul = new StaffBuilder(ZUL).withUsername(VALID_USERNAME_ZUL).withRole(Staff.Role.user)
                .build();
        assertFalse(ZUL.isSameStaff(editedZul));

        // same name, same username, same password, different attributes -> returns true
        editedZul = new StaffBuilder(ZUL).withRole(Staff.Role.user).build();
        assertTrue(ZUL.isSameStaff(editedZul));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Staff zulCopy = new StaffBuilder(ZUL).build();
        assertTrue(ZUL.equals(zulCopy));

        // same object -> returns true
        assertTrue(ZUL.equals(ZUL));

        // null -> returns false
        assertFalse(ZUL.equals(null));

        // different type -> returns false
        assertFalse(ZUL.equals(9));

        // different person -> returns false
        assertFalse(ZUL.equals(TENGXIONG));

        // different name -> returns false
        Staff editiedZul = new StaffBuilder(ZUL).withName(VALID_NAME_ZUL).build();
        assertFalse(ZUL.equals(editiedZul));

        // different username -> returns false
        editiedZul = new StaffBuilder(ZUL).withUsername(VALID_USERNAME_ZUL).build();
        assertFalse(ZUL.equals(editiedZul));

        // different password -> returns false
        editiedZul = new StaffBuilder(ZUL).withPassword(VALID_PASSWORD_ZUL).build();
        assertFalse(ZUL.equals(editiedZul));

        // different role -> returns false
        editiedZul = new StaffBuilder(ZUL).withRole(Staff.Role.manager).build();
        assertFalse(ZUL.equals(editiedZul));
    }
}
