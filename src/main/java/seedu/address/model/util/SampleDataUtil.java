package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");
    public static final Remark NON_EMPTY_REMARK = new Remark("Likes dogs");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Patient(new Name("Utah Tan"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), NON_EMPTY_REMARK,
                getTagSet("Patient"), "123"),
            new Patient(new Name("Jackie Chen"), new Phone("91234232"), new Email("jackiechen@example.com"),
                    new Address("Blk 123 Clementi Street 9, #08-20"), EMPTY_REMARK,
                    getTagSet("Patient"), "123"),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            if (samplePerson instanceof Patient) {
                sampleAb.addPatient((Patient) samplePerson);
            } else if (samplePerson instanceof Doctor) {
                sampleAb.addDoctor((Doctor) samplePerson);
            } else {
                sampleAb.addPerson(samplePerson);
            }
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
