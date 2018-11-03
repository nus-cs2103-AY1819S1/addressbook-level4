package seedu.restaurant.ui.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertCardDisplaysAccount;

import org.junit.Test;

import guitests.guihandles.accounts.AccountCardHandle;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.ui.GuiUnitTest;

//@@author AZhiKai
public class AccountCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Account account = new AccountBuilder().build();
        AccountCard accountCard = new AccountCard(account, 1);
        uiPartRule.setUiPart(accountCard);
        assertCardDisplay(accountCard, account, 1);
    }

    @Test
    public void equals() {
        Account account = new AccountBuilder().build();
        AccountCard accountCard = new AccountCard(account, 0);

        // same item, same index -> returns true
        AccountCard copy = new AccountCard(account, 0);
        assertTrue(accountCard.equals(copy));

        // same object -> returns true
        assertTrue(accountCard.equals(accountCard));

        // null -> returns false
        assertFalse(accountCard.equals(null));

        // different types -> returns false
        assertFalse(accountCard.equals(0));

        // different item, same index -> returns false
        Account differentAccount = new AccountBuilder().withUsername("someotherusername").build();
        assertFalse(accountCard.equals(new AccountCard(differentAccount, 0)));

        // same item, different index -> returns false
        assertFalse(accountCard.equals(new AccountCard(account, 1)));
    }

    /**
     * Asserts that {@code itemCard} displays the details of {@code expectedItem} correctly and matches {@code
     * expectedId}.
     */
    private void assertCardDisplay(AccountCard accountCard, Account expectedAccount, int expectedId) {
        guiRobot.pauseForHuman();

        AccountCardHandle accountCardHandle = new AccountCardHandle(accountCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", accountCardHandle.getId());

        // verify item details are displayed correctly
        assertCardDisplaysAccount(expectedAccount, accountCardHandle);
    }
}
