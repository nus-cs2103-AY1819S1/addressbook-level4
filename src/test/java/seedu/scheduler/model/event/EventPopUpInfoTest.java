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


    private EventPopUpInfo MA2101_eventPopUpInfo = new EventPopUpInfo(VALID_EVENT_UID_MA2101,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA2101),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101)
            , Duration.parse("PT15M"));
    private EventPopUpInfo MA2101_differentDuration = new EventPopUpInfo(VALID_EVENT_UID_MA2101,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA2101),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101)
            , Duration.parse("PT30M"));
    private EventPopUpInfo MA2101_differentStarTime = new EventPopUpInfo(VALID_EVENT_UID_MA2101,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA3220),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101)
            , Duration.parse("PT15M"));
    private EventPopUpInfo MA2101_differentId = new EventPopUpInfo(VALID_EVENT_UID_MA3220,
            VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
            new DateTime(VALID_START_DATETIME_MA2101),
            new DateTime(VALID_END_DATETIME_MA2101),
            new Description(VALID_DESCRIPTION_MA2101),
            new Venue(VALID_VENUE_MA2101)
            , Duration.parse("PT15M"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventPopUpInfo(VALID_EVENT_UID_MA2101,
                VALID_EVENT_UUID_MA2101, new EventName(VALID_EVENT_NAME_MA2101),
                new DateTime(VALID_START_DATETIME_MA2101),
                new DateTime(VALID_END_DATETIME_MA2101),
                new Description(VALID_DESCRIPTION_MA2101),
                new Venue(VALID_VENUE_MA2101)
                , null));
    }


    @Test
    public void equals() {
        // same values -> returns true

        // same object -> returns true
        assertEquals(MA2101_eventPopUpInfo, MA2101_eventPopUpInfo);

        // null -> returns false
        assertNotEquals(null, MA2101_eventPopUpInfo);

        // different type -> returns false
        assertNotEquals(5, MA2101_eventPopUpInfo);

        // different object -> returns false
        assertNotEquals(MA2101_eventPopUpInfo, MA2101_differentDuration);
        assertNotEquals(MA2101_eventPopUpInfo, MA2101_differentStarTime);
        assertNotEquals(MA2101_eventPopUpInfo, MA2101_differentId);

    }

    @Test
    public void getPopUpDisplay() {

        String expectedString = "Venue: " + MA2101_JANUARY_1_2018_YEARLY.getVenue().toString() + "\n" +
                MA2101_JANUARY_1_2018_YEARLY.getStartDateTime().getPrettyString()
                + " - " + MA2101_JANUARY_1_2018_YEARLY.getEndDateTime().getPrettyString();
        assertEquals(MA2101_eventPopUpInfo.getPopUpDisplay(), expectedString);

    }
}
