package seedu.restaurant.ui.account;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.accounts.UsernameDisplayHandle;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.commons.events.ui.accounts.LogoutEvent;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.ui.GuiUnitTest;

//@@author AZhiKai
public class UsernameDisplayTest extends GuiUnitTest {

    private static final LoginEvent LOGIN_EVENT_STUB = new LoginEvent(new AccountBuilder().build());

    private UsernameDisplayHandle resultDisplayHandle;

    @Before
    public void setUp() {
        UsernameDisplay usernameDisplay = new UsernameDisplay();
        uiPartRule.setUiPart(usernameDisplay);

        resultDisplayHandle = new UsernameDisplayHandle(getChildNode(usernameDisplay.getRoot(),
                UsernameDisplayHandle.USERNAME_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals(UsernameDisplay.ACCOUNT_STATUS_GUEST, resultDisplayHandle.getText());

        // new result received - login
        postNow(LOGIN_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(String.format(UsernameDisplay.ACCOUNT_STATUS, LOGIN_EVENT_STUB.username.toString().toUpperCase()),
                resultDisplayHandle.getText());

        guiRobot.pauseForHuman();

        // new result received - logout
        postNow(new LogoutEvent());
        assertEquals(UsernameDisplay.ACCOUNT_STATUS_GUEST, resultDisplayHandle.getText());
    }
}
