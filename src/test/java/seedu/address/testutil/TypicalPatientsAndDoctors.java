package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPatientsAndDoctors {

    public static final Patient ALICE_PATIENT = new PatientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("egg", "sub-health").build();
    public static final Patient BENSON_PATIENT = new PatientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withRemark("Has heart disease")
            .withTags("Patient", "friends")
            .withMedicalHistory("", "having cold").build();
    public static final Patient CARL_PATIENT = new PatientBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("fish", "").build();
    public static final Patient DANIEL_PATIENT = new PatientBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withRemark("")
            .withTags("Patient", "friends")
            .withMedicalHistory("", "").build();
    public static final Patient ELLE_PATIENT = new PatientBuilder()
            .withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("", "").build();
    public static final Doctor FIONA_DOCTOR = new DoctorBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withRemark("")
            .withTags("Doctor").build();
    public static final Doctor GEORGE_DOCTOR = new DoctorBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withRemark("")
            .withTags("Doctor").build();
    public static final Doctor HELENA_DOCTOR = new DoctorBuilder()
            .withName("Helena Sophia")
            .withPhone("95264283")
            .withEmail("helena@example.com")
            .withAddress("7th street")
            .withRemark("")
            .withTags("Doctor").build();

    private TypicalPatientsAndDoctors() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBookWithPatientAndDoctor() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPatientsAndDoctors()) {
            if (person instanceof Patient) {
                ab.addPatient((Patient) person);
            } else if (person instanceof Doctor) {
                ab.addDoctor((Doctor) person);
            } else {
                ab.addPerson(person);
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPatientsAndDoctors() {
        return new ArrayList<>(Arrays.asList(ALICE_PATIENT, BENSON_PATIENT, CARL_PATIENT,
                DANIEL_PATIENT, ELLE_PATIENT, FIONA_DOCTOR, GEORGE_DOCTOR, HELENA_DOCTOR));
    }
}
