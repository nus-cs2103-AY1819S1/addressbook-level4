package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerId;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Volunteer[] getSampleVolunteers() {
        return new Volunteer[]{
            new Volunteer(new Name("Alex Yeoh"), new Gender("m"),
                    new Birthday("02-10-1996"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("student")),
            new Volunteer(new Name("Bernice Yu"), new Gender("f"),
                    new Birthday("15-03-1995"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("student", "driver")),
            new Volunteer(new Name("Charlotte Oliveiro"), new Gender("f"),
                    new Birthday("17-09-1992"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("adult")),
            new Volunteer(new Name("David Li"), new Gender("m"),
                    new Birthday("28-04-1994"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("student")),
            new Volunteer(new Name("Irfan Ibrahim"), new Gender("m"),
                    new Birthday("10-12-2000"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("student")),
            new Volunteer(new Name("Roy Balakrishnan"), new Gender("m"),
                    new Birthday("01-01-1984"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("adult"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[]{
            new Event(new seedu.address.model.event.Name("Blood Donation Drive 2018"),
                    new Location("750E Chai Chee Road"), new Date("02-10-2018"), new Date("05-10-2018"),
                    new Time("11:30"), new Time("17:30"), new Description("Donation drive for blood."),
                    getTagSet("Public", "Donation")),
            new Event(new seedu.address.model.event.Name("Youth Humanitarian Challenge"),
                    new Location("29 Havelock Road"), new Date("28-09-2018"), new Date("28-09-2018"),
                    new Time("10:00"), new Time("14:00"), new Description("To engage youths in humanitarianism."),
                    getTagSet("Competition")),
        };
    }

    public static Record[] getSampleRecord() {
        return new Record[]{
            new Record(new EventId(1), new VolunteerId(1), new Hour("1"), new Remark("Emcee")),
            new Record(new EventId(2), new VolunteerId(1), new Hour("1"), new Remark("Delivery Man"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Volunteer sampleVolunteer : getSampleVolunteers()) {
            sampleAb.addVolunteer(sampleVolunteer);
        }

        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }

        for (Record sampleRecord : getSampleRecord()) {
            sampleAb.addRecord(sampleRecord);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
