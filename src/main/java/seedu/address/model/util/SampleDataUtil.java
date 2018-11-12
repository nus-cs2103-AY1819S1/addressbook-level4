package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ArchiveList;
import seedu.address.model.AssignmentList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyArchiveList;
import seedu.address.model.ReadOnlyAssignmentList;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Salary("10000"), new Username("Alex Yeoh"),
                    new Password("Pa55w0rd"), getProjectSet("OASIS")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Salary("10000"),
                    new Username("Bernice Yu"), new Password("Pa55w0rd"), getProjectSet("ANLGENE")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Salary("10000"),
                    new Username("Charlotte Oliveiro"), new Password("Pa55w0rd"), getProjectSet("BEARY")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Salary("10000"),
                    new Username("David Li"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Salary("10000"),
                    new Username("Irfan Ibrahim"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Salary("10000"),
                    new Username("Roy Balakrishnan"), new Password("Pa55w0rd"), getProjectSet("OASIS"))
        };
    }

    public static Person[] getSampleArchive() {
        return new Person[] {
            new Person(new Name("Bernard Chong"), new Phone("85738293"), new Email("BernardC@example.com"),
                        new Address("Blk 50 Punggol Street, #06-40"), new Salary("1000"), new Username("Bernard Chong"),
                        new Password("Pa55w0rd"), getProjectSet("OASIS")),
            new Person(new Name("Terrence Tan"), new Phone("88272758"), new Email("TerrenceT@example.com"),
                new Address("Blk 30 Lorong 3 Lorong Asrama, #07-18"), new Salary("6000"),
                new Username("Terrence Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Jasmine Chong"), new Phone("88272758"), new Email("JasmineC@example.com"),
                new Address("19, Jln Kilang Barat #02-01, AceTech Centre Singapore 159361, Singapore"),
                    new Salary("6000"),
                new Username("Jasmine Chong"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Eugene Foo"), new Phone("88272758"), new Email("EugeneF@example.com"),
                new Address("86, International Rd Singapore 629176, Singapore"), new Salary("6000"),
                new Username("Eugene Foo"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Leonard Tay"), new Phone("88272758"), new Email("LeonardT@example.com"),
                new Address("Royal Tanglin Golf Course 130E Minden Road Singapore 248819, Singapore"),
                    new Salary("6000"),
                new Username("LeonardTay"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Andy Lim"), new Phone("88272758"), new Email("AndyL@example.com"),
                new Address("Block 77, Marine Drive, ,06-48 440077, Singapore"), new Salary("6000"),
                new Username("Andy Lim"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Andy Foo"), new Phone("88272758"), new Email("AndyF@example.com"),
                new Address("9 Pandan Road, 609257, Singapore"), new Salary("6000"),
                new Username("Andy Foo"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Jesper Poh"), new Phone("88272758"), new Email("JesperP@example.com"),
                new Address("15 Tuas Avenue 10 Jurong Singapore 639139, Singapore"), new Salary("6000"),
                new Username("Jesper Poh"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Raymond Scott"), new Phone("88272758"), new Email("RaymondS@example.com"),
                new Address("3 Killiney Rd #01-01, 239519, Singapore"), new Salary("6000"),
                new Username("Raymond Scott"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Bob Tan"), new Phone("88272758"), new Email("BobT@example.com"),
                new Address("151 Chin Swee Road #14-11 Manhattan House, 169876, Singapore"), new Salary("6000"),
                new Username("Bob Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Ruven"), new Phone("88272758"), new Email("Ruven@example.com"),
                new Address("2 Geylang East Avenue 2 #04-109, 389754, Singapore"), new Salary("6000"),
                new Username("Ruven"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Jeffrey Toh"), new Phone("88272758"), new Email("JeffreyT@example.com"),
                new Address("38B Pagoda Street, 059197, Singapore"), new Salary("6000"),
                new Username("Jeffrey Toh"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Leon Lim"), new Phone("88272758"), new Email("LeonL@example.com"),
                new Address("1 Harbourfront Centre Walk 02-143 Vivocity, 098585, Singapore"), new Salary("6000"),
                new Username("Leon Lim"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Florence Tan"), new Phone("88272758"), new Email("FlorenceT@example.com"),
                new Address("60 Admiralty Road West #02-01 PROJECT OFFICE NO 8, 759947, Singapore"), new Salary("6000"),
                new Username("Florence Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Abigail Tan"), new Phone("88272758"), new Email("AbigailT@example.com"),
                new Address("6b boon tiong road 10-55, 165006, Singapore"), new Salary("6000"),
                new Username("Abigail Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Melanie Tan"), new Phone("88272758"), new Email("Melanie@example.com"),
                new Address("Alexandra Technopark 438A Alexandra Road #B1-03 119967, Singapore"), new Salary("6000"),
                new Username("Melanie Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Cindy Tan"), new Phone("88272758"), new Email("CindyT@example.com"),
                new Address("52 Kallang Bahru ,07-21 339335, Singapore"), new Salary("6000"),
                new Username("Cindy Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Kiera Tan"), new Phone("88272758"), new Email("Kiera@example.com"),
                new Address("145 Woodlands Industrial Park E5, 757509 Singapore, Singapore"), new Salary("6000"),
                new Username("Kiera Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Olivia Tan"), new Phone("88272758"), new Email("OliviaT@example.com"),
                new Address("30 Jalan Buroh , zipcode: Singapore619486, Singapore"), new Salary("6000"),
                new Username("Olivia Tan"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
            new Person(new Name("Tori Kelly"), new Phone("88272758"), new Email("ToriK@example.com"),
                new Address("27 Wilby Road #B1-00 Singapore 276302, Singapore"), new Salary("6000"),
                new Username("Tori Kelly"), new Password("Pa55w0rd"), getProjectSet("FALCON")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static Assignment[] getSampleAssignments() {
        return new Assignment[] {
            new Assignment(new ProjectName("OASIS"), new Name("Amy Bee"),
                    new Description("Project Management System.")),
            new Assignment(new ProjectName("FALCON"), new Name("Bob Choo"),
                    new Description("Home Security System.")),
            new Assignment(new ProjectName("SCRUB DADDY"), new Name("Billy Bo"),
                    new Description("World's most friendly scrubber.")),
            new Assignment(new ProjectName("BEARY"), new Name("John Ale"),
                    new Description("Creating innovative teddy bear that is not just a bear")),
            new Assignment(new ProjectName("SHARK TANK"), new Name("Marcus Ban"),
                    new Description("Development for world's largest aquarium.")),
            new Assignment(new ProjectName("ANLGENE"), new Name("Terry Cook"),
                    new Description("Creating the best water bottle that is bullet proof and featherlight")),
            new Assignment(new ProjectName("COLE HAAN"), new Name("Han Ming"),
                    new Description("The leading company in trendy wallet."))
        };
    }

    public static ReadOnlyAssignmentList getSampleAssignmentList() {
        AssignmentList sampleAssignmentList = new AssignmentList();
        for (Assignment sampleAssignment : getSampleAssignments()) {
            sampleAssignmentList.addAssignment(sampleAssignment);
        }
        return sampleAssignmentList;
    }

    public static ReadOnlyArchiveList getSampleArchiveList() {
        ArchiveList sampleAl = new ArchiveList();
        for (Person samplePerson : getSampleArchive()) {
            sampleAl.addPerson(samplePerson);
        }
        return sampleAl;
    }

    /**
     * Returns a project set containing the list of strings given.
     */
    public static Set<Project> getProjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Project::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a date list containing the list of dates given.
     */
    public static List<LocalDate> getDateList(LocalDate... dates) {
        return Arrays.stream(dates)
                .collect(Collectors.toList());
    }
}
