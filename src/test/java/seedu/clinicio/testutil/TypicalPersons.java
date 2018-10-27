package seedu.clinicio.testutil;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ID_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ID_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // Manually added (Patient)
    public static final Patient ALICE_AS_PATIENT = PatientBuilder.buildFromPerson(ALICE)
            .build();
    public static final Patient BENSON_AS_PATIENT = PatientBuilder.buildFromPerson(BENSON)
            .build();
    public static final Patient CARL_AS_PATIENT = PatientBuilder.buildFromPerson(CARL)
            .build();
    public static final Patient DANIEL_AS_PATIENT = PatientBuilder.buildFromPerson(DANIEL)
            .build();
    public static final Patient ELLE_AS_PATIENT = PatientBuilder.buildFromPerson(ELLE)
            .build();
    public static final Patient FIONA_AS_PATIENT = PatientBuilder.buildFromPerson(FIONA)
            .build();
    public static final Patient GEORGE_AS_PATIENT = PatientBuilder.buildFromPerson(GEORGE)
            .build();
    public static final Patient AMY_AS_PATIENT = PatientBuilder.buildFromPerson(AMY)
            .build();

    // Manually added (Doctor)
    public static final Doctor ADAM = new DoctorBuilder().withId(VALID_ID_ADAM).withName(VALID_NAME_ADAM)
            .withPassword(VALID_HASH_PASSWORD_ADAM, true).build();
    public static final Doctor BEN = new DoctorBuilder().withId(VALID_ID_BEN).withName(VALID_NAME_BEN)
            .withPassword(VALID_HASH_PASSWORD_BEN, true).build();

    // Appointments
    public static final Appointment AMY_APPT = new AppointmentBuilder().withDate(2, 10, 2018)
            .withTime(13, 00).withPatient(AMY_AS_PATIENT).withDoctor(ADAM).build();
    public static final Appointment BENSON_APPT = new AppointmentBuilder().withDate(3, 10, 2018)
            .withTime(17, 45).withPatient(BENSON_AS_PATIENT).withDoctor(ADAM).build();
    public static final Appointment CARL_APPT = new AppointmentBuilder().withDate(2, 10, 2018)
            .withTime(18, 00).withPatient(CARL_AS_PATIENT).withDoctor(ADAM).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code ClinicIo} with all the typical persons.
     */
    public static ClinicIo getTypicalClinicIo() {
        ClinicIo clinicIo = new ClinicIo();
        for (Person person : getTypicalPersons()) {
            clinicIo.addPerson(person);
        }
        for (Doctor doctor : getTypicalDoctors()) {
            clinicIo.addDoctor(doctor);
        }
        /*
        for (Appointment appointment : getTypicalAppointments()) {
            clinicIo.addAppointment(appointment);
        }
        */
        return clinicIo;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(ADAM, BEN));
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(AMY_APPT, BENSON_APPT, CARL_APPT));
    }
}
