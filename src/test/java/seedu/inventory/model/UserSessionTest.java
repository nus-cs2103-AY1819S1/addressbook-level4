package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_ZUL;
import static seedu.inventory.testutil.TypicalStaffs.ZUL;

import org.junit.Test;

import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.StaffBuilder;

public class UserSessionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UserSession(null));
    }

    @Test
    public void updateUser() {
        UserSession userSession = new UserSession(ZUL);

        Assert.assertThrows(NullPointerException.class, () -> userSession.updateUser(null));

        Staff editedZul = new StaffBuilder(ZUL).withName(VALID_NAME_ZUL).build();
        userSession.updateUser(editedZul);
        assertEquals(userSession.getUser(), editedZul);
    }

    @Test
    public void isLoggedIn() {
        UserSession userSession = new UserSession(ZUL);
        assertTrue(userSession.isLoggedIn());

        userSession.logout();
        assertFalse(userSession.isLoggedIn());
    }
}
