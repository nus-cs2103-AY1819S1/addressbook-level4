package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.BLOOD;

import seedu.address.model.AddressBook;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class containing {@code Volunteer} objects with {@code records}, to be used in tests.
 */
public class TypicalVolunteersWithRecords {
    private TypicalVolunteersWithRecords() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical {@code volunteers} and their {@code records}.
     */
    public static AddressBook getTypicalVolunteersWithRecordsAddressBook() {
        AddressBook ab = new AddressBook();

        // Use only a single event
        ab.addEvent(BLOOD);

        // Add 1 record for each typical volunteer for this single event
        for (Volunteer volunteer : TypicalVolunteers.getTypicalVolunteers()) {
            ab.addVolunteer(volunteer);
            ab.addRecord(new Record(
                    BLOOD.getEventId(),
                    volunteer.getVolunteerId(),
                    new Hour("1"),
                    new Remark("Participant")));
        }
        return ab;
    }
}
