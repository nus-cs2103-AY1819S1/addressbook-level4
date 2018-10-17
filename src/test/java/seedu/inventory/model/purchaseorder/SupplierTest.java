package seedu.inventory.model.purchaseorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SupplierTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Supplier(null));
    }

    @Test
    public void constructor_invalidSupplier_throwsIllegalArgumentException() {
        String invalidSupplier = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Supplier(invalidSupplier));
    }

    @Test
    public void constructor_validSupplier() {
        assertNotNull(new Supplier("Apple Inc."));
    }

    @Test
    public void isValidSupplier() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Supplier.isValidSupplier(null));

        // invalid Supplier
        assertFalse(Supplier.isValidSupplier("")); // empty string
        assertFalse(Supplier.isValidSupplier(" ")); // spaces only

        // valid Supplier
        assertTrue(Supplier.isValidSupplier("Samsung"));
    }

    @Test
    public void testToString() {
        Supplier supplier = new Supplier("Apple Inc.");
        String expected = "Apple Inc.";
        assertEquals(expected, supplier.toString());
    }

    @Test
    public void testEqualsSymmetric() {
        Supplier s1 = new Supplier("Apple Inc."); // equals and hashCode check name field value
        Supplier s2 = new Supplier("Apple Inc.");
        assertTrue(s1.equals(s2) && s2.equals(s1));
        assertTrue(s1.hashCode() == s2.hashCode());
    }
}
