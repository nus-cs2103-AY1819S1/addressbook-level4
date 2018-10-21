package seedu.clinicio.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinicio.commons.util.HashUtil;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.doctor.Id;
import seedu.clinicio.model.doctor.Password;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ClinicIo} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    //@@author jjlee050
    public static List<Doctor> getSampleDoctors() {
        return new ArrayList<>(Arrays.asList(
                new Doctor(new Id(1), new Name("Adam Bell"),
                        new Password(HashUtil.hashToString("doctor1"), true)),
                new Doctor(new Id(2), new Name("Chip Dale"),
                        new Password(HashUtil.hashToString("doctor2"), true))));
    }

    public static ReadOnlyClinicIo getSampleClinicIo() {
        ClinicIo sampleClinicIo = new ClinicIo();
        for (Person samplePerson : getSamplePersons()) {
            sampleClinicIo.addPerson(samplePerson);
        }
        //@@author jjlee050
        for (Doctor sampleDoctor : getSampleDoctors()) {
            sampleClinicIo.addDoctor(sampleDoctor);
        }
        return sampleClinicIo;
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
