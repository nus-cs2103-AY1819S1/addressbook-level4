package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.AMY_APPT;
import static seedu.address.testutil.TypicalPersons.AMY_AS_PATIENT;
import static seedu.address.testutil.TypicalPersons.BEN;
import static seedu.address.testutil.TypicalPersons.BENSON_APPT;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PersonBuilder;


public class PatientTest {
    @Test
    public void isSamePerson() {

        // same name, different preferred doctor -> returns false
        Patient editedAliceWithPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        Patient editedAliceWithAnotherPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(BEN).build();
        assertFalse(editedAliceWithPreferredDoctor.isSamePerson(editedAliceWithAnotherPreferredDoctor));

        // same name, one with preferred doctor, one without -> returns false
        editedAliceWithPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        Patient editedAliceWithoutPreferredDoctor = new PatientBuilder(ALICE_AS_PATIENT).build();
        assertFalse(editedAliceWithPreferredDoctor.isSamePerson(editedAliceWithoutPreferredDoctor));

        // same name, same preferred doctor, different attributes -> returns true
        Patient editedAliceWithPreferredDoctorAndBobAddress = (Patient) new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        Patient editedAliceWithPreferredDoctorAndAliceAddress = new PatientBuilder(ALICE_AS_PATIENT)
                .withPreferredDoctor(ADAM).build();
        assertTrue(editedAliceWithPreferredDoctorAndBobAddress
                .isSamePerson(editedAliceWithPreferredDoctorAndAliceAddress));

        // same name, different appointment -> returns false
        Patient editedAmyWithAppointment = new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT).build();
        Patient editedAmyWithAnotherAppointment = new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(BENSON_APPT).build();
        assertFalse(editedAmyWithAppointment.isSamePerson(editedAmyWithAnotherAppointment));

        // same name, one with appointment, one without -> returns false
        editedAmyWithAppointment = new PatientBuilder(AMY_AS_PATIENT).withAppointment(AMY_APPT).build();
        Patient editedAmyWithoutAppointment = new PatientBuilder(AMY_AS_PATIENT).build();
        assertFalse(editedAmyWithAppointment.isSamePerson(editedAmyWithoutAppointment));

        // same name, same appointment, different attributes -> returns true
        Patient editedAmyWithAppointmentAndAmyAddress = (Patient) new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        Patient editedAmyWithAppointmentAndBobAddress = (Patient) new PatientBuilder(AMY_AS_PATIENT)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(editedAmyWithAppointmentAndAmyAddress.isSamePerson(editedAmyWithAppointmentAndBobAddress));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different preffered doctor -> returns false
        Person editedAliceWithPreferredDoctor = new PersonBuilder(ALICE).withPreferredDoctor(ADAM).build();
        Person editedAliceWithAnotherPreferredDoctor = new PersonBuilder(ALICE).withPreferredDoctor(BEN).build();
        assertFalse(editedAliceWithPreferredDoctor.equals(editedAliceWithAnotherPreferredDoctor));

        // different appointment -> returns false
        Person editedAmyWithAppointment = new PersonBuilder(AMY).withAppointment(AMY_APPT).build();
        Person editedAmyWithAnotherAppointment = new PersonBuilder(AMY).withAppointment(BENSON_APPT).build();
        assertFalse(editedAmyWithAppointment.equals(editedAmyWithAnotherAppointment));
    }
}
