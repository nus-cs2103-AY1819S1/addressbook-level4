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

        //Invalid id (less than or equal to 0)
        assertFalse(Id.isValidId(0)); //Zero
        assertFalse(Id.isValidId(-1)); //Negative
        assertFalse(Id.isValidId(-9478));
        assertFalse(Id.isValidId(-120 * 102));
        assertFalse(Id.isValidId((int)Math.pow(2, 32) + 1)); //More than 2 ^ 32
     
        //valid id
        assertTrue(Id.isValidId(1)); // One
        assertTrue(Id.isValidId(20));
        assertTrue(Id.isValidId(015)); //Leading zeros.
        assertTrue(Id.isValidId(00204));
        assertTrue(Id.isValidId(10274));
        assertTrue(Id.isValidId(350274));
        assertTrue(Id.isValidId((int)Math.pow(2, 32))); //Very large number
    }
}
