package seedu.address.model.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PermissionTest {

    String VALID_PERMISSION = "ADD_EMPLOYEE";
    String INVALID_PERMISSION = "invalid";

    @Test
    public void isValidPermission() {
        //null -> false
        assertFalse(Permission.isValidPermission(null));
        //Invalid permission -> false
        assertFalse(Permission.isValidPermission(INVALID_PERMISSION));
        //Valid permission -> true
        assertTrue(Permission.isValidPermission(VALID_PERMISSION));
    }
}