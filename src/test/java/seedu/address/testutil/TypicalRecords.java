package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.record.Record;

/**
 * A utility class containing a list of {@code Records} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record R1 = new RecordBuilder().withEventId("1")
            .withVolunteerId("1")
            .withHour("1")
            .withRemark("Emcee").build();

    public static final Record R2 = new RecordBuilder().withEventId("2")
            .withVolunteerId("2")
            .withHour("2")
            .withRemark("Delivery man").build();

    public static final Record R3 = new RecordBuilder().withEventId("2")
            .withVolunteerId("1")
            .withHour("2")
            .withRemark("Food delivery").build();

    private TypicalRecords() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Record record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(R1, R2, R3));
    }
}
