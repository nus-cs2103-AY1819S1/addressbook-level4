package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ModelManagerTest.java

import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_STAFFS;

import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BENSON;
||||||| merged common ancestors
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BENSON;
=======
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.address.testutil.TypicalPersons.BEN;
import static seedu.address.testutil.TypicalPersons.BENSON;
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/ModelManagerTest.java

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasStaff(null);
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAppointment(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ModelManagerTest.java
    public void hasStaff_staffNotInClinicIo_returnsFalse() {
        assertFalse(modelManager.hasStaff(ADAM));
||||||| merged common ancestors
    public void hasDoctor_doctorNotInClinicIo_returnsFalse() {
        assertFalse(modelManager.hasDoctor(ADAM));
=======
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDoctor(ADAM));
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/ModelManagerTest.java
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        Date date = new Date(1, 1, 2018);
        Time time = new Time(5, 30);
        Appointment appt = new Appointment(date, time, ALICE_AS_PATIENT, 0, null);
        assertFalse(modelManager.hasAppointment(appt));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ModelManagerTest.java
    public void hasStaff_staffInClinicIo_returnsTrue() {
        modelManager.addStaff(ADAM);
        assertTrue(modelManager.hasStaff(ADAM));
||||||| merged common ancestors
    public void hasDoctor_doctorInClinicIo_returnsTrue() {
        modelManager.addDoctor(ADAM);
        assertTrue(modelManager.hasDoctor(ADAM));
=======
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        modelManager.addDoctor(ADAM);
        assertTrue(modelManager.hasDoctor(ADAM));
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/ModelManagerTest.java
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        Date date = new Date(1, 1, 2018);
        Time time = new Time(5, 30);
        Appointment appt = new Appointment(date, time, ALICE_AS_PATIENT, 0, null);
        modelManager.addAppointment(appt);
        assertTrue(modelManager.hasAppointment(appt));
    }

    @Test
    public void getStaff_staffInClinicIO_returnsStaff() {
        modelManager.addStaff(ADAM);
        assertEquals(ADAM, modelManager.getStaff(ADAM));
    }

    @Test
    public void getStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.getStaff(null);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getFilteredStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredStaffList().remove(0);
    }

    //@@author gingivitiss
    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAppointmentList().remove(0);
    }

    @Test
    public void equals() {
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ModelManagerTest.java
        ClinicIo clinicIo = new ClinicIoBuilder().withPerson(ALICE).withPerson(BENSON)
                .withStaff(ADAM).withStaff(BEN).build();
        ClinicIo differentClinicIo = new ClinicIo();
||||||| merged common ancestors
        ClinicIo clinicIo = new ClinicIoBuilder().withPerson(ALICE).withPerson(BENSON)
                .withDoctor(ADAM).withDoctor(BEN).build();
        ClinicIo differentClinicIo = new ClinicIo();
=======
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withDoctor(ADAM).withDoctor(BEN).build();
        AddressBook differentAddressBook = new AddressBook();
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/ModelManagerTest.java
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        //@@author jjlee050
        modelManager.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
