package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidName));
    }

    @Test
    public void isValidModuleTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ModuleCode.isValidCode(null));

        // invalid name
        assertFalse(ModuleCode.isValidCode("")); // empty string
        assertFalse(ModuleCode.isValidCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidCode("^")); // only non-alphanumeric characters
        assertFalse(ModuleCode.isValidCode("peter*")); // contains non-alphanumeric characters
        assertFalse(ModuleCode.isValidCode("peter jack")); // alphabets only
        assertFalse(ModuleCode.isValidCode("12345")); // numbers only
        assertFalse(ModuleCode.isValidCode("peter the 2nd")); // alphanumeric characters
        assertFalse(ModuleCode.isValidCode("Capital Tan")); // with capital letters
        assertFalse(ModuleCode.isValidCode("David Roger Jackson Ray Jr 2nd")); // long names

        // valid name
        assertTrue(ModuleCode.isValidCode("CS1231"));
        assertTrue(ModuleCode.isValidCode("CS1231R"));
    }
}
