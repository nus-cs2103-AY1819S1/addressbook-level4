package seedu.address.testutil;

import seedu.address.model.ClinicIo;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ClinicIo ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ClinicIo clinicIo;

    public AddressBookBuilder() {
        clinicIo = new ClinicIo();
    }

    public AddressBookBuilder(ClinicIo clinicIo) {
        this.clinicIo = clinicIo;
    }

    /**
     * Adds a new {@code Person} to the {@code ClinicIo} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        clinicIo.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Doctor} to the {@code ClinicIo} that we are building.
     */
    public AddressBookBuilder withDoctor(Doctor doctor) {
        clinicIo.addDoctor(doctor);
        return this;
    }

    public ClinicIo build() {
        return clinicIo;
    }
}
