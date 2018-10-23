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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerAddress;
import seedu.address.model.volunteer.VolunteerEmail;
import seedu.address.model.volunteer.VolunteerName;
import seedu.address.model.volunteer.VolunteerPhone;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("driver", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("student")),
            new Person(new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                         new Email("irfan@example.com"),
                         new Address("Blk 47 Tampines Street 20, #17-35"),
                         getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
        };
    }
    public static Volunteer[] getSampleVolunteers() {
        return new Volunteer[]{
            new Volunteer(new VolunteerName("Alex Yeoh"), new Gender("m"),
                    new Birthday("02-10-1996"), new VolunteerPhone("87438807"),
                    new VolunteerEmail("alexyeoh@example.com"),
                    new VolunteerAddress("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("student")),
            new Volunteer(new VolunteerName("Bernice Yu"), new Gender("f"),
                    new Birthday("15-03-1995"), new VolunteerPhone("99272758"),
                    new VolunteerEmail("berniceyu@example.com"),
                    new VolunteerAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("student", "driver")),
            new Volunteer(new VolunteerName("Charlotte Oliveiro"), new Gender("f"),
                    new Birthday("17-09-1992"), new VolunteerPhone("93210283"),
                    new VolunteerEmail("charlotte@example.com"),
                    new VolunteerAddress("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("adult")),
            new Volunteer(new VolunteerName("David Li"), new Gender("m"),
                    new Birthday("28-04-1994"), new VolunteerPhone("91031282"),
                    new VolunteerEmail("lidavid@example.com"),
                    new VolunteerAddress("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("student")),
            new Volunteer(new VolunteerName("Irfan Ibrahim"), new Gender("m"),
                    new Birthday("10-12-2000"), new VolunteerPhone("92492021"),
                    new VolunteerEmail("irfan@example.com"),
                    new VolunteerAddress("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("student")),
            new Volunteer(new VolunteerName("Roy Balakrishnan"), new Gender("m"),
                    new Birthday("01-01-1984"), new VolunteerPhone("92624417"),
                    new VolunteerEmail("royb@example.com"),
                    new VolunteerAddress("Blk 45 Aljunied Street 85, #11-31"),
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
            new Record(new EventId(1), new PersonId(1), new Hour("1"), new Remark("Emcee")),
            new Record(new EventId(2), new PersonId(1), new Hour("1"), new Remark("Delivery Man"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

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
