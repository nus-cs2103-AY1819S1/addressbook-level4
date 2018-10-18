package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class XmlAdaptedPermissionTest {
    private static final String FIRST_VALID_PERMISSION = "ASSIGN_PERMISSION";
    private static final String SECOND_VALID_PERMISSION = "ADD_EMPLOYEE";

    @Test
    public void equals() {
        XmlAdaptedPermission validXmlPermission = new XmlAdaptedPermission(FIRST_VALID_PERMISSION);
        // same object -> returns true
        assertTrue(validXmlPermission.equals(validXmlPermission));

        // same values -> returns true
        XmlAdaptedPermission duplicateXmlPermission = new XmlAdaptedPermission(FIRST_VALID_PERMISSION);
        assertTrue(validXmlPermission.equals(duplicateXmlPermission));

        // different types -> returns false
        assertFalse(validXmlPermission.equals(1));

        // null -> returns false
        assertFalse(validXmlPermission.equals(null));

        // different person -> returns false
        XmlAdaptedPermission differentXmlPermission = new XmlAdaptedPermission(SECOND_VALID_PERMISSION);
        assertFalse(validXmlPermission.equals(differentXmlPermission));
    }
}
