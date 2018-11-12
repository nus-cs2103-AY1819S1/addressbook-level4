package seedu.address.model.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.order.Order;
import seedu.address.testutil.Assert;
import seedu.address.testutil.OrderBuilder;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void getPostalCode() {
        String[] postalCodes = {"654321", "100321", "100000", "123456"};
        String[] directory = {"1234 Johnson Street 654321", "Block 123, Fire Road, #10-10, 100321",
            "Block 546, 100000", "Triad Street, 123456"};

        for (int i = 0; i < directory.length; i++) {
            Order order = new OrderBuilder().withAddress(directory[i]).build();
            assertTrue(order.getAddress().getPostalCode().equals(postalCodes[i]));
        }

    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses - empty
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // invalid addresses - no 6 digit postal code
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertFalse(Address.isValidAddress("-")); // one character
        assertFalse(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));

        // invalid address - postal code not at the end
        assertFalse(Address.isValidAddress("Blk 456, Den Road, 600123, #01-355"));
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355, 600123, "));
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355, 600123 "));

        // valid address
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355, 610123"));
        assertTrue(Address.isValidAddress("- 615132")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USAl 1456789"));
    }
}
