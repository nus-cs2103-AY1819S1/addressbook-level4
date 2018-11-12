package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.StatusBarFooter.TOTAL_VOLUNTEERS_STATUS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.testutil.AddressBookBuilder;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final Path STUB_SAVE_LOCATION = Paths.get("Stub");
    private static final Path RELATIVE_PATH = Paths.get(".");

    private static final AddressBookChangedEvent EVENT_STUB = new AddressBookChangedEvent(
            new AddressBookBuilder().withVolunteer(ALICE).build());

    private static final int INITIAL_TOTAL_VOLUNTEERS = 0;

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        StatusBarFooter.setClock(injectedClock);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, INITIAL_TOTAL_VOLUNTEERS);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH.resolve(STUB_SAVE_LOCATION).toString(), SYNC_STATUS_INITIAL,
                String.format(TOTAL_VOLUNTEERS_STATUS, INITIAL_TOTAL_VOLUNTEERS));

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH.resolve(STUB_SAVE_LOCATION).toString(),
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
                String.format(TOTAL_VOLUNTEERS_STATUS, EVENT_STUB.data.getVolunteerList().size()));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}, and the total volunteers matches that of
     * {@code expectedTotalVolunteersStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus,
                                        String expectedTotalVolunteersStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalVolunteersStatus, statusBarFooterHandle.getTotalVolunteersStatus());
        guiRobot.pauseForHuman();
    }

}
