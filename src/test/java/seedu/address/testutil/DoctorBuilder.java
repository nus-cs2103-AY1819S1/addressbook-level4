package seedu.address.testutil;

import java.util.HashSet;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Id;
import seedu.address.model.doctor.Password;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

//@@author jjlee050
/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder extends PersonBuilder {

    public static final int DEFAULT_ID = 1;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PASSWORD = "alicepaul";

    private Id id;
    private Password password;

    public DoctorBuilder() {
        super(new Person(new Name(DEFAULT_NAME), new Phone(""), new Email(""), new Address(""), new HashSet<>()));
        id = new Id(DEFAULT_ID);
        password = new Password(DEFAULT_PASSWORD);
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code doctorToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        super(new Person(doctorToCopy.getName(), new Phone(""), new Email(""), new Address(""), new HashSet<>()));
        id = doctorToCopy.getId();
        password = doctorToCopy.getPassword();
    }

    /**
     * Sets the {@code Id} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withId(int id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    public Doctor build() {
        return new Doctor(id, name, password);
    }

}
