package seedu.address.model.entry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new seedu.address.model.person.Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new seedu.address.model.person.Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> seedu.address.model.person.Address.isValidAddress(null));

        // invalid addresses
        assertFalse(seedu.address.model.person.Address.isValidAddress("")); // empty string
        assertFalse(seedu.address.model.person.Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(seedu.address.model.person.Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(seedu.address.model.person.Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        assertFalse(new Address("123, Jurong West Ave 6, #08-111").equals(
                new Address("123, Jurong East Ave 6, #08-111")));
    }
}
