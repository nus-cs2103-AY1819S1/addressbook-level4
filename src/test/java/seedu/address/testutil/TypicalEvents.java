package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MEETING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */

public class TypicalEvents {

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event DOCTORAPPT =
            new ScheduledEventBuilder()
                    .withName(VALID_NAME_DOCTORAPPT)
                    .withDescription(VALID_DESC_DOCTORAPPT)
                    .withDate(VALID_DATE_DOCTORAPPT)
                    .withTime(VALID_TIME_DOCTORAPPT)
                    .withAddress(VALID_ADDRESS_DOCTORAPPT)
                    .build();
    public static final Event MEETING =
            new ScheduledEventBuilder()
                    .withName(VALID_NAME_MEETING)
                    .withDescription(VALID_DESC_MEETING).
                    withDate(VALID_DATE_MEETING)
                    .withTime(VALID_TIME_MEETING)
                    .withAddress(VALID_ADDRESS_MEETING)
                    .build();

    private TypicalEvents() {} // prevents instantiation
}
