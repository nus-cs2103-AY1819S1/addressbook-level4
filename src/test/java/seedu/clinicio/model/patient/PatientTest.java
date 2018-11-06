package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.AMY;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.AMY_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BENSON_APPT;
import static seedu.clinicio.testutil.TypicalPersons.BENSON_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.DANIEL_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.ELLE_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.FIONA_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.GEORGE_AS_PATIENT;

import org.junit.Test;

import seedu.clinicio.testutil.PatientBuilder;

public class PatientTest {
    @Test
    public void isSamePatient() {

        // same name, different preferred staff -> returns false
        Patient editedAliceAsPatientWithPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        Patient editedAliceAsPatientWithAnotherPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(BEN).build();
        assertFalse(editedAliceAsPatientWithPreferredDoctor
                .isSamePatient(editedAliceAsPatientWithAnotherPreferredDoctor));

        // same name, one with preferred staff, one without -> returns false
        editedAliceAsPatientWithPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        Patient editedAliceWithoutPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT).build();
        assertFalse(editedAliceAsPatientWithPreferredDoctor.isSamePatient(editedAliceWithoutPreferredDoctor));

        // same name, same preferred staff, different attributes -> returns true
        Patient editedAliceAsPatientWithPreferredDoctorAndBobAddress = (Patient) new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        Patient editedAliceAsPatientWithPreferredDoctorAndAliceAddress = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        assertTrue(editedAliceAsPatientWithPreferredDoctorAndBobAddress
                .isSamePatient(editedAliceAsPatientWithPreferredDoctorAndAliceAddress));

        // same name, different appointment -> returns false
        Patient editedAmyAsPatientWithAppointment = new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT).build();
        Patient editedAmyAsPatientWithAnotherAppointment = new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(BENSON_APPT).build();
        assertFalse(editedAmyAsPatientWithAppointment.isSamePatient(editedAmyAsPatientWithAnotherAppointment));

        // same name, one with appointment, one without -> returns false
        editedAmyAsPatientWithAppointment = new PatientBuilder(AMY_AS_PATIENT).withAppointment(AMY_APPT).build();
        Patient editedAmyAsPatientWithoutAppointment = PatientBuilder.buildFromPerson(AMY).build();
        assertFalse(editedAmyAsPatientWithAppointment.isSamePatient(editedAmyAsPatientWithoutAppointment));

        // same name, same appointment, different attributes -> returns true
        Patient editedAmyAsPatientWithAppointmentAndAmyAddress = (Patient) new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        Patient editedAmyAsPatientWithAppointmentAndBobAddress = (Patient) new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(editedAmyAsPatientWithAppointmentAndAmyAddress
                .isSamePatient(editedAmyAsPatientWithAppointmentAndBobAddress));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient aliceAsPatientCopy = new PatientBuilder(ALICE_AS_PATIENT).build();
        assertTrue(ALICE_AS_PATIENT.equals(aliceAsPatientCopy));

        // same object -> returns true
        assertTrue(ALICE_AS_PATIENT.equals(ALICE_AS_PATIENT));

        // different type -> returns false
        assertFalse(ALICE_AS_PATIENT.equals(5));

        // different person -> returns false
        assertFalse(ALICE_AS_PATIENT.equals(BENSON_AS_PATIENT));
        assertFalse(ALICE_AS_PATIENT.equals(CARL_AS_PATIENT));
        assertFalse(ALICE_AS_PATIENT.equals(DANIEL_AS_PATIENT));
        assertFalse(ALICE_AS_PATIENT.equals(ELLE_AS_PATIENT));
        assertFalse(ALICE_AS_PATIENT.equals(FIONA_AS_PATIENT));
        assertFalse(ALICE_AS_PATIENT.equals(GEORGE_AS_PATIENT));

        // different name -> returns false
        Patient editedAliceAsPatient = (Patient) new PatientBuilder(ALICE_AS_PATIENT).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_AS_PATIENT.equals(editedAliceAsPatient));

        // different phone -> returns false
        editedAliceAsPatient = (Patient) new PatientBuilder(ALICE_AS_PATIENT).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_AS_PATIENT.equals(editedAliceAsPatient));

        // different email -> returns false
        editedAliceAsPatient = (Patient) new PatientBuilder(ALICE_AS_PATIENT).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE_AS_PATIENT.equals(editedAliceAsPatient));

        // different address -> returns false
        editedAliceAsPatient = (Patient) new PatientBuilder(ALICE_AS_PATIENT).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE_AS_PATIENT.equals(editedAliceAsPatient));

        // different tags -> returns false
        editedAliceAsPatient = (Patient) new PatientBuilder(ALICE_AS_PATIENT).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE_AS_PATIENT.equals(editedAliceAsPatient));

        // different preffered staff -> returns false
        Patient editedAliceAsPatientWithPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        Patient editedAliceAsPatientWithAnotherPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(BEN).build();
        assertFalse(editedAliceAsPatientWithPreferredDoctor.equals(editedAliceAsPatientWithAnotherPreferredDoctor));

        // different appointment -> returns false
        Patient editedAmyAsPatientWithAppointment = new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT).build();
        Patient editedAmyAsPatientWithAnotherAppointment = new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(BENSON_APPT).build();
        assertFalse(editedAmyAsPatientWithAppointment.equals(editedAmyAsPatientWithAnotherAppointment));
    }

    @Test
    public void hasPreferredDoctor() {
        // patient without preferred staff -> returns false
        assertFalse(ALICE_AS_PATIENT.hasPreferredDoctor());

        // patient with a preferred staff -> returns true
        Patient aliceAsPatientWithPreferredDoctor = ALICE_AS_PATIENT;
        aliceAsPatientWithPreferredDoctor.setPreferredDoctor(BEN);
        assertTrue(aliceAsPatientWithPreferredDoctor.hasPreferredDoctor());
    }

    @Test
    public void hasAppointment() {
        // patient without an appointment -> returns false
        assertFalse(ALICE_AS_PATIENT.hasAppointment());

        // patient with an appointment -> returns true
        Patient amyAsPatientWithAppointment = AMY_AS_PATIENT;
        amyAsPatientWithAppointment.setAppointment(AMY_APPT);
        assertTrue(amyAsPatientWithAppointment.hasAppointment());
    }
}
