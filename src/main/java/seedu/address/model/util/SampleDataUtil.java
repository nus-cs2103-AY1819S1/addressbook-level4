package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePhoto;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new ProfilePhoto(
                    "D:\\Spencer\\CodingWork\\RepoWithSourceTree\\teamRepo\\docs\\images\\samplePicture.png"),
                getTagSet("friends"), createMarks("Finals", "63")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new ProfilePhoto(
                    "D:\\Spencer\\CodingWork\\RepoWithSourceTree\\teamRepo\\docs\\images\\samplePicture2.jpg"),
                getTagSet("colleagues", "friends"), createMarks("Finals", "70")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new ProfilePhoto(
                    "D:\\Spencer\\CodingWork\\RepoWithSourceTree\\teamRepo\\docs\\images\\samplePicture3.png"),
                getTagSet("neighbours"), createMarks("Finals", "60")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new ProfilePhoto(
                    "D:\\Spencer\\CodingWork\\RepoWithSourceTree\\teamRepo\\docs\\images\\samplePicture4.png"),
                getTagSet("family"), createMarks("Finals", "71")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new ProfilePhoto(
                    "D:\\Spencer\\CodingWork\\RepoWithSourceTree\\teamRepo\\docs\\images\\samplePicture5.png"),
                getTagSet("classmates"), createMarks("Finals", "54")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), createMarks("Finals", "61")),
            new Person(new Name("Adams Baker"), new Phone("96218580"), new Email("adamb@example.com"),
                new Address("Blk 20 Tampines Street 63, #05-60"),
                getTagSet("acquaintance"), createMarks("Finals", "43")),
            new Person(new Name("Clark Evans"), new Phone("82439807"), new Email("clarke@example.com"),
                new Address("Blk 125 Bishan Street 6, #11-12"),
                getTagSet("classmates"), createMarks("Finals", "67")),
            new Person(new Name("Thomas Tank"), new Phone("67617743"), new Email("thomast@example.com"),
                new Address("Blk 1 Tunnel Street 1, #09-04"),
                getTagSet("partner"), createMarks("Finals", "56")),
            new Person(new Name("Garfield Paw"), new Phone("86127129"), new Email("garfield@example.com"),
                new Address("Blk 54 Smith Street 12, #01-02"),
                getTagSet("catLoversSociety"), createMarks("Finals", "91")),
            new Person(new Name("Winnie Pooh"), new Phone("93225167"), new Email("winniep@example.com"),
                new Address("100 Acre Wood, #01-01"),
                getTagSet("bestFriend"), createMarks("Finals", "37")),
            new Person(new Name("Tooth Fairy"), new Phone("95583216"), new Email("toothf@example.com"),
                new Address("Blk 30 Geylang Street 29, #6-40"),
                getTagSet("fairy"), createMarks("Finals", "85")),
            new Person(new Name("Santa Claus"), new Phone("81673172"), new Email("santac@example.com"),
                new Address("Blk 30 South Pole, #02-20"),
                getTagSet("retailer"), createMarks("Finals", "77")),
            new Person(new Name("Ash Ketchum"), new Phone("90125369"), new Email("gottacatchemall@example.com"),
                new Address("Pallet Town Route 1, #01-00"),
                getTagSet("friends"), createMarks("Finals", "52")),
            new Person(new Name("Bob Ross"), new Phone("67286700"), new Email("bobross@example.com"),
                new Address("Blk 5 Punggol Avenue 10, #12-22"),
                getTagSet("painter"), createMarks("Finals", "62")),
            new Person(new Name("Chuck Norris"), new Phone("99117638"), new Email("chuckn@example.com"),
                new Address("Blk 12 Yishun Street 4, #09-12"),
                getTagSet("bodyguard"), createMarks("Finals", "32")),
            new Person(new Name("Jasmine Lily"), new Phone("96279213"), new Email("jasl@example.com"),
                new Address("Blk 1 Parliament House, #15-26"),
                getTagSet("queen"), createMarks("Finals", "74")),
            new Person(new Name("Muthu Balakrishnan"), new Phone("82372108"), new Email("muthub@example.com"),
                new Address("Blk 82 Yio Chu Kang Street 23, #09-12"),
                getTagSet("classmates"), createMarks("Finals", "59")),
            new Person(new Name("John Lim"), new Phone("679512014"), new Email("johnl@example.com"),
                new Address("Blk 11 Bartley Street 7, #07-08"),
                getTagSet("friends"), createMarks("Finals", "57")),
            new Person(new Name("Jack Frost"), new Phone("89428365"), new Email("jackf@example.com"),
                new Address("Blk 30 Kallang Street 18, #06-50"),
                getTagSet("acquaintance"), createMarks("Finals", "60"))
        };
    }

    public static Assignment[] getSampleAssignments() {
        return new Assignment[] {
            new Assignment(new AssignmentName("Finals"), new Weight("50"), new Deadline("06/12/2018"),
                new Mark("100")),
            new Assignment(new AssignmentName("Product Demo"), new Weight("15"), new Deadline("07/11/2018"),
                new Mark("40")),
            new Assignment(new AssignmentName("Midterms"), new Weight("15"), new Deadline("16/10/2018"),
                new Mark("50")),
            new Assignment(new AssignmentName("Participation"), new Weight("10"), new Deadline("29/11/2018"),
                new Mark("10")),
            new Assignment(new AssignmentName("Project Portfolio"), new Weight("10"), new Deadline("12/11/2018"),
                new Mark("40"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Assignment sampleAssignment : getSampleAssignments()) {
            sampleAb.addAssignment(sampleAssignment);
        }
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
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

    private static Map<String, Mark> createMarks(String name, String mark) {
        HashMap<String, Mark> marks = new HashMap<>();
        marks.put(name, new Mark(mark));
        return marks;
    }

}
