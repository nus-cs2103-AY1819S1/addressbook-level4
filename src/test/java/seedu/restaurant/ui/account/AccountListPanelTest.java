package seedu.restaurant.ui.account;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.testutil.account.TypicalAccounts.getTypicalAccounts;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertCardDisplaysAccount;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.accounts.AccountCardHandle;
import guitests.guihandles.accounts.AccountListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.ui.GuiUnitTest;

//@@author AZhiKai
public class AccountListPanelTest extends GuiUnitTest {

    private static final ObservableList<Account> TYPICAL_ACCOUNTS =
            FXCollections.observableList(getTypicalAccounts());

    private AccountListPanelHandle accountListPanelHandle;

    @Before
    public void setUp() {
        AccountListPanel accountListPanel = new AccountListPanel(TYPICAL_ACCOUNTS);
        uiPartRule.setUiPart(accountListPanel);

        accountListPanelHandle = new AccountListPanelHandle(getChildNode(accountListPanel.getRoot(),
                AccountListPanelHandle.ACCOUNT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_ACCOUNTS.size(); i++) {
            accountListPanelHandle.navigateToCard(TYPICAL_ACCOUNTS.get(i));
            Account expectedAccount = TYPICAL_ACCOUNTS.get(i);
            AccountCardHandle actualCard = accountListPanelHandle.getAccountCardHandle(i);

            assertCardDisplaysAccount(expectedAccount, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
