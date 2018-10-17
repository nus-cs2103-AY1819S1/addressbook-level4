package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SkuTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Sku(null));
    }

    @Test
    public void constructor_invalidSku_throwsIllegalArgumentException() {
        String invalidSku = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Sku(invalidSku));
    }

    @Test
    public void isValidSku() {
        // null sku
        Assert.assertThrows(NullPointerException.class, () -> Sku.isValidSku(null));

        // blank sku
        assertFalse(Sku.isValidSku("")); // empty string
        assertFalse(Sku.isValidSku(" ")); // spaces only

        // missing parts
        assertFalse(Sku.isValidSku("@iphonex")); // invalid character in front
        assertFalse(Sku.isValidSku("ipho@nex")); // invalid character in middle
        assertFalse(Sku.isValidSku("iphone@")); // trailing invalid characters

        // valid sku
        assertTrue(Sku.isValidSku("iphone-xs"));
        assertTrue(Sku.isValidSku("a_xs")); // minimal
        assertTrue(Sku.isValidSku("iphonexs")); // alphabets only
        assertTrue(Sku.isValidSku("_-_-example-_-_-")); // special characters local part
        assertTrue(Sku.isValidSku("123-_-")); // numeric local part and domain name
        assertTrue(Sku.isValidSku("123-_-asd-__-123")); // mixture of alphanumeric and special characters
        assertTrue(Sku.isValidSku("very_-longskU-123with-characters-and-special-chars-nos")); // long domain name
    }
}
