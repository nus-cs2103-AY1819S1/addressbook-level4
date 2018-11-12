package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPETITION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event BLOOD = new EventBuilder().withName("Blood Donation Drive 2018")
            .withLocation("750E, Chai Chee Road, #08-111").withStartDate("02-10-2018").withEndDate("05-10-2018")
            .withStartTime("11:30").withEndTime("17:30").withDescription("Donation drive for blood.")
            .withTags("Public", "Donation").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event YOUTH = new EventBuilder().withName(VALID_NAME_YOUTH)
            .withLocation(VALID_LOCATION_YOUTH).withStartDate(VALID_START_DATE_YOUTH).withEndDate(VALID_END_DATE_YOUTH)
            .withStartTime(VALID_START_TIME_YOUTH).withEndTime(VALID_END_TIME_YOUTH)
            .withDescription(VALID_DESCRIPTION_YOUTH).withTags(VALID_TAG_COMPETITION).build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(BLOOD, YOUTH));
    }
}
