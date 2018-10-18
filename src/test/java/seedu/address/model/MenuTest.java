package seedu.address.model;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.model.expenses.exceptions.ItemNotFoundException;
import seedu.address.testutil.Assert;

public class MenuTest {

    private static final Menu validMenu = new Menu();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Menu(null));
    }

    @Test
    public void setMenu_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> validMenu.setMenu(null));
    }

    @Test
    public void isValidMenuNumber_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> validMenu.isValidMenuNumber(null));
    }

    @Test
    public void isValidMenuNumber_invalidString_returnsFalse() {
        assertFalse(validMenu.isValidMenuNumber("-"));
    }

    @Test
    public void getExpenseType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> validMenu.getExpenseType(null));
    }

    @Test
    public void getExpenseType_invalidString_throwsItemNotFoundException() {
        Assert.assertThrows(ItemNotFoundException.class, () -> validMenu.getExpenseType("-"));
    }
}
