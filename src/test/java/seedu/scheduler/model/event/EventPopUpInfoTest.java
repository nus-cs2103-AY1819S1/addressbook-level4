package seedu.scheduler.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_UID_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_UID_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_UUID_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.scheduler.testutil.TypicalEvents.MA2101_JANUARY_1_2018_YEARLY;

import java.time.Duration;

import org.junit.Test;

import seedu.scheduler.testutil.Assert;

public class EventPopUpInfoTest {


    private static final EventPopUpInfo MA2101_EVENTPOPUPINFO = new EventPopUpInfo(VALID_EVENT_UID_MA2101,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA2101),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101), Duration.parse("PT15M"));
    private static final EventPopUpInfo MA2101_DIFFERENTDURATION = new EventPopUpInfo(VALID_EVENT_UID_MA2101,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA2101),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101), Duration.parse("PT30M"));
    private static final EventPopUpInfo MA2101_DIFFERENTSTARTTIME = new EventPopUpInfo(VALID_EVENT_UID_MA2101,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA3220),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101), Duration.parse("PT15M"));
    private static final EventPopUpInfo MA2101_DIFFERENTID = new EventPopUpInfo(VALID_EVENT_UID_MA3220,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA2101),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101), Duration.parse("PT15M"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventPopUpInfo(VALID_EVENT_UID_MA2101,
                VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
                new DateTime(VALID_START_DATETIME_MA2101),
                new DateTime(VALID_END_DATETIME_MA2101),
                new Description(VALID_DESCRIPTION_MA2101),
                new Venue(VALID_VENUE_MA2101), null));
    }


    @Test
    public void equals() {
        // same values -> returns true

        // same object -> returns true
        assertEquals(MA2101_EVENTPOPUPINFO, MA2101_EVENTPOPUPINFO);

        // null -> returns false
        assertNotEquals(null, MA2101_EVENTPOPUPINFO);

        // different type -> returns false
        assertNotEquals(5, MA2101_EVENTPOPUPINFO);

        // different object -> returns false
        assertNotEquals(MA2101_EVENTPOPUPINFO, MA2101_DIFFERENTDURATION);
        assertNotEquals(MA2101_EVENTPOPUPINFO, MA2101_DIFFERENTSTARTTIME);
        assertNotEquals(MA2101_EVENTPOPUPINFO, MA2101_DIFFERENTID);

    }

    @Test
    public void compareTo() {
        assertEquals(MA2101_EVENTPOPUPINFO.compareTo(MA2101_EVENTPOPUPINFO), 0);
        assertEquals(MA2101_EVENTPOPUPINFO.compareTo(MA2101_DIFFERENTDURATION), 1);
        assertEquals(MA2101_DIFFERENTDURATION.compareTo(MA2101_EVENTPOPUPINFO), -1);
    }

    @Test
    public void getPopUpDisplay() {
        String expectedString = "Venue: " + MA2101_JANUARY_1_2018_YEARLY.getVenue().toString() + "\n"
                + MA2101_JANUARY_1_2018_YEARLY.getStartDateTime().getPrettyString()
                + " - " + MA2101_JANUARY_1_2018_YEARLY.getEndDateTime().getPrettyString();
        assertEquals(MA2101_EVENTPOPUPINFO.getPopUpDisplay(), expectedString);

    }
}
