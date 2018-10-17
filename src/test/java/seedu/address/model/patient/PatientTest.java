package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalPersons.*;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class PatientTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, different preferred doctor -> returns false
        Person editedAliceWithPreferredDoctor = new PersonBuilder(ALICE).withPreferredDoctor(ADAM).build();
        Person editedAliceWithAnotherPreferredDoctor = new PersonBuilder(ALICE).withPreferredDoctor(BEN).build();
        assertFalse(editedAliceWithPreferredDoctor.isSamePerson(editedAliceWithAnotherPreferredDoctor));

        // same name, one with preferred doctor, one without -> returns false
        editedAliceWithPreferredDoctor = new PersonBuilder(ALICE).withPreferredDoctor(ADAM).build();
        Person editedAliceWithoutPreferredDoctor = new PersonBuilder(ALICE).build();
        assertFalse(editedAliceWithPreferredDoctor.isSamePerson(editedAliceWithoutPreferredDoctor));

        // same name, same preferred doctor, different attributes -> returns true
        Person editedAliceWithPreferredDoctorAndBobAddress = new PersonBuilder(ALICE)
                .withPreferredDoctor(ADAM)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        Person editedAliceWithPreferredDoctorAndAliceAddress = new PersonBuilder(ALICE)
                .withPreferredDoctor(ADAM).build();
        assertTrue(editedAliceWithPreferredDoctorAndBobAddress
                .isSamePerson(editedAliceWithPreferredDoctorAndAliceAddress));

        // same name, different appointment -> returns false
        Person editedAmyWithAppointment = new PersonBuilder(AMY).withAppointment(AMY_APPT).build();
        Person editedAmyWithAnotherAppointment = new PersonBuilder(AMY).withAppointment(BENSON_APPT).build();
        assertFalse(editedAmyWithAppointment.isSamePerson(editedAmyWithAnotherAppointment));

        // same name, one with appointment, one without -> returns false
        editedAmyWithAppointment = new PersonBuilder(AMY).withAppointment(AMY_APPT).build();
        Person editedAmyWithoutAppointment = new PersonBuilder(AMY).build();
        assertFalse(editedAmyWithAppointment.isSamePerson(editedAmyWithoutAppointment));

        // same name, same appointment, different attributes -> returns true
        Person editedAmyWithAppointmentAndAmyAddress = new PersonBuilder(AMY)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        Person editedAmyWithAppointmentAndBobAddress = new PersonBuilder(AMY)
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
