package seedu.address.model.doctor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.address.testutil.Assert;

public class IdTest {
    
    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        int invalidId = 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }
    
    @Test
    public void isValidId() {
        // null id
        Assert.assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        //invalid id
        assertFalse(Id.isValidId("-1"));
        assertFalse(Id.isValidId("0-120"));
        assertFalse(Id.isValidId("a"));
        assertFalse(Id.isValidId("b12"));
        assertFalse(Id.isValidId("1c2asd"));
        assertFalse(Id.isValidId("_12casd"));
     
        //valid id
        assertTrue(Id.isValidId("1"));
        assertTrue(Id.isValidId("20"));
        assertTrue(Id.isValidId("015"));
        assertTrue(Id.isValidId("00204"));
        assertTrue(Id.isValidId("10274"));
        assertTrue(Id.isValidId("350274"));
    }
}
