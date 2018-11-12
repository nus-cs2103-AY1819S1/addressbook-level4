package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Time;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Education("Secondary 4"),
                    getGradesHashMap("Y1819S1_Mid 100"), getTimeList("mon 1000 1200"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Education("Primary 3"),
                    getGradesHashMap("Y1819S1_Mid 95"), getTimeList("mon 1300 1500"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Education("Primary 6"),
                    getGradesHashMap("Y1819S1_Mid 68"), getTimeList("tue 1200 1400"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Education("Secondary 1"),
                    getGradesHashMap("Y1819S1_Mid 98"), getTimeList("tue 1600 1800"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Education("Secondary 2"),
                    getGradesHashMap("Y1819S1_Mid 97"), getTimeList("wed 1300 1500"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Education("Secondary 3"),
                    getGradesHashMap("Y1819S1_Mid 99"), getTimeList("wed 1600 1800"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a HashMap containing the map of grades given.
     */
    public static HashMap<String, Grades> getGradesHashMap(String... strings) {
        HashMap<String, Grades> gradesHashMap = new HashMap<>();
        for (String s : strings) {
            String[] splitS = s.trim().split("\\s+");
            //assert the s is valid
            gradesHashMap.put(splitS[0], new Grades(splitS[1]));
        }
        return gradesHashMap;
    }

    /**
     * Returns an ArrayList containing the list of timings given.
     */
    public static ArrayList<Time> getTimeList(String... strings) {
        ArrayList<Time> timeList = new ArrayList<>();
        for (String s : strings) {
            timeList.add(new Time(s));
        }
        return timeList;
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
