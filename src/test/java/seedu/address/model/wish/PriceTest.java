package seedu.address.model.wish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new Price((Price) null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void copyConstructor_success() {
        Price price = new Price("93028");
        Price copy = new Price(price);
        assertEquals(price, copy);
    }

    @Test
    public void isValidPrice() {
        // null price value
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("91..1")); // double dot
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("1.0e321")); // exp
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits
        assertFalse(Price.isValidPrice("2.")); // no digits after decimal point

        // valid prices
        assertTrue(Price.isValidPrice("93121534")); // no decimal digit
        assertTrue(Price.isValidPrice("1.0")); // one decimal digit
        assertTrue(Price.isValidPrice("1.02")); // two decimal digits
    }

    @Test
    public void toStringTest() {
        Price price = new Price(VALID_PRICE_AMY);
        assertTrue(price.toString().equals(VALID_PRICE_AMY));
    }

    @Test
    public void hashCodeTest() {
        assertTrue(new Price(VALID_PRICE_AMY).hashCode()
                == new Price(VALID_PRICE_AMY).hashCode());
    }
}
