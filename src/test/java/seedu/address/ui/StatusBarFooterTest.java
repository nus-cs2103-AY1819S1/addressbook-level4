package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_INITIAL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;

public class StatusBarFooterTest extends GuiUnitTest {

    private StatusBarFooterHandle statusBarFooterHandle;

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(null, null);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(LOGIN_STATUS_INITIAL);

        // after address book is updated
        //postNow(EVENT_STUB);
        assertStatusBarContent(LOGIN_STATUS_INITIAL);
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedLoginStatus) {
        assertEquals(expectedLoginStatus, statusBarFooterHandle.getLoginStatus());
        guiRobot.pauseForHuman();
    }

}
