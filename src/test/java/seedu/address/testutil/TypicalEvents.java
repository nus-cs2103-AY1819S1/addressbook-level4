package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_MEETING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */

public class TypicalEvents {

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event DOCTORAPPT =
            new ScheduledEventBuilder()
                    .withEventName(VALID_EVENT_NAME_DOCTORAPPT)
                    .withEventDescription(VALID_EVENT_DESC_DOCTORAPPT)
                    .withEventDate(VALID_EVENT_DATE_DOCTORAPPT)
                    .withEventStartTime(VALID_EVENT_START_TIME_DOCTORAPPT)
                    .withEventEndTime(VALID_EVENT_END_TIME_DOCTORAPPT)
                    .withEventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)
                    .build();
    public static final Event MEETING =
            new ScheduledEventBuilder()
                    .withEventName(VALID_EVENT_NAME_MEETING)
                    .withEventDescription(VALID_EVENT_DESC_MEETING)
                    .withEventDate(VALID_EVENT_DATE_MEETING)
                    .withEventStartTime(VALID_EVENT_START_TIME_MEETING)
                    .withEventEndTime(VALID_EVENT_END_TIME_MEETING)
                    .withEventAddress(VALID_EVENT_ADDRESS_MEETING)
                    .build();

    private TypicalEvents() {} // prevents instantiation

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(DOCTORAPPT, MEETING));
    }
}
