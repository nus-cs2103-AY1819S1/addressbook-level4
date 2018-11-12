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
            new Volunteer(new Name("Zack ZeroRecords"), new VolunteerId("S0000000A"), new Gender("m"),
                    new Birthday("02-10-1993"), new Phone("97438807"),
                    new Email("zackzero@example.com"),
                    new Address("Blk 32 Sembawang Avenue 3, #09-40"),
                    getTagSet("driver")),
            new Volunteer(new Name("Natalie NoRecords"), new VolunteerId("S0000001A"), new Gender("f"),
                    new Birthday("12-03-1993"), new Phone("91438807"),
                    new Email("natalienoerec@example.com"),
                    new Address("Blk 32 Woodlands Avenue 6, #01-32"),
                    getTagSet("speaker")),
            new Volunteer(new Name("Samuel Same"), new VolunteerId("S0000002A"), new Gender("m"),
                    new Birthday("02-05-1982"), new Phone("91438807"),
                    new Email("samuelsame@example.com"),
                    new Address("Blk 323 Khatib Avenue 8, #01-32"),
                    getTagSet("student")),
            new Volunteer(new Name("Samuel Same"), new VolunteerId("S0000003A"), new Gender("m"),
                    new Birthday("02-05-1980"), new Phone("92438807"),
                    new Email("secondsamuel@example.com"),
                    new Address("Blk 234 Yio Chu Kang Avenue 8, #04-32"),
                    getTagSet("student")),
            new Volunteer(new Name("Alex Yeoh"), new VolunteerId("S9647345A"), new Gender("m"),
                    new Birthday("02-10-1996"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("student")),
            new Volunteer(new Name("Bernice Yu"), new VolunteerId("S9518076J"), new Gender("f"),
                    new Birthday("15-03-1995"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("student", "driver")),
            new Volunteer(new Name("Charlotte Oliveiro"), new VolunteerId("F9273073E"), new Gender("f"),
                    new Birthday("17-09-1992"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("adult")),
            new Volunteer(new Name("David Li"), new VolunteerId("G0213905C"), new Gender("m"),
                    new Birthday("28-04-2002"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("student")),
            new Volunteer(new Name("Irfan Ibrahim"), new VolunteerId("T0069459I"), new Gender("m"),
                    new Birthday("10-12-2000"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("student")),
            new Volunteer(new Name("Roy Balakrishnan"), new VolunteerId("F8457737E"), new Gender("m"),
                    new Birthday("01-01-1984"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("adult")),
            new Volunteer(new Name("Zack Goh"), new VolunteerId("S1000000A"), new Gender("m"),
                    new Birthday("12-12-2001"), new Phone("91438807"),
                    new Email("zackgoh@example.com"),
                    new Address("Blk 321 Bishan Street 2, #09-40"),
                    getTagSet("driver")),
            new Volunteer(new Name("Natalia Tan"), new VolunteerId("S2000001A"), new Gender("f"),
                    new Birthday("03-04-2001"), new Phone("91538807"),
                    new Email("natialiatan@example.com"),
                    new Address("Blk 418 Orchard Link 12, #31-1318"),
                    getTagSet("speaker")),
            new Volunteer(new Name("Samuel Soh"), new VolunteerId("S3000002A"), new Gender("m"),
                    new Birthday("02-05-1995"), new Phone("91428807"),
                    new Email("samuelsoh@example.com"),
                    new Address("Blk 12 Upper Thomson Avenue 41, #04-3211"),
                    getTagSet("student")),
            new Volunteer(new Name("Samuel Seah"), new VolunteerId("S4000003A"), new Gender("m"),
                    new Birthday("02-05-1960"), new Phone("92429807"),
                    new Email("seahsamuel@example.com"),
                    new Address("Blk 123 Admiralty Avenue 10, #06-3111"),
                    getTagSet("student")),
            new Volunteer(new Name("Alexander Yeoh"), new VolunteerId("S9747345A"), new Gender("m"),
                    new Birthday("02-10-1997"), new Phone("87438807"),
                    new Email("alexanderyeoh@example.com"),
                    new Address("Blk 33 Geylang Street 29, #08-40"),
                    getTagSet("student")),
            new Volunteer(new Name("Bella Yee"), new VolunteerId("S9918076J"), new Gender("f"),
                    new Birthday("15-03-1999"), new Phone("99272111"),
                    new Email("bellayee@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #09-18"),
                    getTagSet("student", "driver")),
            new Volunteer(new Name("Charlene Neo"), new VolunteerId("S9873073E"), new Gender("f"),
                    new Birthday("17-09-1998"), new Phone("93210282"),
                    new Email("charleneneo@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("adult")),
            new Volunteer(new Name("David Lim"), new VolunteerId("G0212305C"), new Gender("m"),
                    new Birthday("28-04-2002"), new Phone("91041282"),
                    new Email("limdavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 21, #16-43"),
                    getTagSet("student")),
            new Volunteer(new Name("Ahmad Ibrahim"), new VolunteerId("T0069463I"), new Gender("m"),
                    new Birthday("10-12-2000"), new Phone("92123021"),
                    new Email("ahmadi@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("student")),
            new Volunteer(new Name("Ronald Vivek"), new VolunteerId("S8457737E"), new Gender("m"),
                    new Birthday("01-01-1984"), new Phone("92847417"),
                    new Email("ronav@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #12-31"),
                    getTagSet("adult"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[]{
            new Event(new seedu.address.model.event.Name("Christmas Funfair 2018"),
                    new Location("29/31 Dalhousie Lane, Singapore 209691"), new Date("20-12-2018"),
                    new Date("22-12-2018"), new Time("10:30"), new Time("15:30"),
                    new Description("Donation drive for blood."),
                    getTagSet("Family")),
            new Event(new seedu.address.model.event.Name("Volunteer Recruitment Day II"),
                    new Location("Queenstown Community Center, Singapore 149732"), new Date("05-12-2018"),
                    new Date("05-12-2018"), new Time("11:30"), new Time("17:30"),
                    new Description("Recruiting for more volunteers."),
                    getTagSet("Recruitment")),
            new Event(new seedu.address.model.event.Name("Blood Donation Drive 2018"),
                    new Location("750E Chai Chee Road"), new Date("28-11-2018"), new Date("04-12-2018"),
                    new Time("10:30"), new Time("16:30"), new Description("Donation drive for blood."),
                    getTagSet("Public", "Donation")),
            new Event(new seedu.address.model.event.Name("Youth Humanitarian Challenge"),
                    new Location("29 Havelock Road"), new Date("15-11-2018"), new Date("17-11-2018"),
                    new Time("10:00"), new Time("18:00"), new Description("To engage youths in humanitarianism."),
                    getTagSet("Competition")),
            new Event(new seedu.address.model.event.Name("Charity Concert 2018"),
                    new Location("1 Zubir Said Drive, Singapore 227968"), new Date("13-11-2018"),
                    new Date("16-11-2018"), new Time("20:00"), new Time("22:00"),
                    new Description("Song and dance drama with light-hearted musical performance"),
                    getTagSet("Concert")),
            new Event(new seedu.address.model.event.Name("Volunteer Orientation"),
                    new Location("1 Lor 2 Toa Payoh, Singapore 319637"), new Date("10-11-2018"),
                    new Date("10-11-2018"), new Time("19:30"), new Time("21:00"),
                    new Description("Introduction for volunteers about programmes and staff members."),
                    getTagSet("Internal")),
            new Event(new seedu.address.model.event.Name("Recycling Drive"),
                    new Location("241 Serangoon Ave 3, Singapore 550241"), new Date("20-09-2018"),
                    new Date("21-09-2018"), new Time("09:30"), new Time("12:00"),
                    new Description("Includes a sharing session as well as a hands-on recycling activity together "
                            + "with the volunteers. Learn how to segregate different kinds of items."),
                    getTagSet("Eco")),
            new Event(new seedu.address.model.event.Name("Anniversary Charity Gala Dinner"),
                    new Location("Shangri-La Hotel, Singapore"), new Date("15-07-2018"),
                    new Date("15-07-2018"), new Time("18:00"), new Time("20:00"),
                    new Description("Commemorating an important organisation anniversary."),
                    getTagSet("Anniversary")),
            new Event(new seedu.address.model.event.Name("Free Code Camp 2018"),
                    new Location("Queenstown Community Center, Singapore 149732"), new Date("08-04-2018"),
                    new Date("12-04-2018"), new Time("13:00"), new Time("15:00"),
                    new Description("Teaching kids how to code."),
                    getTagSet("Enrichment", "Coding")),
            new Event(new seedu.address.model.event.Name("Charity Walk 2018"),
                    new Location("Millenia Walk"), new Date("13-02-2018"),
                    new Date("13-02-2018"), new Time("08:00"), new Time("11:00"),
                    new Description("Walking for a good cause."),
                    getTagSet("Walk")),
            new Event(new seedu.address.model.event.Name("Eldercare Programme"),
                    new Location("86 East Coast Road, Singapore 428788"), new Date("23-01-2018"),
                    new Date("24-01-2018"), new Time("08:00"), new Time("18:00"),
                    new Description("Taking care of the elderly and helping them with their problems."),
                    getTagSet("Caretaking")),
            new Event(new seedu.address.model.event.Name("Christmas Funfair 2017"),
                    new Location("29/31 Dalhousie Lane, Singapore 209691"), new Date("20-12-2017"),
                    new Date("22-12-2017"), new Time("10:30"), new Time("15:30"),
                    new Description("Donation drive for blood."),
                    getTagSet("Family")),
            new Event(new seedu.address.model.event.Name("Volunteer Recruitment Day I"),
                    new Location("Queenstown Community Center, Singapore 149732"), new Date("05-12-2017"),
                    new Date("05-12-2017"), new Time("11:30"), new Time("17:30"),
                    new Description("Recruiting for more volunteers."),
                    getTagSet("Recruitment")),
            new Event(new seedu.address.model.event.Name("Blood Donation Drive 2017"),
                    new Location("750E Chai Chee Road"), new Date("28-11-2017"), new Date("04-12-2017"),
                    new Time("10:30"), new Time("16:30"), new Description("Donation drive for blood."),
                    getTagSet("Public", "Donation")),
            new Event(new seedu.address.model.event.Name("Youth Humanitarian Leadership Camp"),
                    new Location("29 Havelock Road"), new Date("15-11-2017"), new Date("17-11-2017"),
                    new Time("10:00"), new Time("18:00"),
                    new Description("To teach youths about leadership and humanitarianism."),
                    getTagSet("Camp")),
            new Event(new seedu.address.model.event.Name("Charity Concert 2017"),
                    new Location("1 Zubir Said Drive, Singapore 227968"), new Date("13-11-2017"),
                    new Date("16-11-2017"), new Time("20:00"), new Time("22:00"),
                    new Description("Song and dance drama with light-hearted musical performance"),
                    getTagSet("Concert")),
            new Event(new seedu.address.model.event.Name("Free Code Camp 2017"),
                    new Location("Queenstown Community Center, Singapore 149732"), new Date("08-04-2017"),
                    new Date("12-04-2017"), new Time("13:00"), new Time("15:00"),
                    new Description("Teaching kids how to code."),
                    getTagSet("Enrichment", "Coding")),
            new Event(new seedu.address.model.event.Name("Charity Walk 2017"),
                    new Location("Millenia Walk"), new Date("13-02-2017"),
                    new Date("13-02-2017"), new Time("08:00"), new Time("11:00"),
                    new Description("Walking for a good cause."),
                    getTagSet("Walk")),
            new Event(new seedu.address.model.event.Name("Christmas Funfair 2016"),
                    new Location("29/31 Dalhousie Lane, Singapore 209691"), new Date("20-12-2016"),
                    new Date("22-12-2016"), new Time("10:30"), new Time("15:30"),
                    new Description("Donation drive for blood."),
                    getTagSet("Family")),
            new Event(new seedu.address.model.event.Name("Blood Donation Drive 2016"),
                    new Location("750E Chai Chee Road"), new Date("28-11-2016"), new Date("04-12-2016"),
                    new Time("10:30"), new Time("16:30"), new Description("Donation drive for blood."),
                    getTagSet("Public", "Donation")),
        };
    }

    public static Record[] getSampleRecord() {
        return new Record[]{
            new Record(new EventId(1), new VolunteerId("S0000000A"), new Hour("0"), new Remark("Costume Maker")),
            new Record(new EventId(1), new VolunteerId("S0000002A"), new Hour("3"), new Remark("Food")),
            new Record(new EventId(1), new VolunteerId("S0000003A"), new Hour("2"), new Remark("Dancer")),
            new Record(new EventId(1), new VolunteerId("S9647345A"), new Hour("3"), new Remark("Emcee")),
            new Record(new EventId(1), new VolunteerId("S8457737E"), new Hour("2"), new Remark("Coordinator")),
            new Record(new EventId(2), new VolunteerId("S9918076J"), new Hour("1"), new Remark("Emcee")),
            new Record(new EventId(2), new VolunteerId("S4000003A"), new Hour("1"), new Remark("Costume Maker")),
            new Record(new EventId(2), new VolunteerId("S9518076J"), new Hour("1"), new Remark("Delivery Man")),
            new Record(new EventId(3), new VolunteerId("S9518076J"), new Hour("1"), new Remark("Delivery Man")),
            new Record(new EventId(3), new VolunteerId("S0000002A"), new Hour("2"), new Remark("Coffee Boy")),
            new Record(new EventId(4), new VolunteerId("S9647345A"), new Hour("0"), new Remark("Costume Maker")),
            new Record(new EventId(5), new VolunteerId("S9518076J"), new Hour("3"), new Remark("Food")),
            new Record(new EventId(6), new VolunteerId("F9273073E"), new Hour("2"), new Remark("Dancer")),
            new Record(new EventId(7), new VolunteerId("G0213905C"), new Hour("3"), new Remark("Emcee")),
            new Record(new EventId(8), new VolunteerId("T0069459I"), new Hour("2"), new Remark("Coordinator")),
            new Record(new EventId(9), new VolunteerId("S9918076J"), new Hour("1"), new Remark("Emcee")),
            new Record(new EventId(10), new VolunteerId("S8457737E"), new Hour("1"), new Remark("Costume Maker")),
            new Record(new EventId(11), new VolunteerId("G0212305C"), new Hour("1"), new Remark("Delivery Man")),
            new Record(new EventId(12), new VolunteerId("S9747345A"), new Hour("1"), new Remark("Delivery Man")),
            new Record(new EventId(13), new VolunteerId("S3000002A"), new Hour("2"), new Remark("Coffee Boy")),
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
