package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new Faculty("FOS")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), new Faculty("FASS")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new Faculty("SOC")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new Faculty(("SOC"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new Faculty("FOE")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new Faculty("SDE"))
        };
    }

    public static Event[] getSampleEvents() {
        Person alex = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new Faculty("FOS"));
        Tag meetingTag = new Tag("Meeting");

        return new Event[] {
            new Event(new EventName("Meeting"), new EventDescription("test events description"),
                new EventDate("2018-04-01"), new EventTime("1400"), new EventTime("1500"),
                    new EventAddress("3 Kent Ridge Drive"), new HashSet<>(Arrays.asList(alex)),
                    new HashSet<>(Arrays.asList(meetingTag)))
        };
    }

    public static Tag[] getSampleEventTags() {
        Tag appointmentTag = new Tag("Appointment");
        Tag meetingTag = new Tag("Meeting");
        return new Tag[] {appointmentTag, meetingTag};
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Tag sampleEventTag : getSampleEventTags()) {
            sampleAb.addEventTag(sampleEventTag);
        }

        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
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

    /**
     * Returns a person set containing the list of persons given.
     */
    public static Set<Person> getEventContactSet(Person... persons) {
        return Arrays.stream(persons)
                .collect(Collectors.toSet());
    }

}
