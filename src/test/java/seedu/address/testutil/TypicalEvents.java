//@@author theJrLinguist
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event TUTORIAL = new EventBuilder().withName("Tutorial")
            .withAddress("NUS UTown")
            .withTags("friends")
            .withOrganiser(new PersonBuilder().build())
            .build();
    public static final Event MEETING = new EventBuilder().withName("Meeting")
            .withAddress("SOC Canteen")
            .withTags("friends")
            .withOrganiser(new PersonBuilder().build())
            .build();
    public static final Event DINNER = new EventBuilder().withName("Dinner")
            .withAddress("Arts Canteen")
            .withTags("friends")
            .withOrganiser(new PersonBuilder().build())
            .build();

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
        return new ArrayList<>(Arrays.asList(TUTORIAL, MEETING, DINNER));
    }
}
