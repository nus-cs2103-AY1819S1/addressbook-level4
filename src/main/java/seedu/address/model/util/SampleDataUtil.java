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
                    new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Salary("10000"),
                    new Username("Bernice Yu"), new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Salary("10000"),
                    new Username("Charlotte Oliveiro"), new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Salary("10000"),
                    new Username("David Li"), new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Salary("10000"),
                    new Username("Irfan Ibrahim"), new Password("Pa55w0rd"), getProjectSet("falcon")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Salary("10000"),
                    new Username("Roy Balakrishnan"), new Password("Pa55w0rd"), getProjectSet("oasis"))
        };
    }

    public static Person[] getSampleArchive() {
        return new Person[] {
            new Person(new Name("Bernard Chong"), new Phone("85738293"), new Email("BernardC@example.com"),
                        new Address("Blk 50 Punggol Street, #06-40"), new Salary("1000"), new Username("Bernard Chong"),
                        new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Terrence Tan"), new Phone("88272758"), new Email("TerrenceT@example.com"),
                        new Address("Blk 30 Lorong 3 Lorong Asrama, #07-18"), new Salary("6000"),
                        new Username("Terrence Tan"), new Password("Pa55w0rd"), getProjectSet("friends")),
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
