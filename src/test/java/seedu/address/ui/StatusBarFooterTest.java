package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.StatusBarFooter.DIRECTORY_ERROR;
import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_UPDATED;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;

import seedu.address.commons.events.ui.ChangeDirectoryEvent;
import seedu.address.commons.events.ui.LoginStatusEvent;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String TEMP_EMAIL = "user@email.com";
    private static final String TEMP_DIR = "Stub Directory";

    private static final LoginStatusEvent LOGIN_STUB = new LoginStatusEvent(TEMP_EMAIL);
    private static final ChangeDirectoryEvent DIRECTORY_STUB = new ChangeDirectoryEvent(TEMP_DIR);
    private StatusBarFooterHandle statusBarFooterHandle;

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(null, null);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        assertStatusBarContent(LOGIN_STATUS_INITIAL, DIRECTORY_ERROR);
        postNow(LOGIN_STUB);
        postNow(DIRECTORY_STUB);
        assertStatusBarContent(String.format(LOGIN_STATUS_UPDATED, TEMP_EMAIL), TEMP_DIR);

    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedLoginStatus, String expectedDirectoryDisplay) {
        assertEquals(expectedLoginStatus, statusBarFooterHandle.getLoginStatus());
        assertEquals(expectedDirectoryDisplay, statusBarFooterHandle.getDirectoryDisplay());
        guiRobot.pauseForHuman();
    }

}
