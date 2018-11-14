package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ModuleTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ModuleTitle(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ModuleTitle(invalidName));
    }

    @Test
    public void isValidModuleTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ModuleTitle.isValidTitle(null));

        // invalid name
        assertFalse(ModuleTitle.isValidTitle("")); // empty string
        assertFalse(ModuleTitle.isValidTitle(" ")); // spaces only
        assertFalse(ModuleTitle.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(ModuleTitle.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ModuleTitle.isValidTitle("peter jack")); // alphabets only
        assertTrue(ModuleTitle.isValidTitle("12345")); // numbers only
        assertTrue(ModuleTitle.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(ModuleTitle.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(ModuleTitle.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
