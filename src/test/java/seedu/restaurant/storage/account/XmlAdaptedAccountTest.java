package seedu.restaurant.storage.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_PASSWORD;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_USERNAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.restaurant.storage.elements.XmlAdaptedAccount.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ONE;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_TWO;

import org.junit.Test;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;
import seedu.restaurant.storage.elements.XmlAdaptedAccount;
import seedu.restaurant.testutil.Assert;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class XmlAdaptedAccountTest {

    private XmlAdaptedAccount account = null;

    @Test
    public void toModelType_validAccountDetails_returnsAccount() throws Exception {
        account = new XmlAdaptedAccount(DEMO_ADMIN);
        assertEquals(DEMO_ADMIN, account.toModelType());
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(null, VALID_PASSWORD_DEMO_ONE, VALID_NAME_DEMO_ONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(INVALID_USERNAME, VALID_PASSWORD_DEMO_ONE, VALID_NAME_DEMO_ONE);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(VALID_USERNAME_DEMO_ONE, null, VALID_NAME_DEMO_ONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(VALID_USERNAME_DEMO_ONE, INVALID_PASSWORD, VALID_NAME_DEMO_ONE);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedAccount accountDemoOne = new XmlAdaptedAccount(new AccountBuilder(DEMO_ONE).build());

        // same object
        assertTrue(accountDemoOne.equals(accountDemoOne));

        XmlAdaptedAccount accountDemoOneDuplicate = new XmlAdaptedAccount(new AccountBuilder(DEMO_ONE).build());

        // different object, same state
        assertTrue(accountDemoOne.equals(accountDemoOneDuplicate));

        XmlAdaptedAccount accountDemoTwo = new XmlAdaptedAccount(new AccountBuilder(DEMO_TWO).build());

        assertFalse(accountDemoOne.equals(accountDemoTwo));

        // not same instance
        assertFalse(accountDemoOne.equals(null));

        // not same instance
        assertFalse(accountDemoOne.equals(1));
    }
}
