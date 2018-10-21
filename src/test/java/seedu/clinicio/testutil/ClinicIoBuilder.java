package seedu.clinicio.testutil;

import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.person.Person;

/**
 * A utility class to help with building ClinicIO objects.
 * Example usage: <br>
 *     {@code ClinicIo clinicIo = new ClinicIoBuilder().withPerson("John", "Doe").build();}
 */
public class ClinicIoBuilder {

    private ClinicIo clinicIo;

    public ClinicIoBuilder() {
        clinicIo = new ClinicIo();
    }

    public ClinicIoBuilder(ClinicIo clinicIo) {
        this.clinicIo = clinicIo;
    }

    /**
     * Adds a new {@code Person} to the {@code ClinicIo} that we are building.
     */
    public ClinicIoBuilder withPerson(Person person) {
        clinicIo.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Doctor} to the {@code ClinicIo} that we are building.
     */
    public ClinicIoBuilder withDoctor(Doctor doctor) {
        clinicIo.addDoctor(doctor);
        return this;
    }

    public ClinicIo build() {
        return clinicIo;
    }
}
