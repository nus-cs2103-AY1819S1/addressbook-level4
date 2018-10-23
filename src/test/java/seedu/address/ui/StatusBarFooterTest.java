package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_UPDATED;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.AddressBook;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final AddressBookChangedEvent EVENT_STUB = new AddressBookChangedEvent(new AddressBook());

    private StatusBarFooterHandle statusBarFooterHandle;

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter();
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(LOGIN_STATUS_INITIAL);

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(String.format(LOGIN_STATUS_UPDATED, "temp"));
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
