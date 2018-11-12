package seedu.address.model.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PermissionTest {

    private String validPermission = "ADD_EMPLOYEE";
    private String invalidPermission = "invalid";

    @Test
    public void isValidPermission() {
        //null -> false
        assertFalse(Permission.isValidPermission(null));
        //Invalid permission -> false
        assertFalse(Permission.isValidPermission(invalidPermission));
        //Valid permission -> true
        assertTrue(Permission.isValidPermission(validPermission));
    }
}
